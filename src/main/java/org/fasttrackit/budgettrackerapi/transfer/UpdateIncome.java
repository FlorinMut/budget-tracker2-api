package org.fasttrackit.budgettrackerapi.transfer;

import javax.validation.constraints.NotBlank;

public class UpdateIncome {

    @NotBlank
    private String source;
    private double amount;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UpdateIncome{" +
                "source='" + source + '\'' +
                ", amount=" + amount +
                '}';
    }
}
