
import StonesContent.Necklace;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.*;

public class Main extends Application {
    public static final Logger logger = Logger.getLogger(Main.class.getName());
    static Necklace content = new Necklace();

    public static void main(String[] args) throws Exception{
        Handler fileHandler = new FileHandler();
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/resources/diamond.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}