package exchange;

import atm.Banknote;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BanknoteSorterHungry implements BanknoteSorter {

    private final List<Integer> values = new ArrayList<>();

    public BanknoteSorterHungry(Map<Integer, Integer> atmState) {
        atmState.forEach((key, value) -> {
            for (int i = 0; i < value; ++i) {
                values.add(key);
            }
        });
        values.sort(Comparator.reverseOrder());
    }

    @Override
    public List<Banknote> give(int sum) {
        ExchangeCounter exchangeCounter = new ExchangeCounterHungry();
        return exchangeCounter.exchange(sum, values).stream()
                .map(Banknote::new)
                .collect(Collectors.toList());
    }

}
