package ControllerPackage;

import Habitat.Habitat;
import javafx.beans.property.*;

public class VeiwModel {


    private BooleanProperty isRunning = new SimpleBooleanProperty(false);
    private BooleanProperty isCheckBoxProperty = new SimpleBooleanProperty(false);
    private BooleanProperty stopConfirmation = new SimpleBooleanProperty(false);
    private BooleanProperty timerProperty = new SimpleBooleanProperty(true);
    private BooleanProperty statsLabelProperty = new SimpleBooleanProperty(false);
    private BooleanProperty percentBoxProperty = new SimpleBooleanProperty(false);

    private LongProperty startTime = new SimpleLongProperty(-1);

    private StringProperty currentTime = new SimpleStringProperty();
    private StringProperty endStats = new SimpleStringProperty();
    private StringProperty currentStats = new SimpleStringProperty();
    private StringProperty currentObjects = new SimpleStringProperty();
    private StringProperty resetPercent = new SimpleStringProperty();
    private StringProperty errorAlert = new SimpleStringProperty();


    public void startSimulation(){
        Habitat.getInstance().startSimulation();
        isRunning.set(true);
    }
    public void stopSimulation(){
        isRunning.set(false);
        Habitat.getInstance().setTimeline(false);
        Habitat.getInstance().stopSimulation();
        Habitat.getInstance().getConfiguration().setCountToZero();
        startTime.setValue(-1);

    }

    public void changeTimerButton(){
        timerProperty.set(!timerProperty.getValue());
    }

    public void changeStatsLabel(){
        statsLabelProperty.set(!statsLabelProperty.getValue());
    }


    public void resumeAfterCancel(){
        isRunning.set(true);
        Habitat.getInstance().setTimeline(true);
    }

    public void update(long l){
        if (startTime.getValue() < 0){
            startTime.set(l);
        }
        Habitat.getInstance().update();
        if (statsLabelProperty.getValue()) {
            currentStats.set(Habitat.getInstance().getAllStats());
        }
        currentObjects.set(Habitat.getInstance().getCurrentStats());
        long seconds = (l - startTime.getValue()) / 1000000000;
        long sec = seconds % 60;
        long min = seconds / 60;

        currentTime.set("Passed: " + min +"." + sec + " time");
        endStats.set(Habitat.getInstance().getAllStats()
                        + "\nPassed: " + min +" minutes " + sec + " seconds");
    }

    public void setAntBoxValue(String oldValue, String newValue){
        if (newValue == null){return;}
        if (newValue.equals("Default")){
            percentBoxProperty.set(false);
        }
        else {
            resetPercent.set(null);
            percentBoxProperty.set(true);
        }
    }


    public void setPercentBoxValue(String newValue, String antValue){
        if (newValue == null || antValue == null){return;}

        float percent = Integer.parseInt(newValue) / 100f;

        switch (antValue) {
            case "Worker" -> Habitat.getInstance().getConfiguration().setWorkerPercent(percent);
            case "Warrior" -> Habitat.getInstance().getConfiguration().setWarriorPercent(percent);
            case "Default" -> {
                Habitat.getInstance().getConfiguration().setWarriorPercent(0.6f);
                Habitat.getInstance().getConfiguration().setWorkerPercent(0.3f);
            }
        }
    }

    public void parseInputText(String periodField, String lifeField, String type){

        if (periodField.isEmpty() && lifeField.isEmpty()){return;}

        try {
            if (!periodField.isEmpty()){

                int periodSec = Integer.parseInt(periodField) * 1000;
                if (periodSec < 0 ){throw new IllegalArgumentException("VALUE CANT BE NEGATIVE");}

                if (type.equals("Worker")) {
                    Habitat.getInstance().getConfiguration().setWorkerSpawnTime(periodSec);
                }
                else {
                    Habitat.getInstance().getConfiguration().setWarriorSpawnTime(periodSec);
                }
            }
            if (!lifeField.isEmpty()){

                int lifeSec = Integer.parseInt(lifeField) * 1000;
                if (lifeSec < 0 ){throw new IllegalArgumentException("VALUE CANT BE NEGATIVE");}

                if (type.equals("Worker")) {
                    Habitat.getInstance().getConfiguration().setWrkLive(lifeSec);
                }
                else {
                    Habitat.getInstance().getConfiguration().setWrrLive(lifeSec);
                }
            }

        }catch (NumberFormatException exception){
            errorAlert.set("FORMAT ERROR: INPUT DIGIT");
        }
        catch (IllegalArgumentException exception){
            errorAlert.set("UNACCEPTABLE VALUE: " + exception.getMessage());
        }

    }

    public BooleanProperty getPercentBoxProperty(){
        return percentBoxProperty;
    }
    public BooleanProperty getRunningProperty(){
        return isRunning;
    }
    public BooleanProperty getCheckBoxProperty(){
        return isCheckBoxProperty;
    }
    public BooleanProperty getTimerProperty(){
        return timerProperty;
    }
    public BooleanProperty getStatsLabelProperty() {
        return statsLabelProperty;
    }
    public StringProperty getCurrentTime(){
        return currentTime;
    }
    public StringProperty getCurrentObjects(){
        return currentObjects;
    }
    public StringProperty getEndStats(){
        return endStats;
    }
    public StringProperty getCurrentStats(){
        return currentStats;
    }
    public StringProperty getResetPercent(){
        return resetPercent;
    }
    public StringProperty getErrorAlert(){
        return errorAlert;
    }

}
