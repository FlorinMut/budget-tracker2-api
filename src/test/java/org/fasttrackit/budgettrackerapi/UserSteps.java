package org.fasttrackit.budgettrackerapi;

import org.fasttrackit.budgettrackerapi.domain.User;
import org.fasttrackit.budgettrackerapi.service.UserService;
import org.fasttrackit.budgettrackerapi.transfer.AddUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSteps { //clasa pt relatia M:1

    @Autowired
    private UserService userService;

    public User addUser() {

        AddUser incurring = new AddUser();
        incurring.setName("Flo");

        return userService.addUser(incurring);

    }

}
