package es.alex.easygestor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.mysql.cj.xdevapi.Client;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	
        scene = new Scene(loadFXML("primary"), 750, 420); //680 400
        //scene.getStylesheets().add(getClass().getResource("resources/es/alex/css/DarkTheme.css").toExternalForm());
        //scene.getStylesheets().add(getClass().getResource("resources/es/alex/css/DarkTheme.css").toExternalForm());
        
        //scene.getStylesheets().add(getClass().getResource("/css/DarkTheme.css").toString());
        
        
        // Importantisimo la ruta no puede empezar por /
        stage.getIcons().add(new Image(App.class.getResourceAsStream("image/ico.png")));
        
        
        stage.setScene(scene);
        
        stage.setResizable(false);
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
    	
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}