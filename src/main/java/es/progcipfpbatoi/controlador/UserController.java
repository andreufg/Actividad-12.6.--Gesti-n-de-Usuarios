package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private ListView<User> userListView;

    @FXML
    private TextField searchBar;

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private ObservableList<User> getData() {
        return FXCollections.observableArrayList(userRepository.findAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userListView.setItems(getData());
        userListView.setCellFactory((ListView<User> l) -> new UserListViewCellController(userListView, this, userRepository));
    }

    /**
     * Se ejecuta al hacer click en el botón Nuevo
     * @param event
     */
    @FXML
    private void goToNewUserForm(ActionEvent event) {
        try {
            NewUserController newUserController = new NewUserController(userRepository, this, "/vistas/user_list.fxml");
            ChangeScene.change(event, newUserController, "/vistas/user_form.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            AlertMessages.mostrarAlertError("No se ha podido acceder al formulario de inserción de usuarios");
        }
    }

    /**
     * Se ejecuta al escribir sobre la barra de búsqueda (busca por email).
     */
    @FXML
    private void searchUsers() {

        userListView.getItems().clear();
        String texto = searchBar.getText();
        ArrayList<User> users = userRepository.findAll(texto);
        userListView.getItems().addAll(users);
    }
}
