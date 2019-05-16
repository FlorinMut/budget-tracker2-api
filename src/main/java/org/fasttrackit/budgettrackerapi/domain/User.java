package org.fasttrackit.budgettrackerapi.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    //1:M relation - one User can have 1 or more incomes
//    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Income> incomes = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
