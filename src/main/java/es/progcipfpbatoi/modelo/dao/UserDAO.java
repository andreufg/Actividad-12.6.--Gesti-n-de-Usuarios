package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;

import java.util.ArrayList;

public interface UserDAO {
    /**
     *  Obtiene todas las user
     */
    ArrayList<User> findAll() throws DatabaseErrorException;

    /**
     * Obtiene todas las user que comiencen por @text
     * @param email
     * @return
     */
    ArrayList<User> findAll(String email) throws DatabaseErrorException;

    /**
     * Obtiene la user cuyo id sea @id
     * @param dni
     * @return
     * @throws NotFoundException
     */
    User getById(String dni) throws NotFoundException, DatabaseErrorException;

    /**
     * Almacena la user o la actualiza en caso de existir
     * @param user
     * @return
     * @throws DatabaseErrorException
     */
    void save(User user) throws DatabaseErrorException;

    /**
     * Elimina el usuario
     * @param user
     * @return
     * @throws DatabaseErrorException
     */
    void remove(User user) throws DatabaseErrorException, NotFoundException;
}
