package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

public class ShowCaricoLavoroPerPersona implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowCaricoLavoroPerPersona(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        int caricoLavPerPersona = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
        view.print(String.valueOf(caricoLavPerPersona));
        return session;
    }
}
