package org.fasttrackit.budgettrackerapi;

import org.fasttrackit.budgettrackerapi.domain.Expense;
import org.fasttrackit.budgettrackerapi.service.ExpenseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetTrackerApiApplication.class, args);


	}
}
