package exchange;

import atm.Banknote;

import java.util.List;

public interface BanknoteSorter {

    List<Banknote> sort(int sum);
}
