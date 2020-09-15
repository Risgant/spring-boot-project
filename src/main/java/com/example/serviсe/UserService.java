package com.example.serviсe;

import com.example.model.User;

import java.util.List;

/**
 * The {@link UserService} interface in my application uses for get access to database
 * entities, that the class  {@link User} corresponds
 *
 * The {@link UserService} interface provides one method for access to {@link User}
 * elements by its primary key
 *
 * The {@link UserService} interface provides one method for access to  all
 * {@link User} elements
 */
public interface UserService {
    /**
     * Returns the {@link List} of {@link User} elements
     *
     * @return the {@link List} of {@link User} elements
     */
    List<User> findAll();

    /**
     * Returns the {@link User} element by its primary key
     *
     * @param id the primary key primary key of the {@link User} entity
     * @return the {@link User} element by its primary key
     */
    User findById(int id);
}
