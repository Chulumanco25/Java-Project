/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.quickchat3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Message class.
 * All test data matches the PoE rubric exactly.
 * Covers Part 2 and Part 3 requirements.
 *
 * @author Student
 */
public class MessageTest {

    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();

        // ══════════════════════════════════════════════════════════
        // POPULATE ARRAYS WITH POE TEST DATA — Part 3 requirement
        // All 5 messages loaded before each test runs
        // ══════════════════════════════════════════════════════════

        // Message 1 — Flag: Sent
        // Recipient: +27834557896
        // Message:   "Did you get the cake?"
        message.sentMessage(
            1,
            "1000000001",
            "+27834557896",
            "Did you get the cake?",
            message.createMessageHash(
                "1000000001", 1,
                "Did you get the cake?")
        );

        // Message 2 — Flag: Stored
        // Recipient: +27838884567
        // Message:   "Where are you? You are late! I have asked
        //             you to be on time."
        message.sentMessage(
            3,
            "1000000002",
            "+27838884567",
            "Where are you? You are late! I have asked " +
            "you to be on time.",
            message.createMessageHash(
                "1000000002", 2,
                "Where are you? You are late! I have asked " +
                "you to be on time.")
        );

        // Message 3 — Flag: Disregard
        // Recipient: +27834484567
        // Message:   "Yohoooo, I am at your gate."
        message.sentMessage(
            2,
            "1000000003",
            "+27834484567",
            "Yohoooo, I am at your gate.",
            message.createMessageHash(
                "1000000003", 3,
                "Yohoooo, I am at your gate.")
        );

        // Message 4 — Flag: Sent
        // Developer/Recipient: 0838884567 (no + as per PoE)
        // Message ID is 0838884567 to match PoE search test
        // Message: "It is dinner time !"
        message.sentMessage(
            1,
            "0838884567",
            "0838884567",
            "It is dinner time !",
            message.createMessageHash(
                "0838884567", 4,
                "It is dinner time !")
        );

        // Message 5 — Flag: Stored
        // Recipient: +27838884567
        // Message:   "Ok, I am leaving without you."
        message.sentMessage(
            3,
            "1000000005",
            "+27838884567",
            "Ok, I am leaving without you.",
            message.createMessageHash(
                "1000000005", 5,
                "Ok, I am leaving without you.")
        );
    }

    // ══════════════════════════════════════════════════════════════
    // PART 2 TESTS
    // ══════════════════════════════════════════════════════════════

    // ── MESSAGE LENGTH ────────────────────────────────────────────

    @Test
    public void testMessageLengthValid() {
        // PoE: message under 250 chars returns success
        assertEquals(
            "Message ready to send.",
            message.validateMessageLength(
                "Hi Mike, can you join us for dinner tonight?")
        );
    }

    @Test
    public void testMessageLengthExceeds250() {
        // PoE: message over 250 chars returns failure with count
        String longMsg = "A".repeat(260);
        assertEquals(
            "Message exceeds 250 characters by 10; " +
            "please reduce the size.",
            message.validateMessageLength(longMsg)
        );
    }

    // ── RECIPIENT CELL ────────────────────────────────────────────

    @Test
    public void testRecipientCellValid() {
        // PoE test data: +27718693002 — should return success
        assertEquals(
            "Cell phone number successfully captured.",
            message.checkRecipientCell("+27718693002")
        );
    }

    @Test
    public void testRecipientCellInvalid() {
        // PoE test data: 08575975889 — should return failure
        assertEquals(
            "Cell phone number is incorrectly formatted or does not " +
            "contain an international code. Please correct the " +
            "number and try again.",
            message.checkRecipientCell("08575975889")
        );
    }

    // ── MESSAGE HASH ──────────────────────────────────────────────

    @Test
    public void testMessageHashCorrect() {
        // PoE rubric: expected result is 00:1:HITONIGHT
        String hash = message.createMessageHash(
            "0012345678",
            1,
            "Hi Mike, can you join us for dinner tonight?"
        );
        assertEquals("00:1:HITONIGHT", hash);
    }

    // ── SEND / DISCARD / STORE ────────────────────────────────────

    @Test
    public void testSentMessageSend() {
        // PoE: action 1 returns sent confirmation
        assertEquals(
            "Message successfully sent.",
            message.sentMessage(
                1, "9999999999",
                "+27711111111",
                "Test message",
                "99:6:TESTMESSAGE")
        );
    }

    @Test
    public void testSentMessageDisregard() {
        // PoE: action 2 returns disregard confirmation
        assertEquals(
            "Press 0 to delete the message.",
            message.sentMessage(
                2, "9999999999",
                "+27711111111",
                "Test message",
                "99:6:TESTMESSAGE")
        );
    }

    @Test
    public void testSentMessageStore() {
        // PoE: action 3 returns store confirmation
        assertEquals(
            "Message successfully stored.",
            message.sentMessage(
                3, "9999999999",
                "+27711111111",
                "Test message",
                "99:6:TESTMESSAGE")
        );
    }

    // ── MESSAGE ID ────────────────────────────────────────────────

    @Test
    public void testMessageIDLength() {
        // PoE: generated ID must be 10 characters or less
        String id = message.generateMessageID();
        System.out.println("Message ID generated: " + id);
        assertTrue(message.checkMessageID(id));
    }

    // ══════════════════════════════════════════════════════════════
    // PART 3 TESTS
    // ══════════════════════════════════════════════════════════════

    // ── SENT MESSAGES ARRAY CORRECTLY POPULATED ───────────────────

    @Test
    public void testSentMessagesArrayPopulated() {
        // PoE expects: "Did you get the cake?" and
        // "It is dinner time !" in sent array
        String report = message.displayReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }

    // ── DISPLAY LONGEST MESSAGE ───────────────────────────────────

    @Test
    public void testGetLongestMessage() {
        // PoE expects message 2 — the longest across all arrays
        String result = message.getLongestMessage();
        assertTrue(result.contains(
            "Where are you? You are late! I have asked " +
            "you to be on time."));
    }

    // ── SEARCH BY MESSAGE ID ──────────────────────────────────────

    @Test
    public void testSearchByMessageID() {
        // PoE test data: search "0838884567"
        // returns "It is dinner time !"
        String result = message.searchByMessageID("0838884567");
        assertTrue(result.contains("It is dinner time !"));
    }

    // ── SEARCH BY RECIPIENT ───────────────────────────────────────

    @Test
    public void testSearchByRecipient() {
        // PoE test data: search +27838884567
        // returns messages 2 and 5
        String result = message.searchByRecipient("+27838884567");
        assertTrue(result.contains(
            "Where are you? You are late! I have asked " +
            "you to be on time."));
        assertTrue(result.contains(
            "Ok, I am leaving without you."));
    }

    // ── DELETE BY HASH ────────────────────────────────────────────

    @Test
    public void testDeleteByHash() {
        // PoE test data: delete message 2 using its hash
        // Expected: confirmation message contains deleted text
        String hashToDelete = message.createMessageHash(
            "1000000002",
            2,
            "Where are you? You are late! I have asked " +
            "you to be on time."
        );

        String result = message.deleteByHash(hashToDelete);

        assertTrue(result.contains(
            "Where are you? You are late! I have asked " +
            "you to be on time."));
        assertTrue(result.contains("successfully deleted"));
    }

    // ── DISPLAY REPORT ────────────────────────────────────────────

    @Test
    public void testDisplayReport() {
        // PoE: report must show hash, recipient and message
        // for all sent messages
        String report = message.displayReport();

        // Check required fields are present
        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));

        // Check sent messages appear in report
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }

    // ── DISPLAY STORED MESSAGES ───────────────────────────────────

    @Test
    public void testDisplayStoredMessages() {
        // PoE: stored messages must show messages 2 and 5
        String result = message.displayStoredMessages();

        assertTrue(result.contains(
            "Where are you? You are late! I have asked " +
            "you to be on time."));
        assertTrue(result.contains(
            "Ok, I am leaving without you."));
    }

}