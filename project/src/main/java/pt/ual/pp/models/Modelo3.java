package pt.ual.pp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Modelo3 extends Modelo {
    public Modelo3(int first, int last, Random random) {
        super(first, last, random);
        timeZone.put(2, 1.20);
        timeZone.put(5, 0.25);
        timeZone.put(1, 0.70);
        timeZone.put(4, 0.90);
        timeZone.put(3, 1.00);
        ordemZone = Arrays.asList(2, 5, 1, 4, 3);
        timeWaitBeforeEnterZone.put(2, new ArrayList<>());
        timeWaitBeforeEnterZone.put(5, new ArrayList<>());
        timeWaitBeforeEnterZone.put(1, new ArrayList<>());
        timeWaitBeforeEnterZone.put(4, new ArrayList<>());
        timeWaitBeforeEnterZone.put(3, new ArrayList<>());
    }
}
