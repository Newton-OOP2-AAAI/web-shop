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
     * Manage categories: /products/categories
     * Following user stories need to be implemented:
     */
    //Employee wants to add a category to keep products categorized, making them easier to find.
    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto createCategory(@RequestBody CategoryCreationDto creationDto) {
        return assortmentService.createCategory(creationDto);
    }

    /**
     * Update a category
     *
     * @param id        id of category
     * @param updateDto CategoryCreationDto
     * @return CategoryDto
     * Employee wants to modify an existing category, to make it easier to update the webshop.
     * Notes: change name, change parentCategory
     */
    @PutMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@RequestParam String id, @RequestBody CategoryCreationDto updateDto) {
        return assortmentService.updateCategory(id, updateDto);
    }

    @GetMapping("/categories/all")
    public List<CategoryDto> allCategories() {
        return assortmentService.findAllCategories();
    }

    /**
     * Manage products: /products
     * Following user stories need to be implemented:
     */

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

    @GetMapping
    public ProductDto findById(@RequestParam String id) {
        return assortmentService.findProductById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestParam String id, @RequestBody ProductUpdateDto updateDto) {
        return assortmentService.updateProduct(id, updateDto);
    }

    @GetMapping("/all/inventories")
    public List<Inventory> allInventories(@RequestParam String id) {
        return assortmentService.findAll(id);
    }

    @PostMapping("/{product_id}/inventories")
    public ProductDto createInventory(@PathVariable(name = "product_id") String productId, @RequestBody InventoryCreationDto creationDto) {
        return assortmentService.createInventory(productId, creationDto);
    }

    @PutMapping("/update/inventories")
    public InventoryDto updateInventories(@RequestParam String id, @RequestBody InventoryCreationDto creationDto) {
        return assortmentService.updateInventories(id, creationDto);
    }

    @DeleteMapping("/delete/inventory")
    public void deleteInventoryById(@RequestParam String id) {
        assortmentService.deleteInventoryById(id);
    }

    @DeleteMapping
    public void deleteCategoryById(@RequestParam String id) {
        assortmentService.deleteCategoryById(id);
    }

    /**
     * Find all products: /products/all
     *
     * @return a productDto-list of all products
     */
    @GetMapping("/all")
    List<ProductDto> all() {
        return assortmentService.findAll();
    }

    /**
     * Sort the list of products
     *
     * @param pageable
     * @return
     */
    @GetMapping("/sort")
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
    @GetMapping("/filter/categoryid")
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
    @GetMapping("/filter/categoryname")
    Page<ProductSimpleDto> findProductByCategoryName(String name, Pageable pageable) {
        return assortmentService.findByCategoryName(name, pageable);
    }

}
