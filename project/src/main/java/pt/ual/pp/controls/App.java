package pt.ual.pp.controls;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, ArrayList<Integer>> ritmoModelos = new TreeMap<>();

        System.out.println("Escolha o ritmo de chegada de encomenda para os modelos:");
        System.out.println("De \"Inicio\" ate \"Fim\"(incluso)");
        for(int i = 0; i < 3; i++) {
            if(!ritmoModelos.containsKey(i+1)) {
                ritmoModelos.put(i+1, new ArrayList<>());
            }
            System.out.print("Modelo " + (i+1) + " inicio: ");
            int inicio = Integer.parseInt(sc.nextLine());
            System.out.print("Modelo " + (i+1) + " fim: ");
            int fim = Integer.parseInt(sc.nextLine());
            ritmoModelos.get(i+1).add(inicio);
            ritmoModelos.get(i+1).add(fim);
        }

        Fabrica fabrica = new Fabrica(ritmoModelos);

        System.out.println();

        List<Integer> linesPerZone = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            System.out.print("Zona " + (i+1) + ". Indique o numero de linhas: ");
            int lines = Integer.parseInt(sc.nextLine());
            linesPerZone.add(lines);
        }


        fabrica.startSimulation(linesPerZone);

        System.out.println();

        System.out.println("Modelo 1 tempo medio de construcao: " + String.format("%.2f", fabrica.getModelos().get(1).buildAveragedTime()) + " minutos.");
        System.out.println("Modelo 2 tempo medio de construcao: " + String.format("%.2f", fabrica.getModelos().get(2).buildAveragedTime()) + " minutos.");
        System.out.println("Modelo 3 tempo medio de construcao: " + String.format("%.2f", fabrica.getModelos().get(3).buildAveragedTime()) + " minutos.");

        System.out.println();

        for(Integer i : fabrica.getModelos().keySet()) {
            System.out.print("Modelo " + i + " tempo medio de espera: ");
            System.out.println(String.format("%.2f", fabrica.getModelos().get(i).waitTimeAvarage()) + " minutos.");
            Map<Integer, Double> waitTimeAvarageModelo = fabrica.getModelos().get(i).waitTimeAvaragePerZone();
            System.out.println("Modelo " + i + " tempo medio entre zonas:");
            for(int j : waitTimeAvarageModelo.keySet()) {
                System.out.println("Zona " + j + " Tempo: " + String.format("%.2f",waitTimeAvarageModelo.get(j)) + " minutos.");
            }
            System.out.println();
        }

        System.out.println();

        int lastDayOfWork = 0;
        int firstDayOfWork = 0;
        int k = 0;
        for(Integer i : fabrica.getDiaMap().keySet()) {
            k++;
            if(k == 1) firstDayOfWork = i;
            lastDayOfWork = i;
        }
        int daysOfWork = lastDayOfWork - firstDayOfWork;
        System.out.println("Dias de trabalho no ano: " + daysOfWork);
        System.out.println("As linhas comecam a trabalhar assim que a primeira encomenda chega e " +
                "terminam assim que a ultima encomenda termina de ser produzida.");
        System.out.println("Percentagem de tempo de utilizacao de cada linha de trabalho " +
                " em relacao a todo o periodo do ano: ");
        int minutesInYearOfWork = daysOfWork * 24 * 60;
        for(int zona : fabrica.getLineTimeInZone().keySet()) {
            for(int i = 0; i < fabrica.getLineTimeInZone().get(zona).size(); i++) {
                System.out.print("Zona " + zona + ". Linha " + (i+1) + ": ");
                System.out.println(String.format("%.2f", fabrica.getLineTimeInZone().get(zona).get(i) / minutesInYearOfWork * 100) + "%");
            }
            System.out.println();
        }


    }
}
