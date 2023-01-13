package com.salesmanager.test.configuration;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;
import io.hypersistence.optimizer.hibernate.event.configuration.identifier.PooledSequenceOptimizerEvent;
import io.hypersistence.optimizer.hibernate.event.configuration.schema.SchemaGenerationEvent;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.salesmanager.core.business"})
@ImportResource("spring/test-shopizer-context.xml")
@EnableJpaRepositories(basePackages = "com.salesmanager.core.business.repositories")
@EntityScan(basePackages = "com.salesmanager.core.model")
public class ConfigurationTest {

    @Bean
	public HypersistenceOptimizer hypersistenceOptimizer(EntityManagerFactory entityManagerFactory) {
        return new HypersistenceOptimizer(
            new JpaConfig(
                entityManagerFactory
            )
            .setEventFilter(event -> {
                if(event instanceof SchemaGenerationEvent ||
                   event instanceof PooledSequenceOptimizerEvent) {
                    return false;
                }
                return true;
            })
        );
    }

}
