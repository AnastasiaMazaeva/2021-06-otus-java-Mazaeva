package atm;

import constants.DenominationConstants;
import exchange.BanknoteSorter;
import exchange.BanknoteSorterHungry;

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
        BanknoteSorter sorter = new BanknoteSorterHungry(atmState);
        List<Banknote> banknotes = sorter.give(sum);
        banknotes.forEach(b -> atmState.put(b.getDenomination(), atmState.get(b.getDenomination()) - 1));
        return banknotes;
    }

    public void take(List<Banknote> banknotes) {
        banknotes.forEach(b -> {
            Integer value = atmState.get(b.getDenomination());
            atmState.put(b.getDenomination(), ++value);
        });
    }

    public int getBalance() {
        return atmState.entrySet()
                .stream()
                .map(e -> e.getKey() * e.getValue())
                .mapToInt(Integer::intValue).sum();
    }
}
