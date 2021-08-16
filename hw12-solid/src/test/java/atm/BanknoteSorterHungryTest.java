package atm;

import exceptions.AtmException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BanknoteSorterHungryTest {

    @Test
    void test_okAll() {
        Atm atm = new Atm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(500);
        assertEquals(10, banknotes.size());
        assertEquals(0, atm.getBalance());
    }

    @Test
    void test_ok() {
        Atm atm = new Atm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(60);
        assertEquals(2, banknotes.size());
        assertEquals(440, atm.getBalance());
    }

    @Test
    void test_okBanknote() {
        Atm atm = new Atm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(50);
        assertEquals(1, banknotes.size());
        assertEquals(450, atm.getBalance());
    }

    @Test
    void test_Exception() {
        Atm atm = new Atm();
        atm.take(prepareBanknotes());
        assertThrows(AtmException.class, () -> atm.give(600));
    }

    private List<Banknote> prepareBanknotes() {
        Banknote banknote1 = new Banknote(10);
        Banknote banknote2 = new Banknote(10);
        Banknote banknote3 = new Banknote(10);
        Banknote banknote4 = new Banknote(10);
        Banknote banknote5 = new Banknote(10);
        Banknote banknote6 = new Banknote(50);
        Banknote banknote7 = new Banknote(100);
        Banknote banknote8 = new Banknote(100);
        Banknote banknote9 = new Banknote(100);
        Banknote banknote10 = new Banknote(100);
        return Arrays.asList(banknote1, banknote2, banknote3, banknote4,
                banknote5, banknote6, banknote7, banknote8, banknote9, banknote10);
    }

}