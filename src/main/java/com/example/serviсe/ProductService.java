package com.example.serviсe;

import com.example.model.Product;

import java.util.List;

///**
// * The {@link ProductService} interface in my application uses for get access to database
// * entities, that the class  {@link Product} corresponds
// *
// * The {@link ProductService} interface provides one method for access to {@link Product}
// * elements by its primary key
// *
// * The {@link ProductService} interface provides one method for access to  all
// * {@link Product} elements
// */
public interface ProductService {

    /**
     * Returns the {@link List} of {@link Product} elements
     *
     * @return the {@link List} of {@link Product} elements
     */
    List<Product> findAll();

    void create(Product product);

    void update(Product product);

    void delete(Product product);

//    /**
//     * Returns the {@link Product} element by its primary key
//     *
//     * @param id the primary key primary key of the {@link Product} entity
//     * @return the {@link Product} element by its primary key
//     */
//    Product findById(int id);
}
