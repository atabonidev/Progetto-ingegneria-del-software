package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Ricetta;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.util.List;

public class ShowRicette implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowRicette(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        List<Ricetta> listaRicette = gestoreRepository.getRicetteRepository().getRicette();
        view.printRicette(listaRicette);
        return session;
    }
}
