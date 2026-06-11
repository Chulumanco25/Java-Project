/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat3;

/**
 * Login class handles all user registration and authentication.
 * No Scanner here — keeps this class fully unit-testable.
 *
 * @author Student
 */
public class Login {

    // ── STORED USER DETAILS ────────────────────────────────────────
    private String savedUsername;
    private String savedPassword;
    private String savedPhoneNumber;
    private String firstName;
    private String lastName;

    
    /**
     * Checks username contains underscore and is 5 characters or less.
     * PoE requirement: username must contain _ and be no more than 5 chars.
     *
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        boolean hasUnderscore = username.contains("_");
        boolean isShortEnough = username.length() <= 5;
        return hasUnderscore && isShortEnough;
    }

    /**
     * Checks password meets all complexity rules.
     * PoE requirement: 8+ characters, uppercase, digit, special character.
     *
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper   = false;
        boolean hasDigit   = false;
        boolean hasSpecial = false;

        // Loop through each character to check requirements
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))      hasUpper   = true;
            if (Character.isDigit(c))          hasDigit   = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Validates a South African cell number.
     * PoE requirement: must contain international code.
     * Regex pattern referenced from:
     * https://www.regular-expressions.info/phone.html
     *
     * @param phoneNumber the number to validate
     * @return true if valid, false otherwise
     */
    public boolean checkCellPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        // +27 followed by exactly 9 digits = 12 characters total
        return phoneNumber.matches("\\+27\\d{9}");
    }

    /**
     * Registers a user if all validation checks pass.
     * PoE requirement: returns appropriate message for each condition.
     *
     * @param username      the chosen username
     * @param password      the chosen password
     * @param cellNumber    the cell phone number
     * @param userFirstName the user's first name
     * @param userLastName  the user's last name
     * @return registration result message
     */
    public String registerUser(String username, String password,
                               String cellNumber, String userFirstName,
                               String userLastName) {

        // Check username first — PoE exact message required
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure " +
                   "that your username contains an underscore and is no " +
                   "more than five characters in length.";
        }

        // Check password — PoE exact message required
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure " +
                   "that the password contains at least eight characters," +
                   " a capital letter, a number, and a special character.";
        }

        // Check cell number — PoE exact message required
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not " +
                   "contain international code.";
        }

        // All checks passed — save the user details
        this.savedUsername    = username;
        this.savedPassword    = password;
        this.savedPhoneNumber = cellNumber;
        this.firstName        = userFirstName;
        this.lastName         = userLastName;

        return "Username successfully captured. Password successfully " +
               "captured. Cell phone number successfully added.";
    }

    /**
     * Checks entered credentials match stored registration details.
     * PoE requirement: returns true if username and password match.
     *
     * @param username entered username
     * @param password entered password
     * @return true if both match
     */
    public boolean loginUser(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return username.equals(savedUsername) &&
               password.equals(savedPassword);
    }

    /**
     * Returns login status message based on result.
     * PoE requirement: returns welcome or error message.
     *
     * @param loginSuccessful result from loginUser()
     * @return welcome message or error message
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName +
                   ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

}
