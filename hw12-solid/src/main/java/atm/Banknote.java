package atm;

import lombok.Data;

@Data
public class Banknote {

    private int denomination;

    public Banknote(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return "Banknote{ denomination=" + denomination + '}';
    }
}
