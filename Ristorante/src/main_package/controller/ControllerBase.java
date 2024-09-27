package main_package.controller;

import main_package.controller.handlers.Handler;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.view.View;

/**
 * Classe astratta che rappresenta il main_package.controller di base. Viene applicato il pattern Template Method per gestire le parti
 * comuni a tutti i main_package.controller e per ammettere possibili parti variabili in base al main_package.controller specifico.
 */
public abstract class ControllerBase extends GestioneVociMenu {
    final protected View view;
    protected Session session;

    public ControllerBase(View view) {
        super();
        this.view = view;
        this.session = new Session(null, State.UNLOGGED);
    }

    /**
     * Metodo nel quale viene gestito il flusso delle azioni dell'applicazione.
     * Qui viene applicato il pattern Template Method
     */
    public final void run(String stringaUtente, String accessoNonAbilitato){
        if(isAccessoAbilitato()){
            corpoFissoMetodoRun(stringaUtente);
        } else {
            view.print(accessoNonAbilitato);
        }
        conclusioneVariabileMetodoRun();
    }

    private void corpoFissoMetodoRun(String stringaUtente) {
        String titolo;
        boolean exit = false;
        do {
            impostazioneMenu();
            titolo = isUtenteLoggato(stringaUtente);
            view.setTitoloMenu(titolo);
            exit = gestioneSceltaUtente();
        } while (!exit);
    }

    private boolean gestioneSceltaUtente() {
        int scelta = view.scegliVoceMenu();
        if (scelta != 0) {
            Handler handler = vociMenu.get(scelta).getHandler();
            session = handler.execute(session, view);
            return false;
        } else {
            view.print("Programma Terminato");
            return true;
        }
    }

    private String isUtenteLoggato(String stringaUtente) {
        if (session.getState().equals(State.LOGGED)) {
            return stringaUtente + session.getUtente().getUsername() + " loggato";
        } else {
            return "Programma " + stringaUtente;
        }
    }

    protected void impostazioneMenu() {
        view.creaMenu("");
        this.setOption();
        view.setVociMenu(this.getVoci());

    }

    protected abstract boolean isAccessoAbilitato();

    protected abstract void conclusioneVariabileMetodoRun();
}
