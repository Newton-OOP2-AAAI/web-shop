package org.newton.webshop;

import org.newton.webshop.models.*;
import org.newton.webshop.models.entities.Category;
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
            /**
             * Sample data for three categories
             */
//            Category middleCategory = new Category();
//            middleCategory.setName("Pants");
//
//            Category childCategory = new Category();
//            childCategory.setName("Jeans");
//
//            Category parentCategory = new Category();
//            parentCategory.setName("Clothes");
//
//            log.info("Preloading " + categoryRepository.save(childCategory));
//            log.info("Preloading " + categoryRepository.save(parentCategory));
//            log.info("Preloading " + categoryRepository.save(middleCategory));
//
//            childCategory.addParentCategory(middleCategory);
//            middleCategory.addParentCategory(parentCategory);
//
//            log.info("Preloading " + categoryRepository.save(childCategory));
//            log.info("Preloading " + categoryRepository.save(middleCategory));
        };
    }
}
