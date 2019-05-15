package org.fasttrackit.budgettrackerapi.web;

import org.fasttrackit.budgettrackerapi.domain.Expense;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.service.ExpenseService;
import org.fasttrackit.budgettrackerapi.transfer.AddExpense;
import org.fasttrackit.budgettrackerapi.transfer.ShowExpenseRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateExpense;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//import sun.security.util.PropertyExpander;


// FACEM un servlet
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    // 1. Endpoint de GET - facem un Endpoint care sa ne dea o ch dupa ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> showExpense(@PathVariable("id") long id) throws ResourceNotFoundException {
        Expense response = expenseService.showExpense(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 2. Endpoint de CREATE - un alt Endpoint - de create
    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody @Valid AddExpense incurred) {

        Expense reponse = expenseService.addExpense(incurred);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);

    }

    // 3. Endpoint de UPDATE
    @PutMapping("/{id}")
    public ResponseEntity updateExpense(@PathVariable("id") long id,
                                        @RequestBody @Valid UpdateExpense incurred) throws ResourceNotFoundException {

        expenseService.updateExpense(id, incurred);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 4. Endpoint de DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity deleteExpense(@PathVariable("id") long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 5. Endpoint de GET cu filtrare dupa anumite criterii
    @GetMapping
    public ResponseEntity<Page<Expense>>  showExpenses(@Valid ShowExpenseRequest request, Pageable pageable) {

        Page<Expense> response = expenseService.getExpenses(request, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
