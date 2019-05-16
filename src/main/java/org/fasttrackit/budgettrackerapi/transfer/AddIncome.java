package org.fasttrackit.budgettrackerapi.transfer;

import javax.validation.constraints.NotBlank;

public class AddIncome {

    @NotBlank
    private String source;
    private double amount;
    private long userId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AddIncome{" +
                "source='" + source + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                '}';
    }
}
