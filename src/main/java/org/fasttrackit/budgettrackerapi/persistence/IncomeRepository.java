package org.fasttrackit.budgettrackerapi.persistence;

import org.fasttrackit.budgettrackerapi.domain.Income;
import org.fasttrackit.budgettrackerapi.domain.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is the wrapper class for primitive long
public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {

    Page<Income> findBySourceContainingAndAmountBetween(
            String partialSourceName, double minimumAmount, double maximumAmount, Pageable pageable);

    Page<Income> findByAmountBetween(
            double minimumAmount, double maximumAmount, Pageable pageable);

}
