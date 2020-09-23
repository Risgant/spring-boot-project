package com.example.repository;

import com.example.model.Product;
import com.example.serviсe.OrderService;
import com.example.serviсe.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
/*@AllArgsConstructor(onConstructor = @__(@Autowired))*/
@ActiveProfiles("test")
class ProductOrderTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @MockBean
    Product product;

    @Test
    public void getFirstPageProduct(){
        Assertions.assertEquals(productService.findPage(0).size(), 5);
    }

    @Test
    public void createProductTest(){
        productService.create(product);
        Assertions.assertTrue(productRepository.findById(product.getId()).isPresent());
    }

}
