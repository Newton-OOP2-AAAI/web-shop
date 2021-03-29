package org.newton.webshop.services.combined;

import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.InventoryService;
import org.newton.webshop.services.ProductService;
import org.newton.webshop.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * 1. No methods in AssortmentService should return en Entity or take an Entity as a parameter. Use DTOs instead.
     *
     * 2. Higher level validation that require multiple services (e.g ProductService and InventoryService) should be done in AssortmentService
     *
     * 3. Lower level validation that only require one service (e.g only ProductService) should be done in the individual, lower level service layer and passed on to AssortmentService.
     */

}
