package org.fasttrackit.budgettrackerapi;

import org.fasttrackit.budgettrackerapi.domain.Income;
import org.fasttrackit.budgettrackerapi.domain.User;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.service.IncomeService;
import org.fasttrackit.budgettrackerapi.service.UserService;
import org.fasttrackit.budgettrackerapi.transfer.AddIncome;
import org.fasttrackit.budgettrackerapi.transfer.ShowIncomeRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateIncome;
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
public class IncomeServiceIntegrationTests {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserSteps userSteps;

    @Test
    public void testCreateUser_whenValidRequest_thenReturnUserWithId() throws ResourceNotFoundException {

        User user = userSteps.addUser();

        AddIncome incomeRequest = new AddIncome();
        incomeRequest.setUserId(user.getId());
        incomeRequest.setSource("Salary");
        incomeRequest.setAmount(5000);


        Income income = incomeService.addIncome(incomeRequest);

        //asigura-te ca valoarea nu e nula
        assertThat(income, notNullValue());
        assertThat(income.getId(), greaterThan(0L));

        assertThat(income.getUser(), notNullValue());
        assertThat(income.getSource(), is(incomeRequest.getSource()));
        assertThat(income.getAmount(), is(incomeRequest.getAmount()));
        assertThat(income.getUser().getId(), is(user.getId()));
    }


    // pt metoda M:1
   // @Autowired
   // private UserSteps userSteps;
     //facem testul pt metoda M:1 cu User, cum fac daca nu am ProductSteps ca si clasa??
//    @Test
//   public void testCreateIncome_whenValidRequest_thenReturnReview() {
//       User user = userSteps.addUser();  }



    // test pt metoda CREATE - ca sa vedem ca putem adauga o sursa de venit noua
    @Test
    public void  testCreatedIncome_whenValidRequest_thenReturnIncomeWithId() throws ResourceNotFoundException {

        Income income = addIncome();

        //asigura-te ca nu valoarea nu e nula
        assertThat(income, notNullValue());
        assertThat(income.getId(), greaterThan(0L));
    }
    private Income addIncome() throws ResourceNotFoundException {
        AddIncome incurredIncome = new AddIncome();
        incurredIncome.setSource("Salary");
        incurredIncome.setAmount(4000);

        return incomeService.addIncome(incurredIncome);
    }


    // test pt metoda de GET, vedem daca ne da o sursa de Venit dupa un anumit ID, putem incerca daca ne da ceva daca cerem V cu ID = 0 si nu ar trebui sa ne dea nimic pt ca ID-urile incep de la 1
    // 1. facem testul pozitiv
    @Test(expected = ResourceNotFoundException.class)
    public void testShowIncome_whenIncomeNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        incomeService.showIncome(0);

    }

    // 2. facem si testul negativ
    @Test //test inseamna ca ii putem da si Run separat, nu e doar o simpla metoda
    public void testShowIncome_whenExistingId_thenReturnRequestedIncome() throws ResourceNotFoundException {
        Income income = addIncome();

        Income retrievedIncome = incomeService.showIncome(income.getId());

        assertThat(retrievedIncome.getId(), is(income.getId()));
        assertThat(retrievedIncome.getSource(), is(income.getSource()));

    }

    // test UPDATE INCOME
    @Test
    public void testUpdateIncome_whenValidRequestWithAllFields_thenShowUpdatedIncome() throws ResourceNotFoundException {
        // testele sunt independente, deci trebuie sa incepem cu creare de produs pt ca nu putem updata daca nu avem macar unul
        Income createdIncome = addIncome();

        UpdateIncome incurred = new UpdateIncome();
        incurred.setSource(createdIncome.getSource() + " Edited");
        incurred.setAmount(createdIncome.getAmount() + 1500);


        Income updatedIncome = incomeService.updateIncome(createdIncome.getId(), incurred);

        assertThat(updatedIncome.getSource(), is(incurred.getSource()));
        assertThat(updatedIncome.getSource(), not(is(createdIncome.getSource())));


        assertThat(updatedIncome.getAmount(), is(incurred.getAmount()));

        assertThat(updatedIncome.getId(), is(createdIncome.getId()));

    }

    //implement also negative tests for update and tests for update only some of the fields

    //test pt DELETE

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteIncome_whenExistingId_thenIncomeisDeleted() throws ResourceNotFoundException {

        //prima data creez o sursa de venit noua, ca sa stiu ca am una ca sa pot sa sterg
        Income addIncome = addIncome();

        //apoi il sterg
        incomeService.deleteIncome(addIncome.getId());

        incomeService.showIncome(addIncome.getId());

    }

    @Test
    public void testShowIncome_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults() throws ResourceNotFoundException {

        Income addIncome = addIncome();

        ShowIncomeRequest request = new ShowIncomeRequest();
        request.setPartialSourceName("sal");
        request.setMinimumAmount(3800.0);
        request.setMaximumAmount(6800.6);

        Page<Income> incomes =
                incomeService.getIncomes(request, PageRequest.of(0, 10));

        assertThat(incomes.getTotalElements(), greaterThanOrEqualTo(1L));

    }

}


