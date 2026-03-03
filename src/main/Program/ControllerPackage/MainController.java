package ControllerPackage;

import Habitat.Habitat;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private Button startButton;
    @FXML
    private Button startButton1;
    @FXML
    private Button stopButton;
    @FXML
    private Button stopButton1;
    @FXML
    private Button timerButton;
    @FXML
    private Button timerButton1;
    @FXML
    private Button statsButton;

    @FXML
    private TextField PeriodField;

    @FXML
    private TextField lifeField;

    private AnimationTimer timer;

    @FXML
    private Label timerLabel;
    private long startTime = -1;
    boolean showLabelTimer = true;
    String time;

    @FXML
    private Label statsLabel;
    private boolean showStats = false;

    @FXML
    private ComboBox<String> percentageBox;
    private final String[] percentageArray = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
    @FXML
    private ComboBox<String> antTypeBox;
    private final String[] antsTypes = {"Default", "Warrior", "Worker"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Habitat.getInstance().setPane(mainPane);

        stopButton.setDisable(true);
        stopButton1.setDisable(true);
        statsLabel.setVisible(false);

        percentageBox.getItems().addAll(percentageArray);
        percentageBox.setVisible(false);

        antTypeBox.getItems().addAll(antsTypes);
        antTypeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue)->{
            percentageBox.setVisible(!newValue.equals("Default"));
        });

        percentageBox.getSelectionModel().selectedItemProperty().addListener((obs, newValue, oldValue)->{
            if (newValue==null || antTypeBox.getValue()==null){return;}

            float percent = Integer.parseInt(newValue) / 100f;

            if ("Worker".equals(antTypeBox.getValue())){
                Habitat.getInstance().getConfiguration().setWorkerPercent(percent);
            }
            else if ("Warrior".equals(antTypeBox.getValue())) {
                Habitat.getInstance().getConfiguration().setWarriorPercent(percent);
            } else if ("Default".equals(antTypeBox.getValue())) {
                Habitat.getInstance().getConfiguration().setWarriorPercent(0.6f);
                Habitat.getInstance().getConfiguration().setWorkerPercent(0.3f);

            }


        });

        Platform.runLater(() ->{
            mainPane.getScene().setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()){
                    case B -> start();
                    case E -> stop();
                    case T -> showTimer();
                }
            });
        });
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (startTime < 0){
                    startTime = l;
                }
                Habitat.getInstance().update();
                if (showStats) {
                    statsLabel.setText(Habitat.getInstance().getAllStats());
                }
                Habitat.getInstance().getCurrentStats();
                long seconds = (l - startTime) / 1000000000;
                long sec = seconds % 60;
                long min = seconds / 60;

                timerLabel.setText("Passed: " + min +"." + sec + " time");
                time = "Passed: " + min +" minutes " + sec + " seconds";


            }
        };


    }

    @FXML
    void start(){
        Habitat.getInstance().startSimulation();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        startButton1.setDisable(true);
        stopButton1.setDisable(false);
        timer.start();

    }

    @FXML
    void stop(){
        timer.stop();
        Habitat.getInstance().setTimeline(false);
        Alert alert = new Alert(Alert.AlertType.NONE, Habitat.getInstance().getAllStats() + time);
        alert.setTitle("INFORMATION");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            startButton.setDisable(false);
            stopButton.setDisable(true);
            startButton1.setDisable(false);
            stopButton1.setDisable(true);
            startTime = -1;
            Habitat.getInstance().stopSimulation();
            Habitat.getInstance().getConfiguration().setCountToZero();
        }
        else {
            Habitat.getInstance().setTimeline(true);
            timer.start();

        }
    }

    @FXML
    public void showTimer(){
        if (showLabelTimer){
            timerButton.setText("SHOW TIMER");
            timerButton1.setText("SHOW TIMER");
        }
        else {
            timerButton.setText("HIDE TIMER");
            timerButton1.setText("HIDE TIMER");
        }
        showLabelTimer = !showLabelTimer;
        timerLabel.setVisible(showLabelTimer);

    }
    @FXML
    public void showStatsLabel(){
        statsLabel.setVisible(!showStats);
        showStats = !showStats;

    }

    @FXML
    void handleInputText(ActionEvent event){

        TextField source = (TextField) event.getSource();

        String txtPeriod = source.getText();
        if (txtPeriod.isEmpty()){return;}
        try {
            int seconds = Integer.parseInt(txtPeriod) * 1000;

            if (seconds > 10000000){
                throw new IllegalArgumentException("VALUE IS TO BIG");
            }
            if (seconds < 0){
                throw new IllegalArgumentException("CANNOT INPUT NEGATIVE VALUE");
            }

            if (source == PeriodField){
                updateSpawnTime(seconds);
            }
            else if (source == lifeField){
                updateLifeTime(seconds);
            }


        }catch (NumberFormatException exception){
            errorAlert("FORMAT ERROR", "INPUT DIGIT");
        }
        catch (IllegalArgumentException exception){
            errorAlert("UNACCEPTABLE VALUE", exception.getMessage());
        }
        finally {
            source.clear();
        }

    }

    public void updateSpawnTime(int seconds){
        System.out.println("вызван метод изменения периодов спавна");
        if ("Worker".equals(antTypeBox.getValue())){
            Habitat.getInstance().getConfiguration().setWorkerSpawnTime(seconds);
            System.out.println("команда принята 1");
        }
        else if ("Warrior".equals(antTypeBox.getValue())) {
            Habitat.getInstance().getConfiguration().setWarriorSpawnTime(seconds);
            System.out.println("команда принята 2");
        }
        else{
            Habitat.getInstance().getConfiguration().setWorkerSpawnTime(5000);
            Habitat.getInstance().getConfiguration().setWarriorSpawnTime(10000);
            System.out.println("команда принята  3");

        }

    }

    public void updateLifeTime(int seconds){
        System.out.println("вызван метод изменения времени жизни");
        if ("Worker".equals(antTypeBox.getValue())){
            Habitat.getInstance().getConfiguration().setWrkLive(seconds);
            System.out.println("команда принята 1");
        }
        else if ("Warrior".equals(antTypeBox.getValue())) {
            Habitat.getInstance().getConfiguration().setWrrLive(seconds);
            System.out.println("команда принята 2");
        }
        else{
            Habitat.getInstance().getConfiguration().setWrkLive(8000);
            Habitat.getInstance().getConfiguration().setWrrLive(15000);
            System.out.println("команда принята  3");

        }
    }

    public void errorAlert(String alertTitle, String alertMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();

    }
    @FXML
    public void showCurrentObjects(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CURRENT OBJECTS");
        alert.setHeaderText(null);
        alert.setContentText(Habitat.getInstance().getCurrentStats());
        alert.showAndWait();
    }
}




