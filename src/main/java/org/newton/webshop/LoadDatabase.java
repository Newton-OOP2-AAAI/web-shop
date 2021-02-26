package org.newton.webshop;

import org.newton.webshop.models.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner InitDatabase(ProductRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Product("T-shirt", 199)));
            log.info("Preloading " + repository.save(new Product("Jeans", 599)));
            log.info("Preloading " + repository.save(new Product("Hat", 299)));
        };
    }
}
