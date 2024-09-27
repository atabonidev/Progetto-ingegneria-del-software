package main_package.controller.handlers;

import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.model.user.Utente;
import main_package.persistence.IUtentiRepository;
import main_package.view.View;

public class Login implements Handler {
    private IUtentiRepository utentiRepository;
    private String tipoUtente;

    public Login(IUtentiRepository utentiRepository, String tipoUtente) {
        this.utentiRepository = utentiRepository;
        this.tipoUtente = tipoUtente;
    }

    public Session execute(Session session, View view) {
        return doLogin(session, view);
    }

    private Session doLogin(Session session, View view) {
        String user = view.leggiStringaNonVuota("Inserisci username: ");
        String pass = view.leggiStringaNonVuota("Inserisci password: ");
        Utente utente = utentiRepository.checkLogin(user, pass, tipoUtente);
        if (utente != null) {
            session.setUtente(utente);
            session.setState(State.LOGGED);
            return session;
        } else view.print("Login Errato, credenziali non valide");
        return session;
    }
}
