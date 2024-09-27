package main_package.controller.handlers.visualizzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Prodotto;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.util.Map;

public class ShowInsiemeBevande implements Handler {
    private GestoreRepository gestoreRepository;

    public ShowInsiemeBevande(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        Map<Prodotto, Double> insiemeBevande = gestoreRepository.getBevandeRepository().getInsiemeBevande();
        view.printTabellaProdottoValore(insiemeBevande, "TABELLA BEVANDA -> CONSUMO PRO CAPITE");
        return session;
    }
}
