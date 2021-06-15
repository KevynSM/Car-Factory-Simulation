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
        for(Integer i : modelo.ordemZone) {
            // Tempo antes?
            mapZonas.get(i).enterZona();
            // Tempo depois?
            // Passar para o modelo esse tempo
            try {
                sleep(modelo.timeZone.get(i).longValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mapZonas.get(i).leaveZone();
        }
    }
}
