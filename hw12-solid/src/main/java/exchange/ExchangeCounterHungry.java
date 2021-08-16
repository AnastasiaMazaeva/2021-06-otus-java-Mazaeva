package exchange;

import exceptions.AtmException;

import java.util.ArrayList;
import java.util.List;

public class ExchangeCounterHungry implements ExchangeCounter {

    @Override
    public List<Integer> exchange(int sum, List<Integer> changeValues) {
        List<Integer> list = new ArrayList<>();
        for (int value : changeValues) {
            if (sum <= 0) {
                break;
            }
            if (sum < value) {
                continue;
            }
            if (sum == value) {
                list.add(value);
                return list;
            }
            list.add(value);
            sum = sum - value;
        }

        if (sum != 0) {
            throw new AtmException();
        }
        return list;
    }
}
