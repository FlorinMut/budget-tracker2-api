package org.fasttrackit.budgettrackerapi.transfer;

public class ShowIncomeRequest {

    private String partialSourceName;
    private Double minimumAmount;
    private Double maximumAmount;

    public String getPartialSourceName() {
        return partialSourceName;
    }

    public void setPartialSourceName(String partialSourceName) {
        this.partialSourceName = partialSourceName;
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

    @Override
    public String toString() {
        return "ShowIncomeRequest{" +
                "partialSourceName='" + partialSourceName + '\'' +
                ", minimumAmount=" + minimumAmount +
                ", maximumAmount=" + maximumAmount +
                '}';
    }
}
