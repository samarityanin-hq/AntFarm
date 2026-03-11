package Habitat;

import AntPackage.Ant;
import AntPackage.AntWarrior;
import AntPackage.AntWorker;
import javafx.scene.layout.Pane;

import java.util.*;

public class AntFactory {
    private Random random = new Random();

    public Ant generateAnt(String type, Pane pane, HabitatConfig conf, HashSet<Integer> idSet){
        long time = System.currentTimeMillis();

        double x = random.nextDouble((pane.getWidth() - random.nextDouble(0, pane.getWidth() - 50)));
        double y = random.nextDouble((pane.getHeight() - random.nextDouble(0, pane.getHeight() - 50)));

        Ant ant;
        if (type.equals("worker")){
            ant = new AntWorker(x, y);
            ant.setLifeTime(conf.getWrkLive());
        }
        else{
            ant = new AntWarrior(x, y);
            ant.setLifeTime(conf.getWrrLive());
        }

        ant.setTimeOfBirth(time);
        ant.setID(generateID(idSet));

        return ant;

    }

    public int generateID(HashSet<Integer> idSet){
        int randomID;
        do {
            randomID = random.nextInt(10000);
        }while (idSet.contains(randomID));

        return randomID;
    }

}
