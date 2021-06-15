package pt.ual.pp.models;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Modelo2 extends Modelo {
    public Modelo2(int first, int last, Random random) {
        super(first, last, random);
        timeZone.put(3, 0.50);
        timeZone.put(1, 0.60);
        timeZone.put(2, 0.50);
        timeZone.put(5, 0.50);
        ordemZone = Arrays.asList(3, 1, 2, 5);
    }
}
