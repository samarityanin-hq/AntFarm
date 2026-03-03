package Habitat;


import AntPackage.Ant;
import AntPackage.AntWarrior;
import AntPackage.AntWorker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


import java.util.*;

public class Habitat {
    private static Habitat instance;
    private HabitatConfig conf = new HabitatConfig();
    private AntFactory factory = new AntFactory();
    private HabitatStatistic statistic = new HabitatStatistic();

    private Pane pane;
    private Timeline timeline;

    private long spawnTime1, spawnTime2, simulationStart;
    private boolean run = false;

    private Vector<Ant> antVector = new Vector<>();
    private HashSet<Integer> idSet = new HashSet<>();
    private TreeMap<Long, Integer> timeTree = new TreeMap<>();

    private Habitat(){};

    public static Habitat getInstance(){
        if (instance == null){
            return instance = new Habitat();
        }
        else {
            return instance;
        }
    }

    public void update(){
        if (pane == null) return;

        long currentTime = System.currentTimeMillis();

        Iterator<Ant> vecIter = antVector.iterator();

        while (vecIter.hasNext()){
            Ant ant = vecIter.next();
            ant.move(pane.getWidth(), pane.getHeight());

            if (currentTime - ant.getTimeOfBirth() >= ant.getLifeTime()){
                pane.getChildren().remove(ant.getImgView());
                idSet.remove(ant.getID());
                timeTree.remove(ant.getTimeOfBirth());
                vecIter.remove();
            }
        }
    }

    public void generateAnts(){
        long currentTime = System.currentTimeMillis();
        Random random = new Random();

        if (currentTime - spawnTime1 >= conf.getWorkerSpawnTime()){
            spawnTime1 = currentTime;
            if (random.nextFloat() < conf.getWorkerPercent()){
                registerAnt(factory.generateAnt("worker", pane, conf, idSet));
                conf.incrementTotalWorker();
            }
        }
        if (currentTime - spawnTime2 >= conf.getWarriorSpawnTime()){
            spawnTime2 = currentTime;
            if (random.nextFloat() < conf.getWarriorPercent()){
                registerAnt(factory.generateAnt("warrior", pane, conf, idSet));
                conf.incrementTotalWarrior();
            }
        }
    }

    public void startSimulation(){
        if (run)return;
        simulationStart = System.currentTimeMillis();


        if (timeline == null){
            timeline = new Timeline(new KeyFrame(Duration.millis(100),
                    e-> {generateAnts();
                })
            );
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        run = true;

    }

    public void stopSimulation(){
        if (timeline != null)
            timeline.stop();
        for (Ant ant : antVector){
            pane.getChildren().remove(ant.getImgView());
        }

        antVector.clear();
        idSet.clear();
        timeTree.clear();
        run = false;
    }

    public void registerAnt(Ant ant){
        antVector.add(ant);
        pane.getChildren().add(ant.getImgView());
        timeTree.put(ant.getTimeOfBirth(), ant.getID());
        idSet.add(ant.getID());
    }

    public void setPane(Pane pane){
        this.pane = pane;
    }

    public void setTimeline(Boolean bool){
        if (bool){
            timeline.play();
        }
        else {timeline.stop();}
    }

    public HabitatConfig getConfiguration(){
        return conf;
    }
    public Pane getPane(){return pane;}
    public String getAllStats(){return statistic.showStats(conf);}
    public String getCurrentStats(){return statistic.currentObjects(timeTree, simulationStart);}
}
