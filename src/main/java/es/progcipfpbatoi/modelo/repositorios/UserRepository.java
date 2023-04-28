package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;

import java.util.ArrayList;

public interface UserRepository {
    ArrayList<User> findAll();
    ArrayList<User> findAll(String email);

    User getById(String dni) throws NotFoundException;

    void save(User user) throws DatabaseErrorException;
    void remove(User user) throws NotFoundException;
}
