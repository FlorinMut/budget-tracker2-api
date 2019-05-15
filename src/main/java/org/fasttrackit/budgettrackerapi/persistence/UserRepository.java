package org.fasttrackit.budgettrackerapi.persistence;

import org.fasttrackit.budgettrackerapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is the wrapper class for primitive long
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> findByNameContaining(String partialName, Pageable pageable);

}
