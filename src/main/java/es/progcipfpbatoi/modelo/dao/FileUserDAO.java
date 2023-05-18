package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileUserDAO implements UserDAO{
    private static final String DATABASE_FILE = "resources/database/usuarios.txt";
    private static final int DNI = 0;
    private static final int NAME = 1;
    private static final int SURNAME = 2;
    private static final int EMAIL = 3;
    private static final int ZIPCODE = 4;
    private static final int MOBILEPHONE = 5;
    private static final int BIRTHDAY = 6;
    private static final int PASSWORD = 7;
    private File file;

    public FileUserDAO() {
        this.file = new File(DATABASE_FILE);
    }

    @Override
    public ArrayList<User> findAll() throws DatabaseErrorException {
        try{
            ArrayList<User> usuarios = new ArrayList<>();
            try(BufferedReader bufferedReader = getReader()){

                do {
                    String register = bufferedReader.readLine();
                    if (register == null){
                        return usuarios;
                    }
                    usuarios.add(getUserFromRegister(register));
                }while (true);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private BufferedReader getReader() throws IOException {
        return new BufferedReader(new FileReader(file));
    }
    private User getUserFromRegister(String register){
        String[] field = register.split(";");
        String name = field[NAME];
        String surname = field[SURNAME];
        String dni = field[DNI];
        String email = field[EMAIL];
        String zipCode = field[ZIPCODE];
        String mobilePhone = field[MOBILEPHONE];
        LocalDate birthday = LocalDate.parse(field[BIRTHDAY]);
        String password = field[PASSWORD];
        return new User(name,surname,dni,email,zipCode,mobilePhone,birthday,password);
    }

    @Override
    public ArrayList<User> findAll(String email) throws DatabaseErrorException {
        ArrayList<User> usuarios = new ArrayList<>();
        for (User usuario:findAll()) {
            if (usuario.getEmail().equals(email)){
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public User getById(String dni) throws NotFoundException, DatabaseErrorException {
        for (User usuario:findAll()) {
            if (usuario.getDni().equals(dni)){
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void save(User user) throws DatabaseErrorException {
        try {

                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {

                    if (getById(user.getDni())==null){
                        bufferedWriter.write(getRegisterFromUser(user));
                        bufferedWriter.newLine();
                    }else {
                        for (User usuario :findAll()) {
                            if (!usuario.equals(user)) {
                                bufferedWriter.write(getRegisterFromUser(usuario));
                                bufferedWriter.newLine();
                            }else {
                                updateOrRemove(user,true);
                            }

                        }

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateOrRemove(User user, boolean update) throws DatabaseErrorException {
        ArrayList<User> users = findAll();
        try (BufferedWriter bufferedWriter = getWriter(false)) {
            for (User userItem : users) {
                if (!userItem.equals(user)) {
                    bufferedWriter.write(getRegisterFromUser(userItem));
                    bufferedWriter.newLine();
                }else if (update) {
                    bufferedWriter.write(getRegisterFromUser(user));
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new DatabaseErrorException("Error en el acceso a la base de datos de tareas");
        }
    }
    private BufferedWriter getWriter(boolean append) throws IOException {
        return new BufferedWriter(new FileWriter(file, append));
    }
    private String getRegisterFromUser(User user) {
        String[] fields = new String[8];
        fields[NAME] = user.getName();
        fields[SURNAME] = user.getSurname();
        fields[DNI] = user.getDni();
        fields[EMAIL] = user.getEmail();
        fields[ZIPCODE] = user.getZipCode();
        fields[MOBILEPHONE] = user.getMobilePhone();
        fields[BIRTHDAY] = String.valueOf(user.getBirthday());
        fields[PASSWORD] = user.getPassword();
        return String.join(";", fields);
    }


    @Override
    public void remove(User user) throws DatabaseErrorException, NotFoundException {
        updateOrRemove(user, false);
    }
}
