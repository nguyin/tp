package seedu.duke.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RecordTest {
    static Record record;

    @BeforeAll
    public static void setUp() {
        record = new Record("97845679", "123 yishun street");
    }

    @Test
    void testRecordDetails() {
        assertEquals(record.getElderlyPhoneNumber(), "97845679");
        assertEquals(record.getElderlyAddress(), "123 yishun street");
    }
}
