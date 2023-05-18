package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class FileUserDAO implements UserDAO {
    private Connection connection;

    private static final String TABLE_NAME = "Users";

    @Override
    public ArrayList<User> findAll() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<User> users = new ArrayList<>();
        connection = new MySqlConnection().conectar();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                User user = getUserFromRegister(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return users;
    }

    public User findByDNI(String dni) throws DatabaseErrorException {
        try {
            return getById(dni);
        } catch (NotFoundException ex) {
            return null;
        }
    }

    private User getUserFromRegister(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String dni = resultSet.getString("dni");
        String email = resultSet.getString("email");
        String zipCode = resultSet.getString("zipCode");
        String mobilePhone = resultSet.getString("mobilePhone");
        LocalDate birthday = LocalDate.parse(resultSet.getString("birthday"));
        String password = resultSet.getString("password");
        return new User(name, surname, dni, email, zipCode, mobilePhone, birthday, password);
    }

    @Override
    public ArrayList<User> findAll(String email) throws DatabaseErrorException {
        ArrayList<User> usuarios = new ArrayList<>();
        for (User usuario : findAll()) {
            if (usuario.getEmail().equals(email)) {
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public User getById(String dni) throws NotFoundException, DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE dni = ?", TABLE_NAME);
        connection = new MySqlConnection().conectar();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = getUserFromRegister(resultSet);
                if (Objects.equals(user.getDni(), dni)) {
                    return user;
                }
            }

            throw new NotFoundException("No existe un usuario con el DNI " + dni);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    @Override
    public void save(User user) throws DatabaseErrorException {
        if (findByDNI(user.getDni()) == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    private User update(User user) throws DatabaseErrorException {
        String sql = String.format("UPDATE %s SET name = ?, surname = ?, email = ?, zipCode = ?, mobilePhone = ?, birthday = ?, password = ? WHERE dni = ?",
                TABLE_NAME);

        try (
                Connection connection = new MySqlConnection().conectar();
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getZipCode());
            statement.setString(5, user.getMobilePhone());
            statement.setString(6, user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setString(7, user.getPassword());
            statement.setString(8, user.getDni());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (update)");
        }

        return user;
    }

    private User insert(User user) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (name, surname, dni, email, zipCode, mobilePhone, birthday, password) VALUES (?,?,?,?,?,?,?,?)",
                TABLE_NAME);
        connection = new MySqlConnection().conectar();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getDni());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getZipCode());
            preparedStatement.setString(6, user.getMobilePhone());
            preparedStatement.setString(7, user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(8, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setDni(resultSet.getString(3));
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    @Override
    public void remove(User user) throws DatabaseErrorException, NotFoundException {
        String sql = String.format("DELETE FROM %s WHERE dni = ?", TABLE_NAME);
        connection = new MySqlConnection().conectar();
        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getDni());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (delete)");
        }
    }
}

