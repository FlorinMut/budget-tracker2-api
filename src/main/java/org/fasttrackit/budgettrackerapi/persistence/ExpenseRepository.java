package org.fasttrackit.budgettrackerapi.persistence;

import org.fasttrackit.budgettrackerapi.domain.Expense;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is the wrapper class for primitive long
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    Page<Expense> findByNameContainingAndQuantityGreaterThanEqual(
            String partialName, int minimumQuantity, Pageable pageable);

    Page<Expense> findByAmountBetweenAndQuantityGreaterThanEqual(
            double minimumPrice, double maximumPrice, int minimumQuantity, Pageable pageable);

    Page<Expense> findByNameContainingAndAmountBetweenAndQuantityGreaterThanEqual(
            String partialName, double minimumPrice, double maximumPrice, int minimumQuantity, Pageable pageable);

    Page<Expense> findByNameContaining(String partialName, Pageable pageable);

}
