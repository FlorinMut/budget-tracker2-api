package org.fasttrackit.budgettrackerapi.persistence;

import org.fasttrackit.budgettrackerapi.domain.Income;
import org.fasttrackit.budgettrackerapi.domain.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is the wrapper class for primitive long
public interface IncomeRepository extends JpaRepository<Income, Long> { //ar fi trebuit JPA Repository sau PagingandSorting?

    //face findBy-ul pt relatia M:1 cu Userul
   // Page<Income> findByUserId(long userId, Pageable pageable); //trebuie sa o facem si in service


    Page<Income> findBySourceContainingAndAmountBetween(
            String partialSourceName, double minimumAmount, double maximumAmount, Pageable pageable);

    Page<Income> findByAmountBetween(
            double minimumAmount, double maximumAmount, Pageable pageable);

}
