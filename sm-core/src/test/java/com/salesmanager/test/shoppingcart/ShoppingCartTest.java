package com.salesmanager.test.shoppingcart;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.event.Event;
import io.hypersistence.optimizer.hibernate.event.configuration.ConfigurationPropertyEvent;
import io.hypersistence.optimizer.hibernate.event.mapping.EntityMappingEvent;
import org.junit.Assert;
import org.junit.Test;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionType;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.business.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Test 
 * 
 * - Add a product to persistent shopping cart
 * - Retrieve an item from the persistent shopping cart
 * - Rebuild a shopping cart item after the product definition has been modified
 * @author Carl Samson
 *
 */
public class ShoppingCartTest extends com.salesmanager.test.common.AbstractSalesManagerCoreTestCase {

	@Autowired
	private HypersistenceOptimizer hypersistenceOptimizer;

	@Override
	public void init() throws ServiceException {
		List<Event> events = hypersistenceOptimizer.getEvents();

		List<Event> configurationPropertyEvents = events.stream()
			.filter(event -> event instanceof ConfigurationPropertyEvent)
			.collect(Collectors.toList());

		assertTrue(configurationPropertyEvents.isEmpty());

		Map<Class, List<Event>> entityMappingEventByEventTypeMap = events.stream()
			.filter(event -> event instanceof EntityMappingEvent)
			.map(e -> EntityMappingEvent.class.cast(e))
			.collect(Collectors.groupingBy(Event::getClass));

		//assertTrue(entityMappingEventByEventTypeMap.isEmpty());

		Map<Class, List<Event>> entityMappingEventByEntityClassTypeMap = events.stream()
			.filter(event -> event instanceof EntityMappingEvent)
			.map(e -> EntityMappingEvent.class.cast(e))
			.sorted(Comparator.comparing(EntityMappingEvent::getEntityName))
			.collect(Collectors.groupingBy(e -> e.getEntityClass(), () -> new LinkedHashMap<Class, List<Event>>(), toList()));

		//assertTrue(entityMappingEventByEntityClassTypeMap.isEmpty());

		hypersistenceOptimizer.getEvents().clear();

		super.init();
	}

	@Test
	public void performanceIssuesTest() {
		List<Event> events = hypersistenceOptimizer.getEvents();

		//assertTrue(events.isEmpty());
	}

	@Test
	public void createShoppingCart() throws Exception {

        MerchantStore store = merchantService.getByCode( MerchantStore.DEFAULT_STORE );
        
		
	    Language en = languageService.getByCode("en");


	    /** CATALOG CREATION **/
	    
	    ProductType generalType = productTypeService.getProductType(ProductType.GENERAL_TYPE);

	    /**
	     * Create the category
	     */
	    Category shirts = new Category();
	    shirts.setMerchantStore(store);
	    shirts.setCode("shirts");

	    CategoryDescription shirtsEnglishDescription = new CategoryDescription();
	    shirtsEnglishDescription.setName("Shirts");
	    shirtsEnglishDescription.setCategory(shirts);
	    shirtsEnglishDescription.setLanguage(en);

	    Set<CategoryDescription> descriptions = new HashSet<CategoryDescription>();
	    descriptions.add(shirtsEnglishDescription);


	    shirts.setDescriptions(descriptions);
	    categoryService.create(shirts);
	    
	    
	    /**
	     * Create a manufacturer
	     */
	    Manufacturer addidas = new Manufacturer();
	    addidas.setMerchantStore(store);
	    addidas.setCode("addidas");

	    ManufacturerDescription addidasDesc = new ManufacturerDescription();
	    addidasDesc.setLanguage(en);
	    addidasDesc.setManufacturer(addidas);
	    addidasDesc.setName("Addidas");
	    addidas.getDescriptions().add(addidasDesc);

	    manufacturerService.create(addidas);
	    
	    /**
	     * Create an option
	     */
	    ProductOption option = new ProductOption();
	    option.setMerchantStore(store);
	    option.setCode("color");
	    option.setProductOptionType(ProductOptionType.Radio.name());
	    
	    ProductOptionDescription optionDescription = new ProductOptionDescription();
	    optionDescription.setLanguage(en);
	    optionDescription.setName("Color");
	    optionDescription.setDescription("Item color");
	    optionDescription.setProductOption(option);
	    
	    option.getDescriptions().add(optionDescription);
	    
	    productOptionService.saveOrUpdate(option);
	    
	    
	    /** first option value **/
	    ProductOptionValue white = new ProductOptionValue();
	    white.setMerchantStore(store);
	    white.setCode("white");
	    
	    ProductOptionValueDescription whiteDescription = new ProductOptionValueDescription();
	    whiteDescription.setLanguage(en);
	    whiteDescription.setName("White");
	    whiteDescription.setDescription("White color");
	    whiteDescription.setProductOptionValue(white);
	    
	    white.getDescriptions().add(whiteDescription);
	    
	    productOptionValueService.saveOrUpdate(white);
	    
	    
	    ProductOptionValue black = new ProductOptionValue();
	    black.setMerchantStore(store);
	    black.setCode("black");
	    
	    /** second option value **/
	    ProductOptionValueDescription blackDesc = new ProductOptionValueDescription();
	    blackDesc.setLanguage(en);
	    blackDesc.setName("Black");
	    blackDesc.setDescription("Black color");
	    blackDesc.setProductOptionValue(black);
	    
	    black.getDescriptions().add(blackDesc);

	    productOptionValueService.saveOrUpdate(black);
	    
	    
	    /**
	     * Create a complex product
	     */
	    Product product = new Product();
	    product.setProductHeight(new BigDecimal(4));
	    product.setProductLength(new BigDecimal(3));
	    product.setProductWidth(new BigDecimal(1));
	    product.setSku("XABC12");
	    product.setManufacturer(addidas);
	    product.setType(generalType);
	    product.setMerchantStore(store);

	    // Product description
	    ProductDescription description = new ProductDescription();
	    description.setName("Short sleeves shirt");
	    description.setLanguage(en);
	    description.setProduct(product);

	    product.getDescriptions().add(description);
	    product.getCategories().add(shirts);
	    
	    
	    //availability
	    ProductAvailability availability = new ProductAvailability();
	    availability.setProductDateAvailable(new Date());
	    availability.setProductQuantity(100);
	    availability.setRegion("*");
	    availability.setProduct(product);// associate with product
	    
	    //price
	    ProductPrice dprice = new ProductPrice();
	    dprice.setDefaultPrice(true);
	    dprice.setProductPriceAmount(new BigDecimal(29.99));
	    dprice.setProductAvailability(availability);
	    
	    

	    ProductPriceDescription dpd = new ProductPriceDescription();
	    dpd.setName("Base price");
	    dpd.setProductPrice(dprice);
	    dpd.setLanguage(en);

	    dprice.getDescriptions().add(dpd);
	    availability.getPrices().add(dprice);
	    product.getAvailabilities().add(availability);
	    
	    
	    //attributes
	    //white
	    ProductAttribute whiteAttribute = new ProductAttribute();
	    whiteAttribute.setProduct(product);
	    whiteAttribute.setProductOption(option);
	    whiteAttribute.setAttributeDefault(true);
	    whiteAttribute.setProductAttributePrice(new BigDecimal(0));//no price variation
	    whiteAttribute.setProductAttributeWeight(new BigDecimal(0));//no weight variation
	    whiteAttribute.setProductOption(option);
	    whiteAttribute.setProductOptionValue(white);
	    
	    product.getAttributes().add(whiteAttribute);
	    //black
	    ProductAttribute blackAttribute = new ProductAttribute();
	    blackAttribute.setProduct(product);
	    blackAttribute.setProductOption(option);
	    blackAttribute.setProductAttributePrice(new BigDecimal(5));//5 + dollars
	    blackAttribute.setProductAttributeWeight(new BigDecimal(0));//no weight variation
	    blackAttribute.setProductOption(option);
	    blackAttribute.setProductOptionValue(black);
	    
	    product.getAttributes().add(blackAttribute);

	    productService.saveProduct(product);
	    
	    /** Create Shopping cart **/
	    
	    ShoppingCart shoppingCart = new ShoppingCart();
	    shoppingCart.setMerchantStore(store);
	    
	    UUID cartCode = UUID.randomUUID();
	    shoppingCart.setShoppingCartCode(cartCode.toString());

	    ShoppingCartItem item = new ShoppingCartItem(shoppingCart,product);
	    item.setSku(product.getSku());
	    item.setShoppingCart(shoppingCart);
	    
	    FinalPrice price = pricingService.calculateProductPrice(product);

	    item.setItemPrice(price.getFinalPrice());
	    item.setQuantity(1);
	    
	    /** user selects black **/
	    ShoppingCartAttributeItem attributeItem = new ShoppingCartAttributeItem(item,blackAttribute);
	    item.getAttributes().add(attributeItem);
	    
	    shoppingCart.getLineItems().add(item);

	    
	    //create cart
	    shoppingCartService.create(shoppingCart);

	    
	    /** Retrieve cart **/

	    ShoppingCart retrievedCart = shoppingCartService.getByCode(cartCode.toString(), store);
	    
	    Assert.assertNotNull(retrievedCart);
	    
	    /** Delete cart **/
	    shoppingCartService.delete(retrievedCart);
	    
	    /** Check if cart has been deleted **/
	    retrievedCart = shoppingCartService.getByCode(cartCode.toString(), store);
	    
	    Assert.assertNull(retrievedCart);

		// Clean up for other tests
	    categoryService.delete(shirts);
	    
	}
	

}