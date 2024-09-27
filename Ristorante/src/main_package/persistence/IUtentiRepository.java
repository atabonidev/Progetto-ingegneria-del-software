package main_package.persistence;

import main_package.model.user.Utente;

public interface IUtentiRepository {
    int getId(String username);
    Utente checkLogin(String usr, String pwd, String tipoUtente);
    boolean checkUsername(String user);
}
