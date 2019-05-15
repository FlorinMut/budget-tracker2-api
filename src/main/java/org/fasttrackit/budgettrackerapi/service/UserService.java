package org.fasttrackit.budgettrackerapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.budgettrackerapi.domain.User;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.persistence.UserRepository;
import org.fasttrackit.budgettrackerapi.transfer.AddUser;
import org.fasttrackit.budgettrackerapi.transfer.ShowUserRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    // Incepem sa facem metodele de CRUD


    // 1. Create - adica adaugam un user in BD
    public User addUser(AddUser incurring) {
        LOGGER.info("Adding user {}", incurring);
        User user = objectMapper.convertValue(incurring, User.class);
        return userRepository.save(user);
    }

    // 2. Read - adica ne afisam un User din BD si daca nu gaseste sa arunce o exceptie
    public User showUser(long id) throws ResourceNotFoundException {
        LOGGER.info("Showing user {}", id);
        return userRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));

    }

    // o metoda care ne aduce m multi Useri dupa m multe criterii

    public Page<User> getUsers(ShowUserRequest request, Pageable pageable) {
        LOGGER.info("Retrieving users >> ", request);

        if (request.getPartialName() != null) {

        }

        return userRepository.findAll(pageable);

    }


    // 3. Update
    public User updateUser (long id, UpdateUser incurred) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, incurred);
        User user = showUser(id);

        BeanUtils.copyProperties(incurred, user);

        return userRepository.save(user);
    }

    // 4. Delete
    public void deleteUser(long id) {
        LOGGER.info("Deleting product {}", id);
        userRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);

    }
}
