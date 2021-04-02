package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.creation.InventoryCreationDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.InventoryService;
import org.newton.webshop.services.ProductService;
import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles requests by AssortmentController.
 * Notes:
 * 1. No methods in AssortmentService should return en Entity or take an Entity as a parameter. Use DTOs instead.
 * 2. Higher level validation that require multiple services (e.g ProductService and InventoryService) should be done in AssortmentService
 * 3. Lower-level validation that only require one service (e.g only ProductService) should be done in
 * the individual, lower-level service layer and passed on to AssortmentService.
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
     * Create a new category and set associations (parent category, child categories, products). Referenced entities must exist in database, otherwise exception is thrown.
     *
     * @param creationDto CategoryCreationDto
     * @return CategoryDto
     */
    public CategoryDto createCategory(CategoryCreationDto creationDto) {
        //Find referenced Parent Category, otherwise leave as null
        var parentCategoryId = creationDto.getParentCategoryId();
        var parentCategory = parentCategoryId != null ? categoryService.findById(parentCategoryId) : null;

        //Find referenced Child Categories, otherwise leave as empty HashSet
        var childCategoryIds = creationDto.getChildCategoryIds();
        var childCategories = childCategoryIds != null ? categoryService.findById(childCategoryIds) : new HashSet<Category>();

        //Find referenced Products, otherwise leave as empty HashSet
        var productIds = creationDto.getProductIds();
        var products = productIds != null ? productService.findById(productIds) : new HashSet<Product>();

        //Convert Dto to Entity and persist in database
        var categoryEntity = toEntity(creationDto, parentCategory, childCategories, products);
        categoryService.createCategory(categoryEntity);

        //Return Dto to Controller
        return toDto(categoryEntity);
    }

    /**
     * Updates a category
     *
     * @param id  id of category to update
     * @param dto CategoryCreationDto
     * @return Dto
     */
    public CategoryDto updateCategory(String id, CategoryCreationDto dto) {
        //todo refactor: see if some of the logic can be done in the inner service layer instead
        //Find the category
        var categoryToUpdate = categoryService.findById(id);

        //If parent category id has changed, fetch the new parent category.
        var oldParentCategoryId = categoryToUpdate.getId();
        var newParentCategoryId = dto.getParentCategoryId();
        var newParentCategory = oldParentCategoryId.equals(newParentCategoryId) ? categoryToUpdate.getParentCategory() : categoryService.findById(newParentCategoryId);

        //todo refactor: the exact same logic is done with both child categories and products. Try to create a general method for this
        //Remove associations with child categories that shouldn't exist anymore
        var childCategories = categoryToUpdate.getChildCategories();
        var newChildCategoryIds = dto.getChildCategoryIds();
        childCategories.removeIf(category -> !dto.getChildCategoryIds().contains(category.getId()));

        //Add associations with child categories which didn't exist before
        var oldChildCategories = childCategories
                .stream()
                .collect(Collectors.toMap(Category::getId, category -> category));
        var newChildCategories = newChildCategoryIds
                .stream()
                .filter(categoryId -> !oldChildCategories.containsKey(categoryId))
                .map(categoryService::findById)
                .collect(Collectors.toSet());
        childCategories.addAll(newChildCategories);

        //Remove associations with child categories that shouldn't exist anymore
        var productIds = dto.getProductIds();
        var products = categoryToUpdate.getProducts();
        products.removeIf(product -> !dto.getProductIds().contains(product.getId()));

        //Add associations with child categories which didn't exist before
        var oldProducts = products
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        var newProducts = productIds
                .stream()
                .filter(productId -> !oldProducts.containsKey(productId))
                .map(productService::findById)
                .collect(Collectors.toSet());
        products.addAll(newProducts);

        return toDto(categoryService.update(toEntity(id, dto, newParentCategory, childCategories, products)));
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
        return Category.builder()
                .name(dto.getName())
                .parentCategory(parentCategory)
                .childCategories(childCategories)
                .products(products)
                .build();
    }

    private static Category toEntity(String id, CategoryCreationDto dto, Category parentCategory, Set<Category> childCategories, Set<Product> products) {
        return Category.builder()
                .id(id)
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
