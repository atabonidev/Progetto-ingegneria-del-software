package main_package.utilities;

import main_package.model.Prodotto;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.view.View;

import java.util.HashMap;
import java.util.Map;

public class InitInsiemeProdotti {
    public Map<Prodotto, Double> init(View view, TipoProdotto tipo, UnitaMisura unita, String stringa) {
        Map<Prodotto, Double> insieme = new HashMap<>();
        double consumoProCapite;

        do {
            String nome = view.leggiStringaNonVuota(stringa);
            Prodotto nuovo = new Prodotto(nome, tipo, unita);
            consumoProCapite = view.leggiDoubleConMinimoEscluso("Inserisci consumo pro capite (in " + unita.toString() + ")>>  ", 0);
            insieme.put(nuovo, consumoProCapite);
        } while (view.leggiYesOrNo("Aggiungere altro? >>  "));

        return insieme;
    }
}
