package com.example.rabbitmqconsumerdemo.models;


import com.example.rabbitmqconsumerdemo.models.basemodels.IdDateTimeModel;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends IdDateTimeModel {

    private String action;
    private String details;
    private UUID listingId;
    private long processingTime;

    @Column(nullable = false)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(nullable = false)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Column(name = "listing_id", nullable = false)
    public UUID getListingId() {
        return listingId;
    }

    public void setListingId(UUID listingId) {
        this.listingId = listingId;
    }

    @Column(name = "processing_time", nullable = false) // Column for processing time
    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }
}
