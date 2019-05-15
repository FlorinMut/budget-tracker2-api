package org.fasttrackit.budgettrackerapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.budgettrackerapi.domain.Expense;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.persistence.ExpenseRepository;
import org.fasttrackit.budgettrackerapi.transfer.AddExpense;
import org.fasttrackit.budgettrackerapi.transfer.ShowExpenseRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateExpense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, ObjectMapper objectMapper) {
        this.expenseRepository = expenseRepository;
        this.objectMapper = objectMapper;
    }

    // Incepem sa facem metodele de CRUD


    // 1. Create - adica adaugam o cheltuiala in BD
    public Expense addExpense(AddExpense incurring) {
        LOGGER.info("Adding expense {}", incurring);
        Expense expense = objectMapper.convertValue(incurring, Expense.class);
        return expenseRepository.save(expense);
    }

    // 2. Read - adica ne afisam una dintre cheltuielile din BD in fct de un criteriu dorit si daca nu gaseste, sa arunce o exceptie
    public Expense showExpense(long id) throws ResourceNotFoundException {
        LOGGER.info("Showing expense {}", id);
        return expenseRepository.findById(id)
                // Optional & lambda expression
                .orElseThrow(() -> new ResourceNotFoundException("Expense " + id + " not found"));

    }

    // o metoda care ne aduce m multe ch dupa m multe criterii

    public Page<Expense> getExpenses(ShowExpenseRequest request, Pageable pageable) {
        LOGGER.info("Retrieving expenses >> ", request);

        if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null &&
                request.getMinimumAmount() != null &&
                request.getMaximumAmount() != null) {

            return expenseRepository.findByNameContainingAndAmountBetweenAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumAmount(),
                    request.getMaximumAmount(), request.getMinimumQuantity(), pageable);

        } else if (request.getMinimumAmount() != null
                && request.getMaximumAmount() != null
                && request.getMinimumQuantity() != null) {

            return expenseRepository.findByAmountBetweenAndQuantityGreaterThanEqual(
                    request.getMinimumAmount(), request.getMaximumAmount(),
                    request.getMinimumQuantity(), pageable);


        } else if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null) {
            return  expenseRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumQuantity(), pageable);
        }

        return expenseRepository.findAll(pageable);

    }


    // 3. Update
    public Expense updateExpense (long id, UpdateExpense incurred) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, incurred);
        Expense expense = showExpense(id);

        BeanUtils.copyProperties(incurred, expense);

        return expenseRepository.save(expense);
    }

    // 4. Delete
    public void deleteExpense(long id) {
        LOGGER.info("Deleting product {}", id);
        expenseRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);

    }
}
