package com.example.rabbitmqconsumerdemo.services;

import com.example.rabbitmqconsumerdemo.models.AuditLog;

import java.util.List;
import java.util.UUID;

public interface AuditLogService {
    void saveAuditLog(AuditLog auditLog);

    void processAuditLog(UUID listingId, String action, String details);
}
