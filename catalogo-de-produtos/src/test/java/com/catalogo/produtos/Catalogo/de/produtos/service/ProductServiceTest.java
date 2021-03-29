package com.catalogo.produtos.Catalogo.de.produtos.service;

import com.catalogo.produtos.Catalogo.de.produtos.models.Product;;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {

    List<Product> products = new ArrayList<>();

    @Autowired
    private ProductService productService;

    @Before
    public void createProducts() {
        Product product = Product
                .builder()
                .name("Product 1")
                .description("Product 1")
                .price(BigDecimal.valueOf(2354.43)).build();

        Product product2 = Product
                .builder()
                .name("Product 2")
                .description("Product 2")
                .price(BigDecimal.valueOf(354.43)).build();
        products.add(product);
        products.add(product2);
    }

    @Test
    public void createProductsTest() {
        List<Product> productsSaved = new ArrayList<>();
        products.forEach(product -> {
            Product productSaved = productService.create(product);
            if (product != null) {
                productsSaved.add(productSaved);
            }
        });
        products.get(0).setId(1L);
        products.get(1).setId(2L);
        compareArrays(productsSaved, products);
    }

    @Test
    public void findByIdTest() {
        Product product = products.get(0);
        product.setId(1L);
        Product productFound = productService.findById(1L);
        assertThat(product, samePropertyValuesAs(productFound));
    }

    @Test
    public void findTest() {
        List<Product> productsFound = productService.find();
        products.get(0).setId(1L);
        products.get(1).setId(2L);
        compareArrays(productsFound, products);
    }

    @Test
    public void searchTest() {
        Long min_price = 355L;
        Long max_price = null;
        String q = null;
        List<Product> productTestOne = productService.search(min_price, max_price, q);
        Assert.assertEquals(productTestOne.size(), 1);
        assertEquals(products.get(0).getName(), productTestOne.get(0).getName());

        min_price = null;
        max_price = 355L;
        List<Product> productTestTwo = productService.search(min_price, max_price, q);
        Assert.assertEquals(productTestTwo.size(), 1);
        assertEquals(products.get(1).getName(), productTestTwo.get(0).getName());

        min_price = null;
        max_price = null;
        q = "Product 2";
        List<Product> productTestThree = productService.search(min_price, max_price, q);
        Assert.assertEquals(productTestThree.size(), 1);
        assertEquals(products.get(1).getName(), productTestThree.get(0).getName());

        min_price = 20L;
        max_price = 10000L;
        q = null;
        List<Product> productTestFour = productService.search(min_price, max_price, q);
        Assert.assertEquals(productTestFour.size(), 2);

        min_price = 20L;
        max_price = 10000L;;
        q = "Product 2";
        List<Product> productTestFive = productService.search(min_price, max_price, q);
        Assert.assertEquals(productTestFive.size(), 1);
        assertEquals(products.get(1).getName(), productTestFive.get(0).getName());
    }

    private void compareArrays(List<Product> productsFound, List<Product> originalProducts) {
        assertEquals(productsFound.size(), originalProducts.size());
        IntStream.range(0, productsFound.size())
                .forEach(idx -> assertThat(originalProducts.get(idx), samePropertyValuesAs(productsFound.get(idx))))
        ;
    }
}
