package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.MenuTematico;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.util.List;

public class ShowMenuTematico implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowMenuTematico(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        List<MenuTematico> listaMenuTematici = gestoreRepository.getMenuTematiciRepository().getMenuTematici();
        view.printMenuTematici(listaMenuTematici);
        return session;
    }
}
