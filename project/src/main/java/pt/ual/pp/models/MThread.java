package pt.ual.pp.models;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MThread extends Thread {
    private Map<Integer, Zona> mapZonas;
    private Map<Integer, List<Double>> lineTimeInZone = new TreeMap<>();
    private Modelo modelo;

    public MThread(Modelo modelo, Map<Integer, Zona> mapZonas, Map<Integer, List<Double>> lineTimeInZone) {
        this.mapZonas = mapZonas;
        this.modelo = modelo;
        this.lineTimeInZone = lineTimeInZone;
    }

    @Override
    public void run() {
        long durationTotal = 0;
        for(Integer i : modelo.ordemZone) {
//            System.out.println("Zona " + i +  "quantity " + mapZonas.get(i).getQuantity());
            long startTime = System.currentTimeMillis();
            mapZonas.get(i).enterZone();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            modelo.addTimeWaitBeforeEnterZone(i, (double) duration);
            durationTotal += (endTime - startTime);
//            System.out.println("Modelo " + modelo +  "zona " + i + " " + duration);
            // Passar para o modelo esse tempo
//            modelo.addTimePerBuild(modelo.sumTimeZone() + duration * 60);
            double timeToSleep = modelo.timeZone.get(i) * 60;
            try {
                sleep((long) timeToSleep);
//                System.out.println("Tempo dormindo " + i + " " + timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int index = mapZonas.get(i).getQuantity()-1;
            double sum = lineTimeInZone.get(i).get(index) + timeToSleep;
            lineTimeInZone.get(i).set(index, sum);
            mapZonas.get(i).leaveZone();
        }
        modelo.addTimePerBuild(modelo.sumTimeZone() + durationTotal);
    }
}
