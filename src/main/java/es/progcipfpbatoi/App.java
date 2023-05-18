package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.UserController;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.FileUserDAO;
import es.progcipfpbatoi.modelo.dao.InMemoryUserDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.services.MySqlConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, DatabaseErrorException {

        FileUserDAO fileUserDAO = new FileUserDAO();
        UserRepository userRepository = new UserRepository(fileUserDAO);

//        InMemoryUserDAO inMemoryUserDAO = new InMemoryUserDAO();
//        for (User user:inMemoryUserDAO.findAll()) {
//            userRepository.save(user);
//        }
        UserController userController = new UserController(userRepository);
        ChangeScene.change(stage, userController, "/vistas/user_list.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}