/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.quickchat3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Login class.
 * All test data matches the PoE rubric exactly.
 *
 * @author Student
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();

        // Register a user so login tests have data to work with
        login.registerUser(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Kyle",
            "Smith"
        );
    }

    // ══════════════════════════════════════════════════════════════
    // USERNAME TESTS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void testUsernameCorrectlyFormatted() {
        // PoE test data: "kyl_1" — should return true
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        // PoE test data: "kyle!!!!!!!" — should return false
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // ══════════════════════════════════════════════════════════════
    // PASSWORD TESTS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void testPasswordMeetsComplexity() {
        // PoE test data: "Ch&&sec@ke99!" — should return true
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordFailsComplexity() {
        // PoE test data: "password" — should return false
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ══════════════════════════════════════════════════════════════
    // CELL NUMBER TESTS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void testCellNumberCorrectlyFormatted() {
        // PoE test data: +27838968976 — should return true
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellNumberIncorrectlyFormatted() {
        // PoE test data: 08966553 — should return false
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ══════════════════════════════════════════════════════════════
    // LOGIN TESTS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void testLoginSuccessful() {
        // PoE requirement: correct credentials return true
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        // PoE requirement: wrong credentials return false
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    // ══════════════════════════════════════════════════════════════
    // assertEquals TESTS — PoE exact messages required
    // ══════════════════════════════════════════════════════════════

    @Test
    public void testUsernameCorrectFormatReturnsMessage() {
        // PoE: correct username moves past username check
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Kyle",
            "Smith"
        );
        assertTrue(result.contains("successfully"));
    }

    @Test
    public void testUsernameIncorrectFormatReturnsMessage() {
        // PoE: exact error message must match
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyle!!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Kyle",
            "Smith"
        );
        assertEquals(
            "Username is not correctly formatted; please ensure " +
            "that your username contains an underscore and is no " +
            "more than five characters in length.",
            result
        );
    }

    @Test
    public void testPasswordSuccessMessage() {
        // PoE: correct password moves to next check
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Kyle",
            "Smith"
        );
        assertTrue(result.contains("successfully"));
    }

    @Test
    public void testPasswordFailMessage() {
        // PoE: exact error message must match
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyl_1",
            "password",
            "+27838968976",
            "Kyle",
            "Smith"
        );
        assertEquals(
            "Password is not correctly formatted; please ensure " +
            "that the password contains at least eight characters," +
            " a capital letter, a number, and a special character.",
            result
        );
    }

    @Test
    public void testCellNumberSuccessMessage() {
        // PoE: correct cell number returns success
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976",
            "Kyle",
            "Smith"
        );
        assertTrue(result.contains("successfully"));
    }

    @Test
    public void testCellNumberFailMessage() {
        // PoE: incorrect cell number returns error
        Login freshLogin = new Login();
        String result = freshLogin.registerUser(
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553",
            "Kyle",
            "Smith"
        );
        assertEquals(
            "Cell phone number incorrectly formatted or does not " +
            "contain international code.",
            result
        );
    }

    @Test
    public void testLoginSuccessReturnsWelcomeMessage() {
        // PoE: successful login returns welcome message
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals(
            "Welcome Kyle Smith, it is great to see you again.",
            login.returnLoginStatus(result)
        );
    }

    @Test
    public void testLoginFailReturnsErrorMessage() {
        // PoE: failed login returns error message
        boolean result = login.loginUser("wrong", "wrong");
        assertEquals(
            "Username or password incorrect, please try again.",
            login.returnLoginStatus(result)
        );
    }

}
