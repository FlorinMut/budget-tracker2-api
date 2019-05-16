package org.fasttrackit.budgettrackerapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.budgettrackerapi.domain.Income;
import org.fasttrackit.budgettrackerapi.domain.User;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.persistence.IncomeRepository;
import org.fasttrackit.budgettrackerapi.transfer.AddIncome;
import org.fasttrackit.budgettrackerapi.transfer.AddUser;
import org.fasttrackit.budgettrackerapi.transfer.ShowIncomeRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateIncome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IncomeService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(IncomeService.class);

    private final IncomeRepository incomeRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, UserService userService, ObjectMapper objectMapper) {
        this.incomeRepository = incomeRepository;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    // Pt relatia M:1, cream o metoda care sa ne adauge un Income
    @Transactional
    public Income addIncome(AddIncome incurring) throws ResourceNotFoundException {
        User user = userService.showUser(incurring.getUserId());

        Income income = new Income();
        income.setAmount(incurring.getAmount());
        income.setSource(incurring.getSource());
        income.setUser(user);

        return incomeRepository.save(income);
    }


    // Incepem sa facem metodele de CRUD

     //1. Create - adica adaugam un Income in BD
//    public Income addIncome(AddIncome incurring) {
//        LOGGER.info("Adding income {}", incurring);
//        Income income = objectMapper.convertValue(incurring, Income.class);
//        return incomeRepository.save(income);
//    }

    // 2. Read - adica ne afisam una dintre veniturile din BD in fct de un criteriu dorit si daca nu gaseste, sa arunce o exceptie
    public Income showIncome(long id) throws ResourceNotFoundException {
        LOGGER.info("Showing income {}", id);
        return incomeRepository.findById(id)
                // Optional & lambda expression
                .orElseThrow(() -> new ResourceNotFoundException("Income " + id + " not found"));

    }

//   public Page<Income> getIncomes(AddUser request, Pageable pageable) {
//        LOGGER.info("Retrieving incomes >> ", request);
//
//        if (request.getUserId != null) {
//
//        return incomeRepository.findAll(pageable); }



    // o metoda care ne aduce m multe venituri dupa m multe criterii

    public Page<Income> getIncomes(ShowIncomeRequest request, Pageable pageable) {
        LOGGER.info("Retrieving incomes >> ", request);

        if (request.getPartialSourceName() != null &&
                request.getMinimumAmount() != null &&
                request.getMaximumAmount() != null) {

            return incomeRepository.findBySourceContainingAndAmountBetween(
                    request.getPartialSourceName(), request.getMinimumAmount(),
                    request.getMaximumAmount(), pageable);

        } else if (request.getMinimumAmount() != null
                && request.getMaximumAmount() != null) {

            return incomeRepository.findByAmountBetween(
                    request.getMinimumAmount(), request.getMaximumAmount(), pageable);

        }

        return incomeRepository.findAll(pageable);

    }


    // 3. Update
    public Income updateIncome (long id, UpdateIncome incurred) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, incurred);
        Income income = showIncome(id);

        BeanUtils.copyProperties(incurred, income);

        return incomeRepository.save(income);
    }

    // 4. Delete
    public void deleteIncome(long id) {
        LOGGER.info("Deleting product {}", id);
        incomeRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);

    }
}
