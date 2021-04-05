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
    private final CategoryService categoryService;
    private final InventoryService inventoryService;
    private final ReviewService reviewService;

    @Autowired
    public AssortmentService(ProductService productService,
                             CategoryService categoryService,
                             InventoryService inventoryService,
                             ReviewService reviewService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.reviewService = reviewService;
    }

    /**
     * Creates a new category and sets associations (parent category, child categories, products). Referenced entities must already exist in database, otherwise exception is thrown.
     *
     * @param creationDto CategoryCreationDto
     * @return CategoryDto
     */
    public CategoryDto createCategory(CategoryCreationDto creationDto) {
        //Find referenced Parent Category, otherwise leave as null
        var parentCategoryId = creationDto.getParentCategoryId();
        var parentCategory = (parentCategoryId == null)
                ? null
                : categoryService.findById(parentCategoryId);

        //Find referenced Child Categories, otherwise leave as empty HashSet
        var childCategoryIds = creationDto.getChildCategoryIds();
        var childCategories = (childCategoryIds == null || childCategoryIds.isEmpty())
                ? new HashSet<Category>()
                : childCategoryIds.stream()
                .map(categoryService::findById)
                .collect(Collectors.toSet());

        //Find referenced Products, otherwise leave as empty HashSet
        var productIds = creationDto.getProductIds();
        var products = (productIds == null || productIds.isEmpty())
                ? new HashSet<Product>()
                : productIds.stream()
                .map(productService::findById)
                .collect(Collectors.toSet());

        //Convert Dto to Entity, persist in database and return Dto to Controller
        var categoryEntity = toEntity(creationDto, parentCategory, childCategories, products);
        categoryService.save(categoryEntity);
        return toDto(categoryEntity);
    }

    /**
     * Updates given category with the fields in dto
     *
     * @param id  id of category which should be updated
     * @param dto contains the fields and associations to update
     * @return Dto
     */
    public CategoryDto updateCategory(String id, CategoryCreationDto dto) {
        //todo Fråga Thor: Ett eller två service lager?

        //todo refactor: see if some of the logic can be done in the inner service layer instead. Perhaps compare methods in entity? (e.g findRemovedChildCategoryIds() and findAddedChildCategoryIds())
        //todo refactor: force user to provide empty sets instead of null values?
        //Find the category
        var oldCategory = categoryService.findById(id); //todo Exception: Category not found

        //Updating parent category
        var newParentCategoryId = dto.getParentCategoryId();
        var oldParentCategory = oldCategory.getParentCategory();
        var updatedParentCategory = categoryService.getNewCategory(newParentCategoryId, oldParentCategory);

        //todo refactor: the exact same logic is done with both child categories and products. Try to create a general method for this
        //Updating child categories
        var newChildCategoryIds = (dto.getChildCategoryIds() == null) //Todo Fråga Thor: Validering i inre lagret? "Variable used in lambda expression should be final or effectively final" när vi assignade om variabeln i inre lagret
                ? new HashSet<String>()
                : dto.getChildCategoryIds();
        var oldChildCategories = oldCategory.getChildCategories();
        var updatedChildCategories = categoryService.getNewCategories(newChildCategoryIds, oldChildCategories);

        //Updating products
        var newProductIds = (dto.getProductIds() == null)
                ? new HashSet<String>()
                : dto.getProductIds();
        var oldProducts = oldCategory.getProducts();
        var updatedProducts = productService.getNewProducts(newProductIds, oldProducts);

        //Create entity from all components, persist in database and return dto to controller
        var updatedCategory = toEntity(id, dto, updatedParentCategory, updatedChildCategories, updatedProducts);
        return toDto(categoryService.save(updatedCategory));
    }

    /**
     * Find a list of all products
     *
     * @return list
     */
    public List<ProductDto> findAll() {
        return productService.findAll()
                .stream()
                .map(AssortmentService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create a product
     *
     * @param productCreationDto inventories cannot be empty or null
     * @return dto
     */
    public ProductDto createProduct(ProductCreationDto productCreationDto) {
        var categoryIds = productCreationDto.getCategoryIds();
        var categories = (categoryIds == null)
                ? new HashSet<Category>()
                : categoryIds.stream()
                .map(categoryService::findById)
                .collect(Collectors.toSet());

        var inventoryDtos = productCreationDto.getInventories();
        if (inventoryDtos == null || inventoryDtos.isEmpty()) {
            throw new RuntimeException(); //todo Exception: Inventory must exist, set quantity to 0 if product isn't in stock yet
        }

        Set<Inventory> inventories = inventoryDtos
                .stream()
                .map(AssortmentService::toEntity)
                .collect(Collectors.toSet());

        Product product = toEntity(productCreationDto, categories);
        productService.save(product);

        inventories.forEach(inventory -> {
            inventory.setProduct(product);
            inventoryService.save(inventory);
        });

        return toDto(product);
    }

    /**
     * Converts CategoryCreationDto to Entity without id
     *
     * @param dto             contains all scalar fields and references to already existing composite fields
     * @param parentCategory  one parent category
     * @param childCategories set of child categories
     * @param products        set of products
     * @return category entity
     */
    private static Category toEntity(CategoryCreationDto dto, Category parentCategory, Set<Category> childCategories, Set<Product> products) {
        //todo Fråga Thor: Bör man ha validering i sina konverteringsmetoder, t.ex name != null
        return Category.builder()
                .name(dto.getName())
                .parentCategory(parentCategory)
                .childCategories(childCategories)
                .products(products)
                .build();
    }

    /**
     * Converts CategoryCreationDto to Entity with id
     *
     * @param id              an existing id
     * @param dto             CategoryCreationDto
     * @param parentCategory  Set of already persisted parent categories
     * @param childCategories Set of already persisted child categories
     * @param products        set of already persisted products
     * @return category entity
     */
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
     * Converts Entity to Dto
     *
     * @param category the category to convert
     * @return CategoryDto
     */
    private static CategoryDto toDto(Category category) {
        //todo microoptimeringar: Fråga Thor
        //todo flytta konvertering till egen factory klass
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
