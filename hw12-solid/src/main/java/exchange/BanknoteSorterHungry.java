package exchange;

import atm.Banknote;
import atm.Storage;
import constants.Denomination;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BanknoteSorterHungry implements BanknoteSorter {

    private final Storage storage;

    public BanknoteSorterHungry(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Banknote> sort(int sum) {
        ExchangeCounter exchangeCounter = new ExchangeCounterHungry();
        List<Integer> values = expandState();
        return exchangeCounter.exchange(sum, values).stream()
                .map(Denomination::getByValue)
                .map(Banknote::new)
                .collect(Collectors.toList());
    }

    private List<Integer> expandState() {
        List<Integer> values = storage.getBanknoteList();
        values.sort(Comparator.reverseOrder());
        return values;
    }

}
