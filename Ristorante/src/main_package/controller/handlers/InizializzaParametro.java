package main_package.controller.handlers;

import main_package.controller.GestioneVociMenu;
import main_package.controller.handlers.inizializzaParametroHandlers.*;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;
import main_package.controller.VoceMenu;

/*
Classe che si occupa della gestione dell'inizializzazione dei singoli parametri ai quali è interessato il Gestore:
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

public class InizializzaParametro extends GestioneVociMenu implements Handler{
    private GestoreRepository gestoreRepository;

    public InizializzaParametro(GestoreRepository gestoreRepository){
        super();
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        this.inizializzaParametro(session, view);
        return session;
    }

    private void inizializzaParametro(Session session, View view){
        boolean exitFromInizializzaParametro = false;
        inizializzaMenu(session, view);
        do {
            exitFromInizializzaParametro = gestioneSceltaUtente(session, view);
        } while (!exitFromInizializzaParametro);
    }

    private boolean gestioneSceltaUtente(Session session, View view) {
        int scelta = view.scegliVoceMenu();
        if (scelta != 0) {
            Handler handler = vociMenu.get(scelta).getHandler();
            handler.execute(session, view);
            return false;
        } else {
            return true;
        }
    }

    private void inizializzaMenu(Session session, View view) {
        view.creaMenu("INIZIALIZZAZIONE PARAMETRO");
        this.setOption();
        view.setVociMenu(this.getVoci());
    }

    @Override
    protected void setOption(){
        vociMenu.add(new VoceMenu("Esci", null));
        vociMenu.add(new VoceMenu("Carico lavoro per persona", new InitCaricoLavoroPerPersona(gestoreRepository)));
        vociMenu.add(new VoceMenu("Numero posti a sedere", new InitNumeroPostiASedere(gestoreRepository)));
        vociMenu.add(new VoceMenu("Insieme delle bevande", new InitInsiemeBevande(gestoreRepository)));
        vociMenu.add(new VoceMenu("Insieme dei generi extra", new InitInsiemeGeneriExtra(gestoreRepository)));
        vociMenu.add(new VoceMenu("Creazione ricetta", new InizializzaRicetta(gestoreRepository)));
        vociMenu.add(new VoceMenu("Creazione piatto", new InizializzaPiatto(gestoreRepository)));
        vociMenu.add(new VoceMenu("Creazione menu tematico", new InizializzaMenuTematico(gestoreRepository)));
    }
}
