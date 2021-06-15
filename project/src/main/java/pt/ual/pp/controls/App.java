package pt.ual.pp.controls;

import pt.ual.pp.models.*;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Map<Integer, ArrayList<Integer>> diaMap = new TreeMap<>();
        Map<Integer, Modelo> modelos = new TreeMap<>();
        Map<Integer, Zona> zonas = new TreeMap<>();
        Random random = new Random();

        Zona zona1 = new Zona(3);
        Zona zona2 = new Zona(2);
        Zona zona3 = new Zona(4);
        Zona zona4 = new Zona(3);
        Zona zona5 = new Zona(1);

        zonas.put(1, zona1);
        zonas.put(2, zona2);
        zonas.put(3, zona3);
        zonas.put(4, zona4);
        zonas.put(5, zona5);

        Modelo modelo1 = new Modelo1(3, 7, random);
        Modelo modelo2 = new Modelo2(4, 6, random);
        Modelo modelo3 = new Modelo3(2, 5, random);

        modelos.put(1, modelo1);
        modelos.put(2, modelo2);
        modelos.put(3, modelo3);

        for(Integer i : modelo1.daysOfWork(365)) {
            if (!diaMap.containsKey(i)) {
                diaMap.put(i, new ArrayList());
            }
            diaMap.get(i).add(1);
        }

        for(Integer i : modelo2.daysOfWork(365)) {
            if (!diaMap.containsKey(i)) {
                diaMap.put(i, new ArrayList());
            }
            diaMap.get(i).add(2);
        }

        for(Integer i : modelo3.daysOfWork(365)) {
            if (!diaMap.containsKey(i)) {
                diaMap.put(i, new ArrayList());
            }
            diaMap.get(i).add(3);
        }

        for(Integer i : diaMap.keySet()) {
            if(diaMap.get(i).size() == 1) {
                modelos.get(diaMap.get(i).get(0)).addTimePerBuild(modelos.get(diaMap.get(i).get(0)).timePerBuild());
                System.out.println("Modelo " + diaMap.get(i).get(0) + ": " + modelos.get(diaMap.get(i).get(0)).timePerBuild() + " minutos");
            }
            else {
                for(Integer j : diaMap.get(i)) {
                    new MThread(modelos.get(j), zonas).start();
                }
            }
        }


        System.out.println(diaMap);
        System.out.println("Modelo 1 tempo medio:" + modelo1.buildAveragedTime());
        System.out.println("Modelo 2 tempo medio:" + modelo2.buildAveragedTime());
        System.out.println("Modelo 3 tempo medio:" + modelo3.buildAveragedTime());



    }
}
