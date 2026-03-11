package ControllerPackage;

import Habitat.Habitat;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    VeiwModel veiwModel = new VeiwModel();
    @FXML
    private Pane mainPane;

    @FXML
    private Button startButton, startButton1, stopButton,
            stopButton1, timerButton, timerButton1, statsButton;

    private AnimationTimer timer;

    @FXML
    private Label timerLabel;
    String time;

    @FXML
    private Label statsLabel;
    private boolean showStats = false;

    @FXML
    private RadioButton hideTimer,showTimer, showTimer1, hideTimer1;
    @FXML
    private MenuItem startItem, stopItem;
    @FXML
    private RadioMenuItem itemShow, itemHide;

    private ToggleGroup tglGroup = new ToggleGroup();
    private ToggleGroup tglGroup1 = new ToggleGroup();
    private ToggleGroup tglMeniGroup = new ToggleGroup();
    @FXML
    private CheckBox checkBox, checkBox1;
    @FXML
    private CheckMenuItem checkMenuItem;

    @FXML
    private TextField wrkField, wrkField1, wrrField, wrrField1;

    @FXML
    private ComboBox<String> percentageBox;
    private final String[] percentageArray = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
    @FXML
    private ComboBox<String> antTypeBox;
    private final String[] antsTypes = {"Default", "Warrior", "Worker"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Habitat.getInstance().setPane(mainPane);

        startButton.disableProperty().bind(veiwModel.getRunningProperty());
        startButton1.disableProperty().bind(veiwModel.getRunningProperty());
        startItem.disableProperty().bind(veiwModel.getRunningProperty());

        stopButton.disableProperty().bind(veiwModel.getRunningProperty().not());
        stopButton1.disableProperty().bind(veiwModel.getRunningProperty().not());
        stopItem.disableProperty().bind(veiwModel.getRunningProperty().not());

        statsLabel.textProperty().bind(veiwModel.getCurrentStats());
        timerLabel.textProperty().bind(veiwModel.getCurrentTime());

        showTimer.toggleGroupProperty();
        timerLabel.visibleProperty().bind(veiwModel.getTimerProperty());

        showTimer.setToggleGroup(tglGroup);
        hideTimer.setToggleGroup(tglGroup);
        showTimer1.setToggleGroup(tglGroup1);
        hideTimer1.setToggleGroup(tglGroup1);
        tglMeniGroup.selectToggle(itemShow);
        tglMeniGroup.selectToggle(itemHide);
        showTimer1.selectedProperty().bindBidirectional(showTimer.selectedProperty());
        hideTimer1.selectedProperty().bindBidirectional(hideTimer.selectedProperty());
        itemHide.selectedProperty().bindBidirectional(hideTimer1.selectedProperty());
        itemShow.selectedProperty().bindBidirectional(showTimer1.selectedProperty());

        checkBox.selectedProperty().bindBidirectional(veiwModel.getCheckBoxProperty());
        checkBox1.selectedProperty().bindBidirectional(veiwModel.getCheckBoxProperty());
        checkMenuItem.selectedProperty().bindBidirectional(veiwModel.getCheckBoxProperty());

        antTypeBox.setValue("Default");
        statsLabel.visibleProperty().bind(veiwModel.getStatsLabelProperty());

        antTypeBox.getItems().addAll(antsTypes);
        percentageBox.getItems().addAll(percentageArray);
        percentageBox.visibleProperty().bind(veiwModel.getPercentBoxProperty());
        percentageBox.valueProperty().bindBidirectional(veiwModel.getResetPercent());

        veiwModel.getErrorAlert().addListener((obs, oldValue, newValue)->{
            if (newValue != null && !newValue.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText(newValue);
                alert.showAndWait();

                veiwModel.getErrorAlert().set("");
            }
        });



        antTypeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue)->{
            veiwModel.setAntBoxValue(oldValue, newValue);
        });

        percentageBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue)->{
            veiwModel.setPercentBoxValue(newValue, antTypeBox.getValue());
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
                veiwModel.update(l);
            }
        };


    }

    @FXML
    void start(){
        veiwModel.startSimulation();
        timer.start();
    }

    @FXML
    void stop(){
        timer.stop();

        Alert alert = new Alert(Alert.AlertType.NONE, veiwModel.getEndStats().getValue());
        alert.setTitle("INFORMATION");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK){
            veiwModel.stopSimulation();
        }
        else {
            timer.start();
            veiwModel.resumeAfterCancel();
        }

    }

    @FXML
    public void showTimer(){
        veiwModel.changeTimerButton();
    }

    @FXML
    public void showStatsLabel(){
        veiwModel.changeStatsLabel();
    }

    @FXML
    void handleInputWrk(){
        veiwModel.parseInputText(wrkField.getText(), wrkField1.getText(), "Worker");
        wrkField.clear();
        wrkField1.clear();
    }

    @FXML
    void handleInputWrr(){
        veiwModel.parseInputText(wrrField.getText(), wrrField1.getText(), "Warrior");
        wrrField.clear();
        wrrField1.clear();
    }

    @FXML
    public void showCurrentObjects(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CURRENT OBJECTS");
        alert.setHeaderText(null);
        alert.setContentText(veiwModel.getCurrentObjects().getValue());
        alert.showAndWait();
    }
}




