package pt.ual.pp.models;

import java.util.*;

public class Modelo1 extends Modelo {
    public Modelo1(int first, int last, Random random) {
        super(first, last, random);
        timeZone.put(4,1.10);
        timeZone.put(1, 0.80);
        timeZone.put(3, 1.10);
        ordemZone = Arrays.asList(4, 1, 3);
    }



}
