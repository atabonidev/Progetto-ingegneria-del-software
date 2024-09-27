package main_package.controller.handlers;
import main_package.model.user.Session;
import main_package.view.View;

/*
Interfaccia che rappresenta la singola azione che può essere fatta da un utente dell'applicazione.
Costituisce un piccolo main_package.controller MVC.
 */

public interface Handler {
    Session execute(Session session, View view);
}
