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
    public void processAuditLog(UUID listingId, String action, String details, long processingTime) {
        AuditLog log = new AuditLog();
        log.setListingId(listingId);
        log.setAction(action);
        log.setDetails(details);
        log.setProcessingTime(processingTime);
        auditLogRepository.saveAndFlush(log);
    }

}
