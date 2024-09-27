package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.util.Map;

public class ShowCorrispondenzaPiattiRicette implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowCorrispondenzaPiattiRicette(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        Map<String, String> corrispondenzePiattiRicetta = gestoreRepository.getPiattiRepository().getCorrispondenzaPiattiRicette();
        view.printCorrispondenzaPiattoRicetta(corrispondenzePiattiRicetta);
        return session;
    }
}
