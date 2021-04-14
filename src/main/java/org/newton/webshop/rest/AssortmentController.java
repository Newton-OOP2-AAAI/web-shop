package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.creation.InventoryCreationDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.dto.response.InventoryDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.dto.response.ProductSimpleDto;
import org.newton.webshop.models.dto.update.ProductUpdateDto;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.services.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * API endpoints to handle the assortment
 * Notes: If a new user story is added, make sure it's also added to the project document!
 */
@RestController
@RequestMapping("/products")
public class AssortmentController {
    private final AssortmentService assortmentService;

    @Autowired
    public AssortmentController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    /**
     * Create a category
     *
     * @param creationDto creation dto
     * @return response dto
     */
    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto createCategory(@RequestBody CategoryCreationDto creationDto) {
        return assortmentService.createCategory(creationDto);
    }

    /**
     * Update a category
     *
     * @param categoryId id of category
     * @param updateDto  CategoryCreationDto
     * @return CategoryDto
     * Employee wants to modify an existing category, to make it easier to update the webshop.
     * Notes: change name, change parentCategory
     */
    @PutMapping(value = "/categories/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@PathVariable(name = "category_id") String categoryId, @RequestBody CategoryCreationDto updateDto) {
        return assortmentService.updateCategory(categoryId, updateDto);
    }

    /**
     * Find all categories
     *
     * @return
     */
    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> allCategories() {
        return assortmentService.findAllCategories();
    }

    /**
     * Create a product
     *
     * @param productCreationDto
     * @return Employee wants to add a new product.
     * Notes: price, name, description, category etc.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto createProduct(@RequestBody ProductCreationDto productCreationDto) {
        return assortmentService.createProduct(productCreationDto);
    }

    /**
     * Find product by id
     *
     * @param id product id
     * @return product dto
     */
    @GetMapping(params = {"id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto findById(@RequestParam String id) {
        return assortmentService.findProductById(id);
    }

    /**
     * Update a product
     *
     * @param productId id of product to update
     * @param updateDto dto containing fields that will be updated
     * @return product dto
     */
    @PutMapping(path = "/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@PathVariable(name = "product_id") String productId,
                                    @RequestBody ProductUpdateDto updateDto) {
        return assortmentService.updateProduct(productId, updateDto);
    }

    /**
     * Find all inventories a product has.
     *
     * @return
     */
    @GetMapping(path = "/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> allInventories(@RequestParam(name = "product_id") String productId) {
        return assortmentService.findAll(productId);
    }

    /**
     * Create a inventory
     *
     * @param creationDto dto
     * @return product dto
     */
    @PostMapping(path = "/{product_id}/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto createInventory(@PathVariable(name = "product_id") String productId,
                                      @RequestBody InventoryCreationDto creationDto) {
        return assortmentService.createInventory(productId, creationDto);
    }

    /**
     * Update a inventory
     *
     * @param inventoryId id of inventory to update
     * @param creationDto dto containing fields that will be updated
     * @return inventory dto
     */
    @PutMapping(path = "/inventories/{inventory_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InventoryDto updateInventories(@PathVariable(name = "inventory_id") String inventoryId,
                                          @RequestBody InventoryCreationDto creationDto) {
        return assortmentService.updateInventories(inventoryId, creationDto);
    }

    /**
     * Delete a inventory
     *
     * @param inventoryId id of inventory to delete
     */
    @DeleteMapping(path = "/inventories/{inventory_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteInventoryById(@PathVariable(name = "inventory_id") String inventoryId) {
        assortmentService.deleteInventoryById(inventoryId);
    }

    /**
     * Delete a category
     *
     * @param categoryId id of category to delete
     */
    @DeleteMapping(path = "/categories/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCategoryById(@PathVariable(name = "category_id") String categoryId) {
        assortmentService.deleteCategoryById(categoryId);
    }

    /**
     * Find all products.
     * Optional params:
     *
     * @param pageable sort, page, size
     * @return page of dtos
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductSimpleDto> all(Pageable pageable) {
        return assortmentService.findAll(pageable);
    }


    /**
     * Filter products by categoryId
     *
     * @param categoryId
     * @param pageable
     * @return
     */
    @GetMapping(path = "/filter", params = {"categoryId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductSimpleDto> findProductByCategoryId(String categoryId, Pageable pageable) {
        return assortmentService.findByCategoryId(categoryId, pageable);
    }

    /**
     * Filter products by categoryName
     *
     * @param name
     * @param pageable
     * @return
     */
    @GetMapping(path = "/filter", params = {"name"}, produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductSimpleDto> findProductByCategoryName(String name, Pageable pageable) {
        return assortmentService.findByCategoryName(name, pageable);
    }
}
