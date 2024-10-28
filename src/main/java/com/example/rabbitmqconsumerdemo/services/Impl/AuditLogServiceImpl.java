package com.example.rabbitmqconsumerdemo.services.Impl;

import com.example.rabbitmqconsumerdemo.models.AuditLog;

import com.example.rabbitmqconsumerdemo.repos.AuditLogRepository;
import com.example.rabbitmqconsumerdemo.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void saveAuditLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
    @Override
    public void processAuditLog(UUID listingId, String action, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setListingId(listingId);
        auditLog.setAction(action);
        auditLog.setDetails(details);

        auditLogRepository.save(auditLog);
    }
}
