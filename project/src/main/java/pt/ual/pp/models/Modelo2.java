package pt.ual.pp.models;

import java.util.*;

public class Modelo2 extends Modelo {
    public Modelo2(int first, int last, Random random) {
        super(first, last, random);
        timeZone.put(3, 0.50);
        timeZone.put(1, 0.60);
        timeZone.put(2, 0.85);
        timeZone.put(5, 0.50);
        ordemZone = Arrays.asList(3, 1, 2, 5);
        timeWaitBeforeEnterZone.put(3, new ArrayList<>());
        timeWaitBeforeEnterZone.put(1, new ArrayList<>());
        timeWaitBeforeEnterZone.put(2, new ArrayList<>());
        timeWaitBeforeEnterZone.put(5, new ArrayList<>());
    }
}
