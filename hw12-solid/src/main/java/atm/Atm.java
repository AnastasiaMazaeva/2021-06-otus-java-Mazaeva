package atm;

import constants.DenominationConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm {

    private final Map<Integer, Integer> atmState = new HashMap<>();

    public Atm() {
        atmState.put(DenominationConstants.TEN, 0);
        atmState.put(DenominationConstants.FIFTY, 0);
        atmState.put(DenominationConstants.HUNDRED, 0);
        atmState.put(DenominationConstants.TWO_HUNDRED, 0);
        atmState.put(DenominationConstants.FIVE_HUNDRED, 0);
        atmState.put(DenominationConstants.THOUSAND, 0);
        atmState.put(DenominationConstants.TWO_THOUSAND, 0);
        atmState.put(DenominationConstants.FIVE_THOUSAND, 0);
    }

    public List<Banknote> give(int sum) {
        BanknoteSorter sorter = new BanknoteSorter(atmState);
        return sorter.give(sum);
    }

    public void take(List<Banknote> banknotes) {
        banknotes.forEach(b -> {
            Integer value = atmState.get(b.getDenomination());
            atmState.put(b.getDenomination(), ++value);
        });
    }
}
