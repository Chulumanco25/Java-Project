/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat3;

import java.util.Random;

/**
 * Message class handles all messaging logic for QuickChat.
 * No Scanner here — keeps this class fully unit-testable.
 *
 * @author Student
 */
public class Message {

    // ══════════════════════════════════════════════════════════════
    // ARRAYS — Part 3 requirement: store messages in arrays
    // ══════════════════════════════════════════════════════════════

    private String[] sentMessages        = new String[50];
    private String[] disregardedMessages = new String[50];
    private String[] storedMessages      = new String[50];
    private String[] messageHashes       = new String[50];

    // Separate ID arrays for sent and stored — prevents overwriting
    private String[] messageIDs          = new String[50];
    private String[] storedMessageIDs    = new String[50];

    // Counters to track how many items are in each array
    private int sentCount         = 0;
    private int disregardedCount  = 0;
    private int storedCount       = 0;
    private int totalMessagesSent = 0;

    // ══════════════════════════════════════════════════════════════
    // PART 2 METHODS
    // ══════════════════════════════════════════════════════════════

    /**
     * Generates a random 10-digit message ID.
     * PoE requirement: unique ID for tracking each message.
     *
     * @return 10-digit ID as a String
     */
    public String generateMessageID() {
        Random rand = new Random();
        long number = (long)(rand.nextDouble() * 9_000_000_000L)
                      + 1_000_000_000L;
        return String.format("%010d", number);
    }

    /**
     * Checks that the message ID is no more than 10 characters.
     * PoE requirement: message ID must not exceed 10 characters.
     *
     * @param id the ID to check
     * @return true if valid
     */
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    /**
     * Validates recipient cell number.
     * PoE requirement: number must start with international code.
     * Must start with + and have 10-11 digits after it.
     *
     * @param cell the number to validate
     * @return success or failure message
     */
    public String checkRecipientCell(String cell) {
        if (cell != null && cell.matches("\\+\\d{10,11}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not " +
               "contain an international code. Please correct the " +
               "number and try again.";
    }

    /**
     * Checks message does not exceed 250 characters.
     * PoE requirement: message must not exceed 250 characters.
     *
     * @param message the message text
     * @return success or failure with overage count
     */
    public String validateMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over +
               "; please reduce the size.";
    }

    /**
     * Creates the message hash.
     * PoE requirement format: first 2 digits of ID : message
     * number : first and last word in caps.
     * Example: 00:1:HITONIGHT
     *
     * @param id            the message ID
     * @param messageNumber the message counter
     * @param text          the message body
     * @return formatted hash string
     */
    public String createMessageHash(String id, int messageNumber,
                                    String text) {
        // Step 1: Get first 2 digits from the ID
        String idDigits = "";
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c)) idDigits += c;
            if (idDigits.length() == 2) break;
        }
        if (idDigits.isEmpty()) idDigits = "00";

        // Step 2: Split message into words
        String[] words = text.trim().split("\\s+");

        // Step 3: First and last word — uppercase, letters only
        String firstWord = words[0].toUpperCase()
                                   .replaceAll("[^A-Z]", "");
        String lastWord  = words[words.length - 1].toUpperCase()
                                                  .replaceAll("[^A-Z]", "");

        // Step 4: Avoid doubling if message is only one word
        String textPart = firstWord.equals(lastWord)
                          ? firstWord
                          : firstWord + lastWord;

        // Step 5: Return the completed hash
        return idDigits + ":" + messageNumber + ":" + textPart;
    }

    /**
     * Handles send, disregard or store action.
     * PoE requirement: saves message into correct array.
     * Uses separate ID arrays to prevent overwriting.
     *
     * @param action 1=Send, 2=Disregard, 3=Store
     * @param id     message ID
     * @param cell   recipient number
     * @param text   message body
     * @param hash   message hash
     * @return status message
     */
    public String sentMessage(int action, String id,
                              String cell, String text,
                              String hash) {
        switch (action) {
            case 1:
                // Save to sent arrays using sentCount
                sentMessages[sentCount]  = cell + "|" + text;
                messageHashes[sentCount] = hash;
                messageIDs[sentCount]    = id;
                sentCount++;
                totalMessagesSent++;
                return "Message successfully sent.";

            case 2:
                // Save to disregarded array
                disregardedMessages[disregardedCount] = cell + "|" + text;
                disregardedCount++;
                return "Press 0 to delete the message.";

            case 3:
                // Save to stored arrays using storedCount
                // Uses storedMessageIDs to avoid overwriting sentIDs
                storedMessages[storedCount]   = cell + "|" + text;
                messageHashes[storedCount]    = hash;
                storedMessageIDs[storedCount] = id;
                storedCount++;
                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    /**
     * Placeholder for recently sent messages feature.
     * PoE requirement: display "Coming Soon" message.
     *
     * @return coming soon message
     */
    public String printMessages() {
        return "Coming Soon.";
    }

    /**
     * Returns total number of messages sent this session.
     * PoE requirement: accumulate and display total messages sent.
     *
     * @return count of sent messages
     */
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Formats a message as a JSON string for storage.
     * PoE requirement: store messages in JSON format.
     * JSON format referenced from: https://www.json.org/json-en.html
     *
     * @param id   message ID
     * @param cell recipient number
     * @param text message body
     * @param hash message hash
     * @return JSON formatted string
     */
    public String storeMessage(String id, String cell,
                               String text, String hash) {
        return "{"
            + "\"id\": \""        + id   + "\", "
            + "\"recipient\": \"" + cell + "\", "
            + "\"message\": \""   + text + "\", "
            + "\"hash\": \""      + hash + "\""
            + "}";
    }

    // ══════════════════════════════════════════════════════════════
    // PART 3 METHODS
    // ══════════════════════════════════════════════════════════════

    /**
     * Displays all stored messages with recipient and text.
     * PoE requirement: display sender and recipient of stored messages.
     *
     * @return formatted string of all stored messages
     */
    public String displayStoredMessages() {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        String result = "--- Stored Messages ---\n";
        for (int i = 0; i < storedCount; i++) {
            String[] parts = storedMessages[i].split("\\|");
            result += "Recipient: " + parts[0] +
                      " | Message: " + parts[1] + "\n";
        }
        return result;
    }

    /**
     * Finds the longest message across sent and stored arrays.
     * PoE requirement: display the longest stored message.
     *
     * @return the longest message text
     */
    public String getLongestMessage() {
        String longest = "";

        // Check sent messages array
        for (int i = 0; i < sentCount; i++) {
            String msg = sentMessages[i].split("\\|")[1];
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        // Check stored messages array
        for (int i = 0; i < storedCount; i++) {
            String msg = storedMessages[i].split("\\|")[1];
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        if (longest.isEmpty()) return "No messages found.";
        return "Longest message: " + longest;
    }

    /**
     * Searches for a message by ID in both sent and stored arrays.
     * PoE requirement: search for a message ID and display
     * the corresponding recipient and message.
     * Uses separate ID arrays to search correctly.
     *
     * @param searchID the ID to search for
     * @return recipient and message if found
     */
    public String searchByMessageID(String searchID) {

        // Search sent messages array using messageIDs
        for (int i = 0; i < sentCount; i++) {
            if (messageIDs[i] != null &&
                messageIDs[i].equals(searchID)) {
                String[] parts = sentMessages[i].split("\\|");
                return "Recipient: " + parts[0] +
                       " | Message: " + parts[1];
            }
        }

        // Search stored messages array using storedMessageIDs
        for (int i = 0; i < storedCount; i++) {
            if (storedMessageIDs[i] != null &&
                storedMessageIDs[i].equals(searchID)) {
                String[] parts = storedMessages[i].split("\\|");
                return "Recipient: " + parts[0] +
                       " | Message: " + parts[1];
            }
        }

        return "Message ID not found.";
    }

    /**
     * Finds all messages sent or stored for a specific recipient.
     * PoE requirement: search for all messages stored for
     * a particular recipient.
     *
     * @param recipient the cell number to search for
     * @return all messages for that recipient
     */
    public String searchByRecipient(String recipient) {
        String result = "";

        // Search sent messages array
        for (int i = 0; i < sentCount; i++) {
            String[] parts = sentMessages[i].split("\\|");
            if (parts[0].equals(recipient)) {
                result += parts[1] + "\n";
            }
        }

        // Search stored messages array
        for (int i = 0; i < storedCount; i++) {
            String[] parts = storedMessages[i].split("\\|");
            if (parts[0].equals(recipient)) {
                result += parts[1] + "\n";
            }
        }

        if (result.isEmpty()) {
            return "No messages found for " + recipient;
        }
        return result.trim();
    }

    /**
     * Deletes a stored message using its hash.
     * PoE requirement: delete a message using the message hash.
     * Shifts remaining elements left after deletion.
     *
     * @param hash the hash of the message to delete
     * @return confirmation or not found message
     */
    public String deleteByHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i] != null &&
                messageHashes[i].equals(hash)) {

                // Save deleted message text for confirmation
                String deleted = storedMessages[i].split("\\|")[1];

                // Shift all elements left to fill the gap
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j]    = storedMessages[j + 1];
                    messageHashes[j]     = messageHashes[j + 1];
                    storedMessageIDs[j]  = storedMessageIDs[j + 1];
                }

                // Clear the last slot and reduce counter
                storedMessages[storedCount - 1]   = null;
                messageHashes[storedCount - 1]    = null;
                storedMessageIDs[storedCount - 1] = null;
                storedCount--;

                return "Message: \"" + deleted +
                       "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    /**
     * Displays a full report of all sent messages.
     * PoE requirement: report showing message hash,
     * recipient and message.
     *
     * @return formatted report string
     */
    public String displayReport() {
        if (sentCount == 0) {
            return "No sent messages to report.";
        }
        String report = "=== Full Message Report ===\n";
        for (int i = 0; i < sentCount; i++) {
            String[] parts = sentMessages[i].split("\\|");
            report += "Hash:      " + messageHashes[i] + "\n";
            report += "Recipient: " + parts[0]          + "\n";
            report += "Message:   " + parts[1]          + "\n";
            report += "---------------------------\n";
        }
        return report;
    }

}