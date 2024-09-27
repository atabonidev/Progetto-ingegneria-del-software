package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

public class InitNumeroPostiASedere implements Handler {
    private GestoreRepository gestoreRepository;

    public InitNumeroPostiASedere(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }

    @Override
    public Session execute(Session session, View view) {
        int numPosti = view.leggiInteroPositivo("Inserisci il numero di posti a sedere >>  ");
        gestoreRepository.getParametriRistoranteRepository().setNumeroPostiASedere(numPosti);
        return session;
    }
}
