package com.example.repository;

import com.example.model.Product;
import com.example.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Optional;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ActiveProfiles("test")
class UserRepositoryTest {
    private UserRepository userRepository;
    private ProductRepository productRepository;


    @Test
    void testFindAllUsers(){
        Assertions.assertEquals(userRepository.findAll().size(), 6);
    }

    @Test
    void testFindUser(){
        User user = testFind(userRepository);
        Assertions.assertEquals(user.getId(), Integer.valueOf(1));
        Assertions.assertEquals(user.getName(), "Tom");
    }

    @Test
    public void testFindAllProducts(){
        Assertions.assertEquals(productRepository.findAll().size(), 5);
    }

    @Test
    public void testFindProduct(){
        Product product = testFind(productRepository);
        Assertions.assertEquals(product.getId(), Integer.valueOf(1));
        Assertions.assertEquals(product.getTitle(), "Salt");
        Assertions.assertEquals(product.getPrice(), Double.valueOf(2.0));
    }

    private<T> T testFind(JpaRepository<T, Integer> repository){
        Optional<T> op = repository.findById(1);
        Assertions.assertTrue(op.isPresent());
        return op.get();
    }
}
