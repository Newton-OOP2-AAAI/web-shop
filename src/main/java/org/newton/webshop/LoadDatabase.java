package org.newton.webshop;

import org.newton.webshop.models.*;
import org.newton.webshop.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner InitDatabase(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            CartRepository cartRepository,
            ItemRepository itemRepository,
            CategoryRepository categoryRepository
    ) {


        return args -> {
            Category category = new Category();
            category.setName("Pants");
            category.setChildCategories(new HashSet<>());
            category.setParentCategories(new HashSet<>());

            Category childCategory = new Category();
            childCategory.setName("Jeans");
            childCategory.setChildCategories(new HashSet<>());
            childCategory.setParentCategories(new HashSet<>());

            Category parentCategory = new Category();
            parentCategory.setName("Clothes");
            parentCategory.setChildCategories(new HashSet<>());
            parentCategory.setParentCategories(new HashSet<>());

            log.info("Preloading " + categoryRepository.save(childCategory));
            log.info("Preloading " + categoryRepository.save(parentCategory));

            category.addChildCategory(childCategory);
            category.addParentCategory(parentCategory);

            log.info("Preloading " + categoryRepository.save(category));





            //todo sample data
            /*
            Customer cust1 = new Customer("Förnamn", "Efternamn", "0701234567", "hej@hej.hej", "Testvägen", 1337, 41870, "Göteborg");
            log.info("Preloading " + customerRepository.save(cust1));
            Customer cust2 = new Customer("Hej", "Hejsson", "0701234567", "hej@hej.hej", "Testvägen", 13, 41870, "Göteborg");
            log.info("Preloading " + customerRepository.save(cust2));

            Product prod1 = new Product("T-shirt", 199, "Basics", "100% cotton, plain white");
            Product prod2 = new Product("Jeans", 599, "Trousers", "10% less water, sustainable brand");
            Product prod3 = new Product("Hat", 299, "Hats", "Incredibly soft beanie!");

            log.info("Preloading " + productRepository.save(prod1));
            log.info("Preloading " + productRepository.save(prod2));
            log.info("Preloading " + productRepository.save(prod3));

            Cart cart1 = new Cart(null, cust1);
            log.info("Preloading " + cartRepository.save(cart1));

            Item item1 = new Item(cart1, prod1, 5, "M");
            Item item2 = new Item(cart1, prod2, 1, "XL");
            Item item3 = new Item(cart1, prod3, 2, "S");

            log.info("Preloading " + itemRepository.save(item1));
            log.info("Preloading " + itemRepository.save(item2));
            log.info("Preloading " + itemRepository.save(item3));

             */
        };
    }
}
