package org.fasttrackit.budgettrackerapi.transfer;

import javax.validation.constraints.NotBlank;

public class AddUser {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddUser{" +
                "name='" + name + '\'' +
                '}';
    }
}
