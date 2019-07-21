package com.adria.model;

/**
 * Represents the M part in MVP architecture
 * Simple model that returns random messages
 */
public class GLoginModel {
    private String[] messages = {"Connected", "Hi : ", "Disconnected"};

    public String generateText(int status) {
        return messages[status];
    }
}
