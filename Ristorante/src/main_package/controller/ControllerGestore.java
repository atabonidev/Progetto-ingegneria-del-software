package main_package.controller;

/*
    L’applicazione deve consentire di inizializzare e visualizzare i valori dei seguenti parametri:
        - carico di lavoro per persona,
        - numero di posti a sedere disponibili nel ristorante,
        - insieme delle bevande,
        - insieme dei generi (alimentari) extra,
        - consumo pro-capite di bevande,
        - consumo pro-capite di generi (alimentari) extra,
        - corrispondenze piatto-ricetta,
        - denominazione e periodo di validità di ciascun piatto.
    Inoltre, deve permettere di creare e visualizzare le ricette e i menu tematici.
     */

import main_package.controller.handlers.*;
import main_package.model.user.State;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

public class ControllerGestore extends ControllerBase{
    private GestoreRepository gestoreRepository;

    public ControllerGestore(View view, GestoreRepository gestoreRepository) {
        super(view);
        this.gestoreRepository = gestoreRepository;
    }

    private void abilitazioneAccesso() {
        boolean isAccessoAbilitato = gestoreRepository.getParametriDiConfig().isAccessoAbilitato();

        if(!isAccessoAbilitato) {
            int numeroPostiASedere = gestoreRepository.getParametriRistoranteRepository().getNumeroPostiASedere();
            int caricoLavoroPersona = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
            boolean isListaPiattiVuota = gestoreRepository.getPiattiRepository().isListaPiattiVuota();
            boolean isListaBevandeVuota = gestoreRepository.getBevandeRepository().isListaBevandeVuota();
            boolean isListaGeneriExtraVuota = gestoreRepository.getGeneriExtraRepository().isListaGeneriExtraVuota();

            if(numeroPostiASedere > 0 && caricoLavoroPersona > 0 && !isListaPiattiVuota && !isListaBevandeVuota && !isListaGeneriExtraVuota) {
                gestoreRepository.getParametriDiConfig().setAccessoAbilitato(true);
            }
        }
    }

    @Override
    protected boolean isAccessoAbilitato() {
        return true;
    }

    @Override
    protected void conclusioneVariabileMetodoRun() {
        this.abilitazioneAccesso();
    }

    @Override
    protected void setOption() {
        vociMenu.clear();
        vociMenu.add(new VoceMenu("Esci", null));
        if (session.getState().equals(State.UNLOGGED)) {
            vociMenu.add(new VoceMenu("Login", new Login(gestoreRepository.getUtentiRepository(), "gestore")));
        } else if (session.getState().equals(State.LOGGED)) {
            vociMenu.add(new VoceMenu("Logout", new Logout()));
            vociMenu.add(new VoceMenu("Inizializza Parametri", new InizializzaParametro(gestoreRepository)));
            vociMenu.add(new VoceMenu("Visualizza Parametri", new VisualizzaParametro(gestoreRepository)));
        }
    }
}
