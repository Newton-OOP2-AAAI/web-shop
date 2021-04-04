package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.creation.InventoryCreationDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.CategoryRepository;
import org.newton.webshop.repositories.InventoryRepository;
import org.newton.webshop.repositories.ProductRepository;
import org.newton.webshop.repositories.ReviewRepository;
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
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public AssortmentService(ProductRepository productRepository,
                             InventoryRepository inventoryRepository,
                             CategoryRepository categoryRepository,
                             ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
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
                : categoryRepository.findById(parentCategoryId).orElseThrow(RuntimeException::new); //todo Exception: Category not found

        //Find referenced Child Categories, otherwise leave as empty HashSet
        var childCategoryIds = creationDto.getChildCategoryIds();
        var childCategories = (childCategoryIds == null || childCategoryIds.isEmpty())
                ? new HashSet<Category>()
                : childCategoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(RuntimeException::new)) //todo Exception: Category not found
                .collect(Collectors.toSet());

        //Find referenced Products, otherwise leave as empty HashSet
        var productIds = creationDto.getProductIds();
        var products = (productIds == null || productIds.isEmpty())
                ? new HashSet<Product>()
                : productIds.stream()
                .map(id -> productRepository.findById(id).orElseThrow(RuntimeException::new)) //todo Exception: Product not found
                .collect(Collectors.toSet());

        //Convert Dto to Entity, persist in database and return Dto to Controller
        var categoryEntity = toEntity(creationDto, parentCategory, childCategories, products);
        categoryRepository.save(categoryEntity);
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
        var oldCategory = categoryRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Category not found

        //todo refactor: create equals method for category (and products below)
        //If parent category id has changed, fetch the new parent category. Ternary operators to avoid nullpointer exceptions.
        var oldParentCategory = oldCategory.getParentCategory();
        var oldParentCategoryId = (oldParentCategory == null)
                ? null
                : oldParentCategory.getId();
        var updatedParentCategoryId = dto.getParentCategoryId();
        var updatedParentCategory = (updatedParentCategoryId == null)
                ? null
                : (updatedParentCategoryId.equals(oldParentCategoryId))
                ? oldParentCategory
                : categoryRepository.findById(updatedParentCategoryId).orElseThrow(RuntimeException::new); //todo Exception: Category not found

        //todo refactor: the exact same logic is done with both child categories and products. Try to create a general method for this
        //Updating child categories
        var updatedChildCategories = oldCategory.getChildCategories();

        //Remove associations with child categories that shouldn't exist anymore
        var newChildCategoryIds = (dto.getChildCategoryIds() == null)
                ? new HashSet<String>()
                : dto.getChildCategoryIds();
        updatedChildCategories.removeIf(category -> !newChildCategoryIds.contains(category.getId()));

        //Add associations with child categories which didn't exist before
        if (!newChildCategoryIds.isEmpty()) {
            var oldChildCategories = updatedChildCategories
                    .stream()
                    .collect(Collectors.toMap(Category::getId, category -> category));
            var newChildCategories = newChildCategoryIds
                    .stream()
                    .filter(categoryId -> !oldChildCategories.containsKey(categoryId))
                    .map(categoryRepository::findById)
                    .map(category -> category.orElseThrow(RuntimeException::new)) //todo Exception: Category not found
                    .collect(Collectors.toSet());
            updatedChildCategories.addAll(newChildCategories);
        }

        //Updating products
        var updatedProducts = oldCategory.getProducts();

        //Remove associations with child categories that shouldn't exist anymore
        var newProductIds = (dto.getProductIds() == null)
                ? new HashSet<String>()
                : dto.getProductIds();
        updatedProducts.removeIf(product -> !newProductIds.contains(product.getId()));

        //Add associations with child categories which didn't exist before
        if (!newProductIds.isEmpty()) {
            var oldProducts = updatedProducts
                    .stream()
                    .collect(Collectors.toMap(Product::getId, product -> product));
            var newProducts = newProductIds
                    .stream()
                    .filter(productId -> !oldProducts.containsKey(productId))
                    .map(productRepository::findById)
                    .map(product -> product.orElseThrow(RuntimeException::new)) //todo Exception: Product not found
                    .collect(Collectors.toSet());
            updatedProducts.addAll(newProducts);
        }

        //Create entity from all components, persist in database and return dto to controller
        var updatedCategory = toEntity(id, dto, updatedParentCategory, updatedChildCategories, updatedProducts);
        return toDto(categoryRepository.save(updatedCategory));
    }

    /**
     * Find a list of all products
     * @return list
     */
    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(AssortmentService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create a product
     * @param productCreationDto inventories cannot be empty or null
     * @return dto
     */
    public ProductDto createProduct(ProductCreationDto productCreationDto) {
        var categoryIds = productCreationDto.getCategoryIds();
        var categories = (categoryIds == null)
                ? new HashSet<Category>()
                : categoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(RuntimeException::new)) //todo Exception: Category not found
                .collect(Collectors.toSet());

        var invetoryDtos = productCreationDto.getInventories();
        if (invetoryDtos == null || invetoryDtos.isEmpty()) {
            throw new RuntimeException(); //todo Exception: Inventory must exist, set quantity to 0 if product isn't in stock yet
        }

        Set<Inventory> inventories = invetoryDtos
                .stream()
                .map(AssortmentService::toEntity)
                .collect(Collectors.toSet());

        Product product = toEntity(productCreationDto, categories);
        productRepository.save(product);

        inventories.forEach(inventory -> {
            inventory.setProduct(product);
            inventoryRepository.save(inventory);
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
