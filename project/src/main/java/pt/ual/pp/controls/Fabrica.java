package pt.ual.pp.controls;

import pt.ual.pp.models.*;

import java.util.*;

public class Fabrica {
    private Map<Integer, ArrayList<Integer>> diaMap = new TreeMap<>();
    private Map<Integer, Modelo> modelos = new TreeMap<>();
    private Map<Integer, Zona> zonas = new TreeMap<>();
    private Random random = new Random();

    private Map<Integer, List<Double>> lineTimeInZone = new TreeMap<>();

    private Map<Integer, ArrayList<Integer>> ritmoModelos = new TreeMap<>();

    public Fabrica(Map<Integer, ArrayList<Integer>> ritmoModelos) {
        this.ritmoModelos = ritmoModelos;
    }

    public Map<Integer, List<Double>> getLineTimeInZone() {
        return lineTimeInZone;
    }

    public Map<Integer, Modelo> getModelos() {
        return modelos;
    }

    public Map<Integer, ArrayList<Integer>> getDiaMap() {
        return diaMap;
    }

    public void startSimulation(List<Integer> linesPerZone) {
        for(int i = 0; i < 5; i++) {
            zonas.put(i+1, new Zona(linesPerZone.get(i)));
            for(int j = 0; j< linesPerZone.get(i); j++) {
                if(!lineTimeInZone.containsKey(i+1)) {
                    lineTimeInZone.put(i+1, new ArrayList<>());
                }
                lineTimeInZone.get(i+1).add(0.0);
            }
        }


        Modelo modelo1 = new Modelo1(ritmoModelos.get(1).get(0), ritmoModelos.get(1).get(1), random);
        Modelo modelo2 = new Modelo2(ritmoModelos.get(2).get(0), ritmoModelos.get(2).get(1), random);
        Modelo modelo3 = new Modelo3(ritmoModelos.get(3).get(0), ritmoModelos.get(3).get(1), random);

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


//        System.out.println(diaMap);
        for(Integer i : diaMap.keySet()) {
//            System.out.println("------------- Dia " + i + " ------------");
            if(diaMap.get(i).size() == 1) {
                modelos.get(diaMap.get(i).get(0)).addTimePerBuild(modelos.get(diaMap.get(i).get(0)).sumTimeZone());
//                System.out.println("Modelo " + diaMap.get(i).get(0) + ": " + modelos.get(diaMap.get(i).get(0)).buildAveragedTime() + " minutos");
                for(Integer j : modelos.get(diaMap.get(i).get(0)).getOrdemZone()) {
                    modelos.get(diaMap.get(i).get(0)).addTimeWaitBeforeEnterZone(j, 0);
                    double sum = lineTimeInZone.get(j).get(0) + modelos.get(diaMap.get(i).get(0)).getTimeZone().get(j) * 60;
                    lineTimeInZone.get(j).set(0, sum);
                }
            }
            else {

                List<Thread> threads = new ArrayList<>();
                for(Integer j : diaMap.get(i)) {
                    threads.add(new MThread(modelos.get(j), zonas, lineTimeInZone));
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
    }
}
