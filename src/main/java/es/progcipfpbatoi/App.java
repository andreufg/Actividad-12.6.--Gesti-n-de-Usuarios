package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.UserController;
import es.progcipfpbatoi.modelo.repositorios.InMemoryUserRepository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        UserController userController = new UserController(inMemoryUserRepository);
        ChangeScene.change(stage, userController, "/vistas/user_list.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}