package com.example.rabbitmqconsumerdemo.utils;

import com.example.rabbitmqconsumerdemo.services.AuditLogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class MessageProcessor {

    private final AuditLogService auditLogService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MessageProcessor(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    public void processMessage(String message, ActionType actionType) {
        long startTime = System.nanoTime();

        System.out.println("Received " + actionType + " message: " + message);
        try {
            UUID listingId = extractListingId(message, actionType);
            String details = "Processed from " + actionType + " queue";

            long endTime = System.nanoTime();
            long processingTime = endTime - startTime;

            auditLogService.processAuditLog(listingId, actionType.name(), details, processingTime);
            System.out.println("Processed " + actionType + " message for listingId: " + listingId);

        } catch (Exception e) {
            System.err.println("Error processing message from " + actionType + " queue: " + e.getMessage());
            long processingTime = System.nanoTime() - startTime;
            logFailedMessage(message, actionType, e.getMessage(), processingTime);
        }
    }


    private UUID extractListingId(String message, ActionType actionType) throws Exception {
        if (actionType == ActionType.DELETE) {
            return extractIdFromString(message);
        } else {
            return extractIdFromJson(message);
        }
    }

    private UUID extractIdFromJson(String message) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(message);
        if (!jsonNode.has("id")) {
            throw new IllegalArgumentException("Missing 'id' field in JSON message: " + message);
        }
        return UUID.fromString(jsonNode.get("id").asText());
    }

    private UUID extractIdFromString(String message) {
        String cleanedMessage = message.replaceAll("^\"|\"$", "").trim();
        if (cleanedMessage.startsWith("Deleted listing with ID: ")) {
            String uuidString = cleanedMessage.substring("Deleted listing with ID: ".length()).trim();
            if (uuidString.isEmpty()) {
                throw new IllegalArgumentException("UUID is empty in message: " + message);
            }
            return UUID.fromString(uuidString);
        } else {
            throw new IllegalArgumentException("Unhandled message format: " + message);
        }
    }

    private void logFailedMessage(String message, ActionType actionType, String errorDetails, long processingTime) {
        auditLogService.processAuditLog(null, "FAILED_" + actionType, "Failed message: " + message + ", Error: " + errorDetails, processingTime);
    }
}
