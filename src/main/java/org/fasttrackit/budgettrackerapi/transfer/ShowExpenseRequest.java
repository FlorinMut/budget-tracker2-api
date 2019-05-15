package org.fasttrackit.budgettrackerapi.transfer;

public class ShowExpenseRequest {

    private String partialName;
    private Double minimumAmount;
    private Double maximumAmount;
    private Integer minimumQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public Double getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(Double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQunatity) {
        this.minimumQuantity = minimumQunatity;
    }

    @Override
    public String toString() {
        return "ShowExpenseRequest{" +
                "partialName='" + partialName + '\'' +
                ", minimumAmount=" + minimumAmount +
                ", maximumAmount=" + maximumAmount +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}
