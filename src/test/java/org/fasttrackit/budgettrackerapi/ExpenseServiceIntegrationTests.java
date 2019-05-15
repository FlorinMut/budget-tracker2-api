package org.fasttrackit.budgettrackerapi;

import org.fasttrackit.budgettrackerapi.domain.Expense;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.service.ExpenseService;
import org.fasttrackit.budgettrackerapi.transfer.AddExpense;
import org.fasttrackit.budgettrackerapi.transfer.ShowExpenseRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateExpense;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenseServiceIntegrationTests {

    @Autowired
    private ExpenseService expenseService;


    // test pt metoda CREATE - ca sa vedem ca putem adauga o cheltuiala noua
    @Test
    public void  testCreatedExpense_whenValidRequest_thenReturnExpenseWithId(){

        Expense expense = addExpense();

        //asigura-te ca nu valoarea nu e nula
        assertThat(expense, notNullValue());
        assertThat(expense.getId(), greaterThan(0L));
    }
    private Expense addExpense() {
        AddExpense incurredExpense = new AddExpense();
        incurredExpense.setName("Rent");
        incurredExpense.setAmount(670);
        incurredExpense.setQuantity(1);

        return expenseService.addExpense(incurredExpense);
    }


    // test pt metoda de GET, vedem daca ne da Ch dupa un anumit ID, putem incerca daca ne da ceva daca cerem Ch cu ID = 0 si nu ar trebui sa ne dea nimic pt ca ID-urile incep de la 1
    // 1. facem testul pozitiv
    @Test(expected = ResourceNotFoundException.class)
    public void testShowExpense_whenExpenseNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        expenseService.showExpense(0);

    }

    // 2. facem si testul negativ
    @Test //test inseamna ca ii putem da si Run separat, nu e doar o simpla metoda
    public void testShowExpense_whenExistingId_thenReturnRequestedExpense() throws ResourceNotFoundException {
        Expense expense = addExpense();

        Expense retrievedExpense = expenseService.showExpense(expense.getId());

        assertThat(retrievedExpense.getId(), is(expense.getId()));
        assertThat(retrievedExpense.getName(), is(expense.getName()));

    }

    // test UPDATE EXPENSE
    @Test
    public void testUpdateExpense_whenValidRequestWithAllFields_thenShowUpdatedExpense() throws ResourceNotFoundException {
        // testele sunt independente, deci trebuie sa incepem cu creare de produs pt ca nu putem updata daca nu avem macar unul
        Expense createdExpense = addExpense();

        UpdateExpense incurred = new UpdateExpense();
        incurred.setName(createdExpense.getName() + " Edited");
        incurred.setAmount(createdExpense.getAmount() + 10);
        incurred.setQuantity(createdExpense.getQuantity() + 1);


        Expense updatedExpense = expenseService.updateExpense(createdExpense.getId(), incurred);

        assertThat(updatedExpense.getName(), is(incurred.getName()));
        assertThat(updatedExpense.getName(), not(is(createdExpense.getName())));


        assertThat(updatedExpense.getAmount(), is(incurred.getAmount()));
        assertThat(updatedExpense.getQuantity(), is(incurred.getQuantity()));

        assertThat(updatedExpense.getId(), is(createdExpense.getId()));

    }

    //implement also negative tests for update and tests for update only some of the fields

    //test pt DELETE

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteExpense_whenExistingId_thenExpenseisDeleted() throws ResourceNotFoundException {

        //prima data creez o cheltuiala noua, ca sa stiu ca am unul ca sa pot sa sterg
        Expense addExpense = addExpense();

        //apoi il sterg
        expenseService.deleteExpense(addExpense.getId());

        expenseService.showExpense(addExpense.getId());

    }

    @Test
    public void testShowExpense_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults() {

        Expense addExpense = addExpense();

        ShowExpenseRequest request = new ShowExpenseRequest();
        request.setPartialName("ren");
        request.setMinimumAmount(400.0);
        request.setMaximumAmount(800.6);
        request.setMinimumQuantity(1);

        Page<Expense> expenses =
                expenseService.getExpenses(request, PageRequest.of(0, 10));

        // acest assert trebuie imbunatatit mult, pt orice ch din raspuns assert that all criteria are matched
        assertThat(expenses.getTotalElements(), greaterThanOrEqualTo(1L));

    }

}


