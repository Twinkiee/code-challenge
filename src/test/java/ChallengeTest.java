import org.junit.Test;

import static org.junit.Assert.*;

public class ChallengeTest {
    @Test
    public void shouldHandleSingleDigits() {
        assertEquals("1st", Challenge.numberToOrdinal(1));
        assertEquals("2nd", Challenge.numberToOrdinal(2));
        assertEquals("3rd", Challenge.numberToOrdinal(3));
    }

    @Test
    public void shouldHandleTeensDigits() {
        assertEquals("11th", Challenge.numberToOrdinal(11));
        assertEquals("12th", Challenge.numberToOrdinal(12));
        assertEquals("113th", Challenge.numberToOrdinal(113));
    }

    @Test
    public void shouldHandleRegularDigits() {
        assertEquals("21st", Challenge.numberToOrdinal(21));
        assertEquals("32nd", Challenge.numberToOrdinal(32));
        assertEquals("1133rd", Challenge.numberToOrdinal(1133));
    }

    @Test
    public void shouldHandleZero() {
        assertEquals("0", Challenge.numberToOrdinal(0));
    }
}