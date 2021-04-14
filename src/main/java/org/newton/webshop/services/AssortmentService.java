package org.newton.webshop.services;


import org.newton.webshop.exceptions.CategoryNotFoundException;
import org.newton.webshop.exceptions.RoleNotFoundException;
import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.creation.InventoryCreationDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.dto.response.InventoryDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.dto.response.ProductSimpleDto;
import org.newton.webshop.models.dto.update.ProductUpdateDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.services.shared.CategoryService;
import org.newton.webshop.services.shared.InventoryService;
import org.newton.webshop.services.shared.ProductService;
import org.newton.webshop.services.shared.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
     * Creates a new category and sets associations (parent category, child categories, products).
     * Referenced entities must already exist in database, otherwise exception is thrown.
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
        var childCategories = categoryService.findById(childCategoryIds);

        //Find referenced Products
        var productIds = creationDto.getProductIds();
        var products = productService.findById(productIds);

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
     *
     * @exception
     */
    public CategoryDto updateCategory(String id, CategoryCreationDto dto) {
        //Find the category
        var oldCategory = categoryService.findById(id);

        //Below we prepare the associated entities with our category (parent category, child categories and products)
        //The new entities are represented by ids to avoid fetching entities twice

        //Parent category
        var newParentCategoryId = dto.getParentCategoryId();
        var oldParentCategory = oldCategory.getParentCategory();
        var updatedParentCategory = categoryService.getNewCategory(newParentCategoryId, oldParentCategory);

        //Child categories
        var newChildCategoryIds = (dto.getChildCategoryIds() == null) ? new HashSet<String>() : dto.getChildCategoryIds();
        var oldChildCategories = oldCategory.getChildCategories();
        var updatedChildCategories = categoryService.getNewCategories(newChildCategoryIds, oldChildCategories);

        //Products
        var newProductIds = (dto.getProductIds() == null) ? new HashSet<String>() : dto.getProductIds();
        var oldProducts = oldCategory.getProducts();
        var updatedProducts = productService.getNewProducts(newProductIds, oldProducts);

        //Create entity from all components, persist in database and return dto to controller
        var updatedCategory = toEntity(id, dto, updatedParentCategory, updatedChildCategories, updatedProducts);
        return toDto(categoryService.save(updatedCategory));
    }

    /**
     * Find a category by id
     *
     * @return category
     */
    public CategoryDto findByCategoryById(String id) {
        Category category = categoryService.findById(id);
        return toDto(category);
    }

    /**
     * Find a list of all categories
     *
     * @return list
     */
    public List<CategoryDto> findAllCategories() {
        return categoryService.findAll()
                .stream()
                .map(AssortmentService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Find a product by productId
     *
     * @return product
     */
    public ProductDto findProductById(String productId) {
        Product product = productService.findById(productId);
        return toDto(product);
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
     * Sort products
     *
     * @param pageable takes chosen specifications from url,e.g. page=0&size=3
     *                 shows first page and three items per page
     * @return list of productSimpleDto:s
     */
    public Page<ProductSimpleDto> findAll(Pageable pageable) {
        List<ProductSimpleDto> allItems = productService.findAll(pageable)
                .stream()
                .map(AssortmentService::toSimpleDto)
                .collect(Collectors.toList());
        return new PageImpl<>(allItems);
    }

    /**
     * Filter products by category (categoryId)
     *
     * @param categoryId - categoryId specified in url
     * @param pageable   takes chosen specifications from url,e.g. page=0&size=3
     * @return list of productSimpleDto:s
     */
    public Page<ProductSimpleDto> findByCategoryId(String categoryId, Pageable pageable) {
        List<ProductSimpleDto> allItems = productService.getAllProductsByCategoryId(categoryId, pageable)
                .stream()
                .map(AssortmentService::toSimpleDto)
                .collect(Collectors.toList());
        return new PageImpl(allItems);
    }

    /**
     * Filter products by category (categoryName)
     *
     * @param name     -category named specified in url
     * @param pageable takes chosen specifications from url,e.g. page=0&size=3
     * @return pageImpl that implements dto-list
     */
    public Page<ProductSimpleDto> findByCategoryName(String name, Pageable pageable) {
        List<ProductSimpleDto> allItems = productService.getAllProductsByCategoryName(name, pageable)
                .stream()
                .map(AssortmentService::toSimpleDto)
                .collect(Collectors.toList());
        return new PageImpl<>(allItems);

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

        Product product = toEntity(productCreationDto, categories, inventories);
        productService.save(product);

        inventories.forEach(inventory -> {
            inventory.setProduct(product);
            inventoryService.save(inventory);
        });

        return toDto(product);
    }

    /**
     * Delete category by category id
     *
     * @param id category id
     */
    public void deleteCategoryById(String id) {
        categoryService.deleteCategory(id);
    }

    /**
     * Update a product entity and it's related inventory entities
     *
     * @param id        the id of the product
     * @param updateDto dto that contains the new values in each field
     * @return dto of the updated product
     */
    public ProductDto updateProduct(String id, ProductUpdateDto updateDto) {
        var categoryIds = updateDto.getCategoryIds();
        var categories = (categoryIds == null)
                ? new HashSet<Category>()
                : categoryIds.stream()
                .map(categoryService::findById)
                .collect(Collectors.toSet());

        Product product = toEntity(updateDto, categories);
        Product updateProduct = productService.updateProduct(id, product);
        return toDto(updateProduct);
    }

    public void deleteProductById(String productId) {
        productService.deleteProduct(productId);
    }

    /**
     * Creates a new inventory entity associated with given product id.
     * An inventory is a variation of a product. For example a product with two available colors has two inventory entities describing the variations.
     *
     * @param productId id of the product
     * @param updateDto details about the inventory
     * @return productdto after the inventory was created
     */
    public ProductDto createInventory(String productId, InventoryCreationDto updateDto) {
        Product product = productService.findById(productId);

        Inventory inventory = toEntity(updateDto);

        inventory.setProduct(product);
        product.addInventory(inventory);

        inventoryService.save(inventory);
        return toDto(product);
    }

    /**
     * Update an inventory
     *
     * @param inventoryId inventory id
     * @param creationDto contains values to update the inventory with
     * @return dto containing the updated inventory todo should maybe return entire resource (productdto)?
     */
    public InventoryDto updateInventories(String inventoryId, InventoryCreationDto creationDto) {
        Inventory inventory = toEntity(creationDto);
        Inventory updatedInventory = inventoryService.updateInventories(inventoryId, inventory);

        return toDto(updatedInventory);
    }

    public Set<InventoryDto> findAll(String id) {
        Product product = productService.findById(id);

        return product.getInventory().stream().map(AssortmentService::toDto).collect(Collectors.toSet());
    }

    public void deleteInventoryById(String id) {
        inventoryService.deleteInventory(id);
    }

    /**
     * Converts CategoryCreationDto to Entity without id
     *
     * @param dto             contains details about the category. The entities referenced by id in the dto should be supplied as additional parameters
     * @param parentCategory  one parent category, already persisted in the database
     * @param childCategories set of child categories, already persisted in the database
     * @param products        set of products, already persisted in the database
     * @return category entity
     */
    private static Category toEntity(CategoryCreationDto dto, Category
            parentCategory, Set<Category> childCategories, Set<Product> products) {
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
    private static Category toEntity(String id, CategoryCreationDto dto, Category
            parentCategory, Set<Category> childCategories, Set<Product> products) {
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


    /**
     * Converts a product entity to a response dto
     *
     * @param product product to convert
     * @return response dto
     */
    private static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .inventory(product.getInventory().stream().map(AssortmentService::toDto).collect(Collectors.toSet()))
                .name(product.getName())
                .price(product.getPrice())
                .categories(product.getCategory().stream().collect(Collectors.toMap(Category::getId, Category::getName)))
                .description(product.getDescription())
                .visible(product.isVisible())
                .build();
    }

    /**
     * Converts a product entity to a response dto which only contains some information about the product
     *
     * @param product the product to convert
     * @return a response dto
     */
    private static ProductSimpleDto toSimpleDto(Product product) {
        return ProductSimpleDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    /**
     * Converts creationDto to Product entity. The referenced entities in the creationDto must be supplied as additional parameters.
     *
     * @param creationDto details about the category
     * @param categories  category entities to associate the product with
     * @param inventories inventory entities to associate the product with
     * @return Product entity
     */
    private static Product toEntity(ProductCreationDto creationDto, Set<Category> categories, Set<Inventory> inventories) {
        return Product.builder()
                .inventory(inventories)
                .name(creationDto.getName())
                .price(creationDto.getPrice())
                .category(categories)
                .description(creationDto.getDescription())
                .visible(creationDto.isVisible())
                .build();
    }

    /**
     * Converts dto to Inventory entity.
     *
     * @param creationDto
     * @return
     */
    private static Inventory toEntity(InventoryCreationDto creationDto) {
        return Inventory.builder()
                .size(creationDto.getSize())
                .color(creationDto.getColor())
                .quantity(creationDto.getQuantity())
                .build();
    }

    /**
     * Converts dto to Product entity. Associated Category entities are supplied as additional parameters.
     *
     * @param creationDto contains details about the product
     * @param categories  set of category entities
     * @return product entity
     */
    private static Product toEntity(ProductUpdateDto creationDto, Set<Category> categories) {
        return Product.builder()
                .name(creationDto.getName())
                .price(creationDto.getPrice())
                .category(categories)
                .description(creationDto.getDescription())
                .visible(creationDto.isVisible())
                .build();
    }

    /**
     * Converts Inventory entity to dto.
     *
     * @param inventory the inventory entity
     * @return dto
     */
    private static InventoryDto toDto(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .size(inventory.getSize())
                .color(inventory.getColor())
                .quantity(inventory.getQuantity())
                .build();
    }
}
