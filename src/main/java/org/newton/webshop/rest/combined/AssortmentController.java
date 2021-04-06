package org.newton.webshop.rest.combined;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.creation.ProductCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.dto.response.ProductDto;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.services.combined.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Employee wants to modify the info about an existing product.

    //Employee wants to delete an existing category
    @DeleteMapping
    public void deleteCategoryById(@RequestParam String id) {
        assortmentService.deleteCategoryById(id);
    }
    //Employee wants to assign a category to a product to make products that belong in multiple categories easier to find.

    //Employee wants to choose if a product is displayed in the webshop to be able to keep information about a product even if it's no longer offered.

    /**
     * View products: /products
     * Following user stories need to be implemented:
     */
    //Customer wants a list of products to get an overview of what the shop has to offer.

    /**
     * Find all products: /products/all
     *
     * @return list of all products
     * @author Isa
     */
    @GetMapping("/all")
    List<ProductDto> all() {
        return assortmentService.findAll();
    }


    //Customer wants to sort products to find the most relevant product first.
    //Notes: Sort by category, date added, most sales


    @GetMapping("/sort")
    Page<Product> all(Pageable pageable) {
        return assortmentService.findAll2(pageable);
    }

    //Customer wants to filter products to find products that meet certain criteria.

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterProductsByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Product> products = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Product> pageProducts;
            if (name == null)
                pageProducts = assortmentService.findAll2(paging);
            else
                pageProducts = assortmentService.findByName(name, paging);
            products = pageProducts.getContent();


            Map<String, Object> response = new HashMap<>();
            response.put("Products", products);
            response.put("currentPage", pageProducts.getNumber());
            response.put("totalItems", pageProducts.getTotalElements());
            response.put("totalPages", pageProducts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/categoryid")
    Page<Product> findProductByCategoryId(String categoryId, Pageable pageable) {
        return assortmentService.findByCategoryId(categoryId, pageable);
    }

    @GetMapping("/filter/categoryname")
    Page<Product> findProductByCategoryName(String name, Pageable pageable) {
        return assortmentService.findByCategoryName(name, pageable);
    }


    //Customer wants to get detailed information about a product to assess if the product meet their needs
    //Notes: Show name, price, number in stock, description
}
