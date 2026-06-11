/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat3;

import java.util.Scanner;

/**
 * QuickChat — main entry point.
 * Handles all console I/O only. Logic lives in Login and Message.
 *
 * @author Student
 */
public class QuickChat3 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Login   login   = new Login();
        Message msg     = new Message();

        // ══════════════════════════════════════════════════════════
        // REGISTRATION — Part 1 requirement
        // ══════════════════════════════════════════════════════════

        System.out.println("================================");
        System.out.println("   Welcome to QuickChat");
        System.out.println("   Please Register to Start");
        System.out.println("================================");

        // First name — optional as per your request
        System.out.print("First name (press Enter to skip): ");
        String firstName = scanner.nextLine();

        // Last name — optional as per your request
        System.out.print("Last name (press Enter to skip): ");
        String lastName = scanner.nextLine();

        // Use defaults if user skipped
        if (firstName.trim().isEmpty()) firstName = "User";
        if (lastName.trim().isEmpty())  lastName  = "";

        // Username loop — must contain _ and be 5 chars or less
        String username = "";
        while (!login.checkUserName(username)) {
            System.out.print("Username (must contain _ " +
                             "and be 5 chars or less): ");
            username = scanner.nextLine();
            if (!login.checkUserName(username)) {
                System.out.println("Invalid username. Must contain _ " +
                                   "and be 5 characters or less.");
            }
        }

        // Password loop — must meet complexity rules
        String password = "";
        while (!login.checkPasswordComplexity(password)) {
            System.out.print("Password (8+ chars, capital letter, " +
                             "number, special character): ");
            password = scanner.nextLine();
            if (!login.checkPasswordComplexity(password)) {
                System.out.println("Invalid password. Must have 8+ " +
                    "characters, a capital letter, a number " +
                    "and a special character.");
            }
        }

        // Phone number loop — must start with +27
        String phone = "";
        while (!login.checkCellPhoneNumber(phone)) {
            System.out.print("Cell number (e.g. +27831234567): ");
            phone = scanner.nextLine();
            if (!login.checkCellPhoneNumber(phone)) {
                System.out.println("Invalid number. Must start " +
                                   "with +27 followed by 9 digits.");
            }
        }

        // Register the user and show result
        System.out.println("\n" + login.registerUser(
                username, password, phone, firstName, lastName));

        // ══════════════════════════════════════════════════════════
        // LOGIN — Part 1 requirement
        // ══════════════════════════════════════════════════════════

        System.out.println("\n================================");
        System.out.println("         Please Login");
        System.out.println("================================");

        int     attempts = 0;
        boolean loggedIn = false;

        // User gets 3 attempts to log in
        while (attempts < 3 && !loggedIn) {
            System.out.print("Username: ");
            String u = scanner.nextLine();
            System.out.print("Password: ");
            String p = scanner.nextLine();

            loggedIn = login.loginUser(u, p);
            System.out.println(login.returnLoginStatus(loggedIn));

            if (!loggedIn) {
                attempts++;
                int left = 3 - attempts;
                if (left > 0) {
                    System.out.println("Attempts left: " + left);
                } else {
                    System.out.println(
                        "Locked out. Please try again later.");
                }
            }
        }

        // Stop the program if login failed after 3 attempts
        if (!loggedIn) {
            scanner.close();
            return;
        }

        // ══════════════════════════════════════════════════════════
        // MAIN MENU — Parts 2 and 3 requirement
        // ══════════════════════════════════════════════════════════

        int choice         = -1;
        int messageCounter = 0;

        while (choice != 0) {

            System.out.println("\n================================");
            System.out.println("       QuickChat Menu");
            System.out.println("================================");
            System.out.println("1. Add New Message");
            System.out.println("2. Display All Sent Messages");
            System.out.println("3. Display Longest Sent Message");
            System.out.println("4. Search Message by ID");
            System.out.println("5. Search Messages by Recipient");
            System.out.println("6. Delete Message by Hash");
            System.out.println("7. Display Sent Messages Report");
            System.out.println("0. Exit");
            System.out.println("================================");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number 0-7.");
                continue;
            }

            switch (choice) {

                // ── 1. ADD NEW MESSAGE ─────────────────────────────
                // Part 2 requirement: send messages after login
                case 1:
                    messageCounter++;
                    System.out.println("\n--- New Message " +
                                       messageCounter + " ---");

                    // Auto-generate the 10-digit message ID
                    String id = msg.generateMessageID();
                    System.out.println("Message ID: " + id);

                    // Recipient number validation loop
                    String cell = "";
                    while (!msg.checkRecipientCell(cell)
                                .contains("successfully")) {
                        System.out.print("Recipient number (+27...): ");
                        cell = scanner.nextLine();
                        System.out.println(msg.checkRecipientCell(cell));
                    }

                    // Message text validation loop — max 250 chars
                    String text = "";
                    while (!msg.validateMessageLength(text)
                                .contains("ready")) {
                        System.out.print("Type your message " +
                                         "(max 250 chars): ");
                        text = scanner.nextLine();
                        System.out.println(
                            msg.validateMessageLength(text));
                    }

                    // Generate the message hash
                    String hash = msg.createMessageHash(
                                      id, messageCounter, text);

                    // Display full message details
                    System.out.println("\n--- Message Details ---");
                    System.out.println("Message ID:   " + id);
                    System.out.println("Message Hash: " + hash);
                    System.out.println("Recipient:    " + cell);
                    System.out.println("Message:      " + text);

                    // User chooses what to do with the message
                    System.out.println("\n1) Send  " +
                                       "2) Disregard  " +
                                       "3) Store");
                    System.out.print("Choose: ");

                    int action = 0;
                    try {
                        action = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid choice. " +
                                           "Message discarded.");
                        break;
                    }

                    // sentMessage now handles array storage too
                    System.out.println(
                        msg.sentMessage(action, id, cell, text, hash));

                    System.out.println("Total messages sent: " +
                                       msg.returnTotalMessages());
                    break;

                // ── 2. DISPLAY ALL SENT MESSAGES ───────────────────
                // Part 3 requirement: display stored messages
                case 2:
                    System.out.println(msg.displayStoredMessages());
                    break;

                // ── 3. DISPLAY LONGEST SENT MESSAGE ───────────────
                // Part 3 requirement: display the longest message
                case 3:
                    System.out.println(msg.getLongestMessage());
                    break;

                // ── 4. SEARCH BY MESSAGE ID ────────────────────────
                // Part 3 requirement: search for a message by ID
                case 4:
                    System.out.print("Enter Message ID to search: ");
                    String searchID = scanner.nextLine();
                    System.out.println(
                        msg.searchByMessageID(searchID));
                    break;

                // ── 5. SEARCH BY RECIPIENT ─────────────────────────
                // Part 3 requirement: search messages by recipient
                case 5:
                    System.out.print("Enter recipient number: ");
                    String searchCell = scanner.nextLine();
                    System.out.println(
                        msg.searchByRecipient(searchCell));
                    break;

                // ── 6. DELETE BY HASH ──────────────────────────────
                // Part 3 requirement: delete a message using its hash
                case 6:
                    System.out.print(
                        "Enter message hash to delete: ");
                    String deleteHash = scanner.nextLine();
                    System.out.println(msg.deleteByHash(deleteHash));
                    break;

                // ── 7. DISPLAY REPORT ──────────────────────────────
                // Part 3 requirement: full report of sent messages
                case 7:
                    System.out.println(msg.displayReport());
                    break;

                // ── 0. EXIT ────────────────────────────────────────
                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                        "Invalid option. Please choose 0 to 7.");
            }
        }

        scanner.close();
    }
}
 