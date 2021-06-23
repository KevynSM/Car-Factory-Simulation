package pt.ual.pp.models;

import java.util.*;

public class Modelo {
    private int time;
    private int push;
    private Random random;
    protected Map<Integer, Double> timeZone = new TreeMap<>();
    protected List<Integer> ordemZone = new ArrayList<>();
    private List<Double> timePerBuild = new ArrayList<>();
    protected Map<Integer, List<Double>> timeWaitBeforeEnterZone = new TreeMap<>();
    private int waitQuantity = 0;
    private int buildQuantity = 0;


    public Modelo(int first, int last, Random random) {
        time = last - first + 1;
        push = first;
        this.random = random;
    }

    public List<Integer> daysOfWork(int lastDay) {
        List<Integer> daysList = new ArrayList<>();
        int day = 0;
        while(day <= lastDay) {
            int nextDay = random.nextInt(time) + push;
            day += nextDay;
            daysList.add(day);
        }
        return daysList;
    }

    public Double sumTimeZone() {
        Double total = 0.0;
        for(Integer i : ordemZone) {
            total += timeZone.get(i);
        }
        return total * 60;
    }


    public void addTimePerBuild(Double time) {
        timePerBuild.add(time);
        buildQuantity++;
    }

    public Double buildAveragedTime() {
        return timePerBuild.stream().mapToDouble(a -> a).sum()/buildQuantity;
    }

    public void addTimeWaitBeforeEnterZone(int zone, double time) {
        timeWaitBeforeEnterZone.get(zone).add(time);
        waitQuantity++;
    }

    public Map<Integer, Double> waitTimeAvarage() {
        Map<Integer, Double> map = new TreeMap<>();
        for(int i : timeWaitBeforeEnterZone.keySet()) {
            double total = 0;
            double avarage = 0;
            for(int j = 0; j < timeWaitBeforeEnterZone.get(i).size(); j++) {
                total += timeWaitBeforeEnterZone.get(i).get(j);
                if(j == timeWaitBeforeEnterZone.get(i).size() - 1) {
                    avarage = total / j;
//                    System.out.println("AVARAGE: " + avarage);
                }
            }

            map.put(i, avarage);
        }
        return map;
    }

    public List<Integer> getOrdemZone() {
        return ordemZone;
    }

    public Map<Integer, Double> getTimeZone() {
        return timeZone;
    }
}
