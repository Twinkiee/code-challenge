import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
    @Test
    public void shouldMaskDigitsForBasicCreditCards() {
        assertEquals("5###########0694", CreditCard.maskify("5512103073210694"));
    }

    @Test
    public void shouldNotMaskDigitsForShortCreditCards1() {
        assertEquals("54321", CreditCard.maskify("54321"));
    }

    @Test
    public void shouldMaskDigitsForSixCharOrMoreCreditCards() {
        assertEquals("6#4321", CreditCard.maskify("654321"));
    }

    @Test
    public void shouldNotMaskDigitsForShortCreditCards2() {
        assertEquals("Skippy", CreditCard.maskify("Skippy"));
    }

    @Test
    public void shouldMaskNumbersOnly1() {
        assertEquals("4###-####-####-5616", CreditCard.maskify("4556-3646-0793-5616"));
    }

    @Test
    public void shouldMaskNumbersOnly2() {
        assertEquals("A#######BCDEFG89HI", CreditCard.maskify("A1234567BCDEFG89HI"));
    }

    @Test
    public void shouldHandleEmptyString() {
        assertEquals("", CreditCard.maskify(""));
    }
}