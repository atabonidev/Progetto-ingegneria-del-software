package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

public class ShowNumeroPostiASedere implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowNumeroPostiASedere(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    public Session execute(Session session, View view) {
        int numeroPostiASedere = gestoreRepository.getParametriRistoranteRepository().getNumeroPostiASedere();
        view.print(String.valueOf(numeroPostiASedere));
        return session;
    }
}
