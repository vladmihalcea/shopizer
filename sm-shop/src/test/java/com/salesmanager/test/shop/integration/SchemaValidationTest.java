package com.salesmanager.test.shop.integration;

import com.salesmanager.shop.application.ShopApplication;
import io.hypersistence.utils.hibernate.util.ReflectionUtils;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.tool.schema.internal.ExceptionHandlerHaltImpl;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaManagementTool;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.hibernate.tool.schema.spi.SchemaValidator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SchemaValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaValidationTest.class);

    @Autowired
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

    private Map<String, Object> settings;

    private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    private MetadataImplementor metadataImplementor;

    @PostConstruct
    public void init() {
        settings = localContainerEntityManagerFactoryBean.getJpaPropertyMap();
        entityManagerFactoryBuilder = Bootstrap.getEntityManagerFactoryBuilder(
            localContainerEntityManagerFactoryBean.getPersistenceUnitInfo(),
            settings
        );
        metadataImplementor = ReflectionUtils.invokeMethod(entityManagerFactoryBuilder, "metadata");
    }

    @Test
    public void testValidate() {
        try(SessionFactoryImplementor sessionFactory = entityManagerFactoryBuilder
            .build()
            .unwrap(SessionFactoryImplementor.class)) {

            SchemaValidator schemaValidator = getSchemaValidator(sessionFactory);

            final ExecutionOptions executionOptions = SchemaManagementToolCoordinator.buildExecutionOptions(
                settings,
                ExceptionHandlerHaltImpl.INSTANCE
            );

            long startNanos = System.nanoTime();
            schemaValidator.doValidation(
                metadataImplementor,
                executionOptions
            );
            LOGGER.info(
                "Schema validation took: [{}] ms",
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos)
            );
        }
    }

    private SchemaValidator getSchemaValidator(SessionFactoryImplementor sessionFactory) {
        ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
        SchemaManagementTool tool = serviceRegistry.getService(SchemaManagementTool.class);
        Map<String,Object> options = Collections.emptyMap();
        return tool.getSchemaValidator(options);
    }
}
