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

public class InitInsiemeGeneriExtra implements Handler {
    private GestoreRepository gestoreRepository;

    public InitInsiemeGeneriExtra(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        InitInsiemeProdotti initInsiemeProdotti = new InitInsiemeProdotti();
        Map<Prodotto, Double> insiemeGeneriExtraPresenti = gestoreRepository.getBevandeRepository().getInsiemeBevande();
        Map<Prodotto, Double> insiemeGeneriExtraInput = initInsiemeProdotti.init(view, TipoProdotto.GENERE_EXTRA, UnitaMisura.ETTOGRAMMI, "inserisci nome genere extra >> ");
        List<String> generiExtraGiaPresenti = OperazioniSuMappe.calcolaIntersezione(insiemeGeneriExtraInput, insiemeGeneriExtraPresenti);
        Map<Prodotto, Double> generiExtraDaInserire = OperazioniSuMappe.calcolaDifferenza(insiemeGeneriExtraInput, insiemeGeneriExtraPresenti);
        gestoreRepository.getGeneriExtraRepository().setInsiemeGeneriExtra(generiExtraDaInserire);
        if (!generiExtraGiaPresenti.isEmpty()) {
            view.print("I seguenti generi extra: " + generiExtraGiaPresenti + " sono gia stati inizializzati!");
        }
        return session;
    }
}
