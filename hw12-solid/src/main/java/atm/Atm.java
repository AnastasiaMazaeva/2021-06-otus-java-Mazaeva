package atm;

import exchange.BanknoteSorter;

import java.util.List;

public class Atm {

    private final Storage storage;
    private final BanknoteSorter sorter;

    public Atm(Storage storage, BanknoteSorter sorter) {

        this.storage = storage;
        this.sorter = sorter;
    }

    public List<Banknote> give(int sum) {
        List<Banknote> banknotes = sorter.sort(sum);
        storage.removeBanknotes(banknotes);
        return banknotes;
    }

    public void take(List<Banknote> banknotes) {
        storage.addBanknotes(banknotes);
    }

    public int getBalance() {
        return storage.getBalance();
    }
}
