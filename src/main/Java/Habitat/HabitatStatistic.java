package Habitat;

import java.util.TreeMap;

public class HabitatStatistic {

    public String showStats(HabitatConfig conf){

        conf.setTotal();
        return "Total ants generated: "+ conf.getTotal() +"\n"+
                "Ants workers: " + conf.getTotalWorker() +"\n"+
                "Ants warriors: " + conf.getTotalWarrior() + "\n";
    }

    public String currentObjects(TreeMap<Long, Integer> timeTree,  long simulationStart){
        StringBuilder txt = new StringBuilder();
        for (long time : timeTree.keySet()){
            txt.append("ID: ").append(timeTree.get(time)).append(
                    " Time: ").append(
                    (time - simulationStart) / 1000.0).append("\n");
        }

        return txt.toString();
    }
}
