package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.InventoryService;
import org.newton.webshop.services.ProductService;
import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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


}
