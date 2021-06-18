package pt.ual.pp.models;

import java.util.Map;

public class MThread extends Thread {
    private Map<Integer, Zona> mapZonas;
    private Modelo modelo;

    public MThread(Modelo modelo, Map<Integer, Zona> mapZonas) {
        this.mapZonas = mapZonas;
        this.modelo = modelo;
    }

    @Override
    public void run() {
        long durationTotal = 0;
        for(Integer i : modelo.ordemZone) {
//            System.out.println("Zona " + i +  "quantity " + mapZonas.get(i).getQuantity());
            long startTime = System.currentTimeMillis();
            mapZonas.get(i).enterZona();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            modelo.addTimeWaitBeforeEnterZone(i, (double) duration);
            durationTotal += (endTime - startTime);
//            System.out.println("Modelo " + modelo +  "zona " + i + " " + duration);
            // Passar para o modelo esse tempo
//            modelo.addTimePerBuild(modelo.sumTimeZone() + duration * 60);
            try {
                double timeToSleep = modelo.timeZone.get(i) * 60;
                sleep((long) timeToSleep);
//                System.out.println("Tempo dormindo " + i + " " + timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mapZonas.get(i).leaveZone();
        }
        modelo.addTimePerBuild(modelo.sumTimeZone() + durationTotal);
    }
}
