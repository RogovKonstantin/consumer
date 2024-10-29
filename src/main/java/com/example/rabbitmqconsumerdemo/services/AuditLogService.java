package com.example.rabbitmqconsumerdemo.services;

import java.util.UUID;

public interface AuditLogService {


    void processAuditLog(UUID listingId, String action, String details, long processingTime);
}
