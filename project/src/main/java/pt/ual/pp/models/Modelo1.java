package pt.ual.pp.models;

import java.util.*;

public class Modelo1 extends Modelo {
    public Modelo1(int first, int last, Random random) {
        super(first, last, random);
        timeZone.put(4, 1.10);
        timeZone.put(1, 0.80);
        timeZone.put(3, 0.75);
        ordemZone = Arrays.asList(4, 1, 3);
        timeWaitBeforeEnterZone.put(4, new ArrayList<>());
        timeWaitBeforeEnterZone.put(1, new ArrayList<>());
        timeWaitBeforeEnterZone.put(3, new ArrayList<>());
    }



}
