package org.fasttrackit.budgettrackerapi.transfer;

import javax.validation.constraints.NotBlank;

public class UpdateUser {

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
        return "UpdateUser{" +
                "name='" + name + '\'' +
                '}';
    }
}
