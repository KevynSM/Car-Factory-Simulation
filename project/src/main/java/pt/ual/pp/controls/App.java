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

        Zona zona1 = new Zona(1);
        Zona zona2 = new Zona(1);
        Zona zona3 = new Zona(1);
        Zona zona4 = new Zona(1);
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


        System.out.println(diaMap);
        for(Integer i : diaMap.keySet()) {
//            System.out.println("------------- Dia " + i + " ------------");
            if(diaMap.get(i).size() == 1) {
                modelos.get(diaMap.get(i).get(0)).addTimePerBuild(modelos.get(diaMap.get(i).get(0)).sumTimeZone());
//                System.out.println("Modelo " + diaMap.get(i).get(0) + ": " + modelos.get(diaMap.get(i).get(0)).buildAveragedTime() + " minutos");
            }
            else {

                List<Thread> threads = new ArrayList<>();
                for(Integer j : diaMap.get(i)) {
                    threads.add(new MThread(modelos.get(j), zonas));
                }
                for(Thread t : threads) {
                    t.start();
                }
                for(Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }



        System.out.println("Modelo 1 tempo medio:" + modelo1.buildAveragedTime());
        System.out.println("Modelo 2 tempo medio:" + modelo2.buildAveragedTime());
        System.out.println("Modelo 3 tempo medio:" + modelo3.buildAveragedTime());



        for(Integer i : modelos.keySet()) {
            Map<Integer, Double> waitTimeAvarageModelo = modelos.get(i).waitTimeAvarage();
            System.out.println("Modelo " + i + " tempo entre zonas:");
            for(int j : waitTimeAvarageModelo.keySet()) {
                System.out.println("Zona " + j + " Tempo: " + waitTimeAvarageModelo.get(j));
            }
        }


    }
}
