package seedu.duke.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LowRiskElderlyTest {
    static LowRiskElderly johnTan;

    @BeforeAll
    public static void setUp() {
        johnTan = new LowRiskElderly("johntan123", "John Tan");
    }

    @Test
    void testElderlyDetails() {
        assertEquals(johnTan.getUsername(), "johntan123");
    }

    @Test
    void testElderlyToString() {
        assertEquals(johnTan.toString(), "[*] Risk Level : LOW\n"
                + "[*] Elderly Username: johntan123\n"
                + "[*] Elderly Name: John Tan\n"
                + "[*] Vaccination status of johntan123: Not vaccinated\n"
                + "[*] Blood pressure of John Tan has not yet been set!\n"
                + "[*] Birthday of johntan123: Not Recorded\n"
                + "[*] Medical History: \n"
                + "[*] Here are johntan123's records:\n"
                + "\n"
                + "\n"
                + "[*] Here is the list of upcoming appointments:\n"
                + "\n"
                + "\n"
                + "[*] This is the list of medicine(s):\n"
                + "\n"
                + "\n"
                + "[*] These are NOK(s) attached to johntan123:\n"
                + "\n");
        johnTan.setElderlyVaccinated();
        assertEquals(johnTan.toString(), "[*] Risk Level : LOW\n"
                + "[*] Elderly Username: johntan123\n"
                + "[*] Elderly Name: John Tan\n"
                + "[*] Vaccination status of johntan123: Vaccinated\n"
                + "[*] Blood pressure of John Tan has not yet been set!\n"
                + "[*] Birthday of johntan123: Not Recorded\n"
                + "[*] Medical History: \n"
                + "[*] Here are johntan123's records:\n"
                + "\n"
                + "\n"
                + "[*] Here is the list of upcoming appointments:\n"
                + "\n"
                + "\n"
                + "[*] This is the list of medicine(s):\n"
                + "\n"
                + "\n"
                + "[*] These are NOK(s) attached to johntan123:\n"
                + "\n");
    }

}
