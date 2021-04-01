package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.InventoryCreationDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.InventoryService;
import org.newton.webshop.services.ProductService;
import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles requests by AssortmentController.
 */
@Service
public class AssortmentService {
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;

    @Autowired
    public AssortmentService(ProductService productService,
                             InventoryService inventoryService,
                             CategoryService categoryService,
                             ReviewService reviewService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    /**
     * Notes:
     * <p>
     * 1. No methods in AssortmentService should return en Entity or take an Entity as a parameter. Use DTOs instead.
     * <p>
     * 2. Higher level validation that require multiple services (e.g ProductService and InventoryService) should be done in AssortmentService
     * <p>
     * 3. Lower level validation that only require one service (e.g only ProductService) should be done in the individual, lower level service layer and passed on to AssortmentService.
     */
    public CategoryDto createCategory(CategoryCreationDto creationDto) {
        var parentCategoryId = creationDto.getParentCategoryId();
        var parentCategory = parentCategoryId != null ? categoryService.findById(parentCategoryId) : null;

        var childCategoryIds = creationDto.getChildCategoryIds();
        var childCategories = childCategoryIds != null ? categoryService.findById(childCategoryIds) : new HashSet<Category>();

        var productIds = creationDto.getProductIds();
        var products = productIds != null ? productService.findById(productIds) : new HashSet<Product>();

        var categoryEntity = toEntity(creationDto, parentCategory, childCategories, products);
        categoryService.createCategory(categoryEntity);

        return toDto(categoryEntity);
    }

    /**
     * Converts Entity to Dto
     *
     * @param category the category to convert
     * @return CategoryDto
     */
    private static CategoryDto toDto(Category category) {
        //todo microoptimeringar
        //todo flytta konvertering till inre servicelagret

        var parentCategory = category.getParentCategory();
        var parentCategoryId = parentCategory != null ? parentCategory.getId() : null;
        var parentCategoryName = parentCategory != null ? parentCategory.getName() : null;

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentCategoryId(parentCategoryId)
                .parentCategoryName(parentCategoryName)
                .childCategories(category.getChildCategories()
                        .stream()
                        .collect(Collectors.toMap(Category::getId, Category::getName)))
                .products(category.getProducts()
                        .stream()
                        .collect(Collectors.toMap(Product::getId, Product::getName)))
                .build();
    }

    /**
     * Converts CategoryCreationDto to Entity. The composite fields references already existing Entities, so they must be provided as parameters
     *
     * @param dto             contains all scalar fields and references to already existing composite fields
     * @param parentCategory  one parent category
     * @param childCategories set of child categories
     * @param products        set of products
     * @return category entity
     */
    private static Category toEntity(CategoryCreationDto dto, Category parentCategory, Set<Category> childCategories, Set<Product> products) {
        //todo flytta konvertering till inre servicelagret
        return Category.builder()
                .name(dto.getName())
                .parentCategory(parentCategory)
                .childCategories(childCategories)
                .products(products)
                .build();
    }


    /**
     *
     */
    //Customer wants a list of products to get an overview of what the shop has to offer:
    public List<ProductDto> findAll() {
        return productService.findAll()
                .stream()
                .map(AssortmentService::toDto)
                .collect(Collectors.toList());
    }

    //Employee wants to add product
    public ProductDto createProduct(ProductCreationDto productCreationDto) {
        var categoryIds = productCreationDto.getCategoryIds();
        var categories = categoryIds != null ? categoryService.findById(categoryIds) : null;


        Set<Inventory> inventories = productCreationDto.getInventories()
                .stream()
                .map(AssortmentService::toEntity)
                .collect(Collectors.toSet());

        Product product = toEntity(productCreationDto, categories);
        productService.createProduct(product);

        inventories.forEach(inventory -> {
            inventory.setProduct(product);
            inventoryService.createInventory(inventory);
        });

        return toDto(product);
    }


    // Testat som i staffService:
    private static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .inventory(product.getInventory())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .description(product.getDescription())
                .visible(product.isVisible())
                .build();
    }

    private static Product toEntity(ProductCreationDto creationDto, Set<Category> categories) {
        return Product.builder()
                .inventory(new HashSet<>())
                .name(creationDto.getName())
                .price(creationDto.getPrice())
                .category(categories)
                .description(creationDto.getDescription())
                .visible(creationDto.isVisible())
                .build();
    }

    private static Inventory toEntity(InventoryCreationDto creationDto) {
        return Inventory.builder()
                .size(creationDto.getSize())
                .color(creationDto.getColor())
                .quantity(creationDto.getQuantity())
                .build();
    }


}
