package org.fasttrackit.budgettrackerapi;

import org.fasttrackit.budgettrackerapi.domain.User;
import org.fasttrackit.budgettrackerapi.exception.ResourceNotFoundException;
import org.fasttrackit.budgettrackerapi.service.UserService;
import org.fasttrackit.budgettrackerapi.transfer.AddUser;
import org.fasttrackit.budgettrackerapi.transfer.ShowUserRequest;
import org.fasttrackit.budgettrackerapi.transfer.UpdateUser;
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
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;


    // test pt metoda CREATE - ca sa vedem ca putem adauga un User nou
    @Test
    public void  testCreatedUser_whenValidRequest_thenReturnUserWithId(){

        User user = addUser();

        //asigura-te ca valoarea nu e nula
        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
    }
    private User addUser() {
        AddUser incurredUser = new AddUser();
        incurredUser.setName("Florin");

        return userService.addUser(incurredUser);
    }


    // test pt metoda de GET, vedem daca ne da un User dupa un anumit ID
    @Test(expected = ResourceNotFoundException.class)
    public void testShowUser_whenUserNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        userService.showUser(0);

    }

    // 2. facem si testul negativ
    @Test //test inseamna ca ii putem da si Run separat, nu e doar o simpla metoda
    public void testShowUser_whenExistingId_thenReturnRequestedUser() throws ResourceNotFoundException {
        User user = addUser();

        User retrievedUser = userService.showUser(user.getId());

        assertThat(retrievedUser.getId(), is(user.getId()));
        assertThat(retrievedUser.getName(), is(user.getName()));

    }

    // test UPDATE EXPENSE
    @Test
    public void testUpdateUser_whenValidRequestWithAllFields_thenShowUpdatedUser() throws ResourceNotFoundException {
        // testele sunt independente, deci trebuie sa incepem cu creare de produs pt ca nu putem updata daca nu avem macar unul
        User createdUser = addUser();

        UpdateUser incurred = new UpdateUser();
        incurred.setName(createdUser.getName() + " Edited");

        User updatedUser = userService.updateUser(createdUser.getId(), incurred);

        assertThat(updatedUser.getName(), is(incurred.getName()));
        assertThat(updatedUser.getName(), not(is(createdUser.getName())));

        assertThat(updatedUser.getId(), is(createdUser.getId()));

    }

    //implement also negative tests for update and tests for update only some of the fields

    //test pt DELETE

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUser_whenExistingId_thenUserisDeleted() throws ResourceNotFoundException {

        //prima data creez o cheltuiala noua, ca sa stiu ca am unul ca sa pot sa sterg
        User addUser = addUser();

        //apoi il sterg
        userService.deleteUser(addUser.getId());

        userService.showUser(addUser.getId());

    }

    @Test
    public void testShowUser_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults() {

        User addUser = addUser();

        ShowUserRequest request = new ShowUserRequest();
        request.setPartialName("Flo");


        Page<User> users =
                userService.getUsers(request, PageRequest.of(0, 10));

        // acest assert trebuie imbunatatit mult, pt orice ch din raspuns assert that all criteria are matched
        assertThat(users.getTotalElements(), greaterThanOrEqualTo(1L));

    }

}


