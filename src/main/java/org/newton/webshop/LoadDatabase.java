package org.newton.webshop;

import org.newton.webshop.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//            /**
//             * Sample data for three categories
//             */
//            Category pantsCategory = new Category();
//            pantsCategory.setName("Pants");
//
//            Category jeansCategory = new Category();
//            jeansCategory.setName("Jeans");
//
//            Category clothesCategory = new Category();
//            clothesCategory.setName("Clothes");
//
//            log.info("Preloading " + categoryRepository.save(jeansCategory));
//            log.info("Preloading " + categoryRepository.save(clothesCategory));
//            log.info("Preloading " + categoryRepository.save(pantsCategory));
//
//            jeansCategory.addParentCategory(pantsCategory);
//            pantsCategory.addParentCategory(clothesCategory);
//
//            log.info("Preloading " + categoryRepository.save(jeansCategory));
//            log.info("Preloading " + categoryRepository.save(pantsCategory));
//
//            /**
//             * Sample data for two products (doesn't work rn)
//             */
//            Product levisJeans = new Product();
//            levisJeans.setDescription("Cool Levis jeans in a traditional style!");
//            levisJeans.setName("Levis Jeans");
//            levisJeans.setPrice(new BigDecimal("1000.00"));
//            levisJeans.setVisible(true);
//
//            jeansCategory.addProduct(levisJeans);
        };
    }
}
