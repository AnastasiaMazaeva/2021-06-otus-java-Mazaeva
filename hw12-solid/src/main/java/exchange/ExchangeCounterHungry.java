package exchange;

import exceptions.AtmException;

import java.util.ArrayList;
import java.util.List;

public class ExchangeCounterHungry implements ExchangeCounter {

    @Override
    public List<Integer> exchange(int sum, List<Integer> changeValues) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < changeValues.size() && sum > 0; ++i) {
            int value = changeValues.get(i);
            if (sum >= value) {
                list.add(value);
                sum -= value;
            }
        }

        if (sum != 0) {
            throw new AtmException("Невозможно выдать требуемую сумму");
        }
        return list;
    }
}
