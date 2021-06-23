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

        Scanner sc = new Scanner(System.in);

        Map<Integer, List<Double>> lineTimeInZone = new TreeMap<>();

        for(int i = 0; i < 5; i++) {
            System.out.println("Zona " + (i+1) + ". Indique o numero de linhas: ");
            int lines = Integer.parseInt(sc.nextLine());
            zonas.put(i+1, new Zona(lines));
            for(int j = 0; j < lines; j++) {
                if(!lineTimeInZone.containsKey(i+1)) {
                    lineTimeInZone.put(i+1, new ArrayList<>());
                }
                lineTimeInZone.get(i+1).add(0.0);
            }
        }


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
                for(Integer j : modelos.get(diaMap.get(i).get(0)).getOrdemZone()) {
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



        System.out.println("Modelo 1 tempo medio:" + String.format("%.2f", modelo1.buildAveragedTime()));
        System.out.println("Modelo 2 tempo medio:" + String.format("%.2f", modelo2.buildAveragedTime()));
        System.out.println("Modelo 3 tempo medio:" + String.format("%.2f", modelo3.buildAveragedTime()));

        System.out.println();

        for(Integer i : modelos.keySet()) {
            Map<Integer, Double> waitTimeAvarageModelo = modelos.get(i).waitTimeAvarage();
            System.out.println("Modelo " + i + " tempo medio entre zonas:");
            for(int j : waitTimeAvarageModelo.keySet()) {
                System.out.println("Zona " + j + " Tempo: " + String.format("%.2f",waitTimeAvarageModelo.get(j)));
            }
        }

        System.out.println();


        int lastDayOfWork = 0;
        int firstDayOfWork = 0;
        int k = 0;
        for(Integer i : diaMap.keySet()) {
            k++;
            if(k == 1) firstDayOfWork = i;
            lastDayOfWork = i;
        }
        int daysOfWork = lastDayOfWork - firstDayOfWork;
        System.out.println("Dias de trabalho no ano: " + daysOfWork);
        System.out.println("As linhas comecam a trabalhar assim que a primeira encomenda chega e " +
                            "terminam assim que a ultima encomenda termina de ser produzida.");
        System.out.println("Percentagem de tempo de utilizacao de cada linha de trabalho " +
                            " em relacao a todo o periodo do ano");
        int minutesInYearOfWork = daysOfWork * 24 * 60;
        for(int zona : lineTimeInZone.keySet()) {
            for(int i = 0; i < lineTimeInZone.get(zona).size(); i++) {
                System.out.print("Zona " + zona + ". Linha " + (i+1) + ": ");
                System.out.println(String.format("%.2f", lineTimeInZone.get(zona).get(i) / minutesInYearOfWork * 100) + "%");
            }
            System.out.println();
        }

    }
}
