package org.fasttrackit.budgettrackerapi.transfer;

public class ShowUserRequest {

    private String partialName;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    @Override
    public String toString() {
        return "ShowUserRequest{" +
                "partialName='" + partialName + '\'' +
                '}';
    }
}
