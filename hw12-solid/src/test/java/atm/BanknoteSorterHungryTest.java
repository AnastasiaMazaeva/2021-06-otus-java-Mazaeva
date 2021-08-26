package atm;

import constants.Denomination;
import exceptions.AtmException;
import exchange.BanknoteSorter;
import exchange.BanknoteSorterHungry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BanknoteSorterHungryTest {

    private static final EnumMap<Denomination, Integer> atmState = new EnumMap<>(Denomination.class);

    @BeforeEach
    public void setUp() {
        atmState.put(Denomination.TEN, 0);
        atmState.put(Denomination.FIFTY, 0);
        atmState.put(Denomination.HUNDRED, 0);
        atmState.put(Denomination.TWO_HUNDRED, 0);
        atmState.put(Denomination.FIVE_HUNDRED, 0);
        atmState.put(Denomination.THOUSAND, 0);
        atmState.put(Denomination.TWO_THOUSAND, 0);
        atmState.put(Denomination.FIVE_THOUSAND, 0);
    }
    @Test
    void test_okAll() {
        Atm atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(500);
        assertEquals(10, banknotes.size());
        assertEquals(0, atm.getBalance());
    }

    @Test
    void test_ok() {
        Atm atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(60);
        assertEquals(2, banknotes.size());
        assertEquals(440, atm.getBalance());
    }

    @Test
    void test_okBanknote() {
        Atm atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(50);
        assertEquals(1, banknotes.size());
        assertEquals(450, atm.getBalance());
    }

    @Test
    void test_Exception() {
        Atm atm = getAtm();
        atm.take(prepareBanknotes());
        assertThrows(AtmException.class, () -> atm.give(600));
    }


    private Atm getAtm() {
        Storage storage = new Storage(atmState);
        BanknoteSorter banknoteSorter = new BanknoteSorterHungry(storage);
        return new Atm(storage, banknoteSorter);
    }

    private List<Banknote> prepareBanknotes() {
        Banknote banknote1 = new Banknote(Denomination.TEN);
        Banknote banknote2 = new Banknote(Denomination.TEN);
        Banknote banknote3 = new Banknote(Denomination.TEN);
        Banknote banknote4 = new Banknote(Denomination.TEN);
        Banknote banknote5 = new Banknote(Denomination.TEN);
        Banknote banknote6 = new Banknote(Denomination.FIFTY);
        Banknote banknote7 = new Banknote(Denomination.HUNDRED);
        Banknote banknote8 = new Banknote(Denomination.HUNDRED);
        Banknote banknote9 = new Banknote(Denomination.HUNDRED);
        Banknote banknote10 = new Banknote(Denomination.HUNDRED);
        return Arrays.asList(banknote1, banknote2, banknote3, banknote4,
                banknote5, banknote6, banknote7, banknote8, banknote9, banknote10);
    }

}