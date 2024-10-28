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
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageProcessor(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
        this.objectMapper = new ObjectMapper();
    }

    public void processCreateMessage(String message) {
        processJsonMessage(message, "CREATE");
    }

    public void processUpdateMessage(String message) {
        processJsonMessage(message, "UPDATE");
    }

    public void processDeleteMessage(String message) {
        System.out.println("Received delete message: " + message);
        try {
            UUID listingId = extractIdFromString(message, "Deleted listing with ID: ");
            String details = "Processed from DELETE queue";
            auditLogService.processAuditLog(listingId, "DELETE", details);
            System.out.println("Processed DELETE message for listingId: " + listingId);
        } catch (Exception e) {
            System.err.println("Error processing message from DELETE queue: " + e.getMessage());
        }
    }

    private void processJsonMessage(String message, String action) {
        System.out.println("Received " + action + " message: " + message);
        try {
            UUID listingId = extractIdFromJson(message);
            String details = "Processed from " + action + " queue";
            auditLogService.processAuditLog(listingId, action, details);
            System.out.println("Processed " + action + " message for listingId: " + listingId);
        } catch (Exception e) {
            System.err.println("Error processing message from " + action + " queue: " + e.getMessage());
        }
    }

    private UUID extractIdFromJson(String message) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(message);
        if (!jsonNode.has("id")) {
            throw new IllegalArgumentException("Missing 'id' field in JSON message: " + message);
        }
        return UUID.fromString(jsonNode.get("id").asText());
    }

    private UUID extractIdFromString(String message, String prefix) throws Exception {
        String cleanedMessage = message.replaceAll("^\"|\"$", "").trim();

        if (cleanedMessage.startsWith(prefix)) {
            String uuidString = cleanedMessage.substring(prefix.length()).trim();

            if (uuidString.isEmpty()) {
                throw new IllegalArgumentException("UUID is empty in message: " + message);
            }

            return UUID.fromString(uuidString);
        } else {
            throw new IllegalArgumentException("Unhandled message format: " + message);
        }
    }

}
