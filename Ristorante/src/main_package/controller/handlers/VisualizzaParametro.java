package main_package.controller.handlers;

import main_package.controller.GestioneVociMenu;
import main_package.controller.handlers.visualizzaParametroHandlers.*;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;
import main_package.controller.VoceMenu;

import java.util.ArrayList;
import java.util.List;

/*
Classe che si occupa della gestione della visualizzazione dei singoli parametri ai quali è interessato il Gestore:
    - carico di lavoro per persona,
    - numero di posti a sedere disponibili nel ristorante,
    - insieme delle bevande,
    - insieme dei generi (alimentari) extra,
    - consumo pro-capite di bevande,
    - consumo pro-capite di generi (alimentari) extra,
    - corrispondenze piatto-ricetta,
    - denominazione e periodo di validità di ciascun piatto

   Inoltre, deve permettere di creare e visualizzare le ricette e i menu tematici.
 */
public class VisualizzaParametro extends GestioneVociMenu implements Handler{
    private GestoreRepository gestoreRepository;

    public VisualizzaParametro(GestoreRepository gestoreRepository){
        super();
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        this.visualizzaParametro(session, view);
        return session;
    }

    private void visualizzaParametro(Session session, View view){
        boolean exitFromVisualizzaParametro = false;
        view.creaMenu("VISUALIZZAZIONE PARAMETRO");
        this.setOption();
        view.setVociMenu(this.getVoci());
        do {
            int scelta = view.scegliVoceMenu();
            if (scelta != 0) {
                Handler handler = vociMenu.get(scelta).getHandler();
                handler.execute(session, view);
            } else {
                exitFromVisualizzaParametro = true;
            }
        } while (!exitFromVisualizzaParametro);
    }

    @Override
    protected void setOption() {
        vociMenu.add(new VoceMenu("Esci", null));
        vociMenu.add(new VoceMenu("Carico lavoro per persona", new ShowCaricoLavoroPerPersona(gestoreRepository)));
        vociMenu.add(new VoceMenu("Numero posti a sedere", new ShowNumeroPostiASedere(gestoreRepository)));
        vociMenu.add(new VoceMenu("Insieme delle bevande", new ShowInsiemeBevande(gestoreRepository)));
        vociMenu.add(new VoceMenu("Insieme dei generi extra", new ShowInsiemeGeneriExtra(gestoreRepository)));
        vociMenu.add(new VoceMenu("Corrispondenza piatti - ricette", new ShowCorrispondenzaPiattiRicette(gestoreRepository)));
        vociMenu.add(new VoceMenu("Ricette", new ShowRicette(gestoreRepository)));
        vociMenu.add(new VoceMenu("Menu tematici", new ShowMenuTematico(gestoreRepository)));
    }
}
