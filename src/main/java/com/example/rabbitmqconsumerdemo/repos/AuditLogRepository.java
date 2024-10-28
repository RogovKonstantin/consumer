package com.example.rabbitmqconsumerdemo.repos;

import com.example.rabbitmqconsumerdemo.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
