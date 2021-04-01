package org.newton.webshop.rest.combined;

import org.newton.webshop.models.dto.creation.CategoryCreationDto;
import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.services.combined.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API endpoints to handle the assortment
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
     * Notes:
     * If a new user story is added, make sure it's also added to the project document!
     *
     */

    /**
     * Manage categories: /products/categories
     *
     * Following user stories need to be implemented:
     */
    //Employee wants to add a category to keep products categorized, making them easier to find.
    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto createCategory(@RequestBody CategoryCreationDto creationDto) {
        return assortmentService.createCategory(creationDto);
    }

    //Employee wants to modify an existing category, to make it easier to update the webshop.
    //Notes: change name,


    /**
     * Manage products: /products
     *
     * Following user stories need to be implemented:
     */
    //Employee wants to add a new product.
    //Notes: price, name, description, category etc.

    //Employee wants to modify the info about an existing product.

    //Employee wants to assign a category to a product to make products that belong in multiple categories easier to find.

    //Employee wants to choose if a product is displayed in the webshop to be able to keep information about a product even if it's no longer offered.

    /**
     * View products: /products
     *
     * Following user stories need to be implemented:
     */
    //Customer wants a list of products to get an overview of what the shop has to offer.

    //Customer wants to filter products to find products that meet certain criteria.

    //Customer wants to sort products to find the most relevant product first.
    //Notes: Sort by category, date added, most sales

    //Customer wants to get detailed information about a product to assess if the product meet their needs
    //Notes: Show name, price, number in stock, description, material type
}
