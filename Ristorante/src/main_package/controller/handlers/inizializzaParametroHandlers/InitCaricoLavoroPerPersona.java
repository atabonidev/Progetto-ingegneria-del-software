package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

public class InitCaricoLavoroPerPersona implements Handler {
    private GestoreRepository gestoreRepository;

    public InitCaricoLavoroPerPersona(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        int carico = view.leggiInteroPositivo("Inserisci nuovo carico di lavoro per persona >>  ");
        this.gestoreRepository.getParametriRistoranteRepository().setCaricoLavoroPerPersona(carico);
        return session;
    }
}
