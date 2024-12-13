package com.example.rabbitmqconsumerdemo.dtos;

import java.util.UUID;

public class ValidationResponseMessage {
    private UUID listingId;
    private boolean isValid;
    private String validationMessage;

    public ValidationResponseMessage(UUID listingId, boolean isValid, String validationMessage) {
        this.listingId = listingId;
        this.isValid = isValid;
        this.validationMessage = validationMessage;
    }

    public UUID getListingId() {
        return listingId;
    }

    public void setListingId(UUID listingId) {
        this.listingId = listingId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    @Override
    public String toString() {
        return "ValidationResponseMessage{" +
                "listingId=" + listingId +
                ", isValid=" + isValid +
                ", validationMessage='" + validationMessage + '\'' +
                '}';
    }
}
