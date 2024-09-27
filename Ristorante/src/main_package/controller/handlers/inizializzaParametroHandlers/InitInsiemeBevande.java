package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Prodotto;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.utilities.InitInsiemeProdotti;
import main_package.utilities.OperazioniSuMappe;
import main_package.view.View;

import java.util.List;
import java.util.Map;

public class InitInsiemeBevande implements Handler {
    private GestoreRepository gestoreRepository;

    public InitInsiemeBevande(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        InitInsiemeProdotti initInsiemeProdotti = new InitInsiemeProdotti();
        Map<Prodotto, Double> insiemeBevandePresenti = gestoreRepository.getBevandeRepository().getInsiemeBevande();
        Map<Prodotto, Double> insiemeBevandeInput = initInsiemeProdotti.init(view, TipoProdotto.BEVANDA_SERVITA, UnitaMisura.LITRI, "inserisci nome bevanda >> ");
        List<String> bevandeGiaPresenti = OperazioniSuMappe.calcolaIntersezione(insiemeBevandeInput, insiemeBevandePresenti);
        Map<Prodotto, Double> bevandeDaInserire = OperazioniSuMappe.calcolaDifferenza(insiemeBevandeInput, insiemeBevandePresenti);
        gestoreRepository.getBevandeRepository().setInsiemeBevande(bevandeDaInserire);
        if (!bevandeGiaPresenti.isEmpty()) {
            view.print("Le seguenti bevande: " + bevandeGiaPresenti + " sono gia state inizializzati!");
        }
        return session;
    }
}
