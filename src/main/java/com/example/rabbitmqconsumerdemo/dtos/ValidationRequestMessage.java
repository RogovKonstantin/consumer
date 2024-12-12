package com.example.rabbitmqconsumerdemo.dtos;

import java.util.UUID;

public class ValidationRequestMessage {
    private UUID listingId;
    private String title;
    private String description;

    public ValidationRequestMessage(UUID listingId, String title, String description) {
        this.listingId = listingId;
        this.title = title;
        this.description = description;
    }

    public UUID getListingId() {
        return listingId;
    }

    public void setListingId(UUID listingId) {
        this.listingId = listingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

