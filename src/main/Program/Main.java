
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/canvasLR3.fxml"));
        Scene scene = new Scene(loader.load(), 1024, 680);
        stage.setScene(scene);
        stage.setTitle("Ant farm");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
