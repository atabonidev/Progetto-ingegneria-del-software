package main_package.controller;

import main_package.controller.handlers.*;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.view.View;
import main_package.model.user.State;

public class ControllerAddettoPrenotazioni extends ControllerBase{
    private AddettoPrenotazioniRepository addettoPrenotazioniRepository;

    public ControllerAddettoPrenotazioni(AddettoPrenotazioniRepository addettoPrenotazioniRepository, View view) {
        super(view);
        this.addettoPrenotazioniRepository = addettoPrenotazioniRepository;
    }

    @Override
    protected boolean isAccessoAbilitato() {
        return addettoPrenotazioniRepository.getParametriConfigurazioneRepository().isAccessoAbilitato();
    }

    @Override
    protected void conclusioneVariabileMetodoRun() {
        //placeholder method: questa classe non prevede una parte finale del metodo diversa dall'implementazione fissa
    }

    @Override
    protected void setOption() {
        vociMenu.clear();
        vociMenu.add(new VoceMenu("Esci", null));
        if (session.getState().equals(State.UNLOGGED)) {
            vociMenu.add(new VoceMenu("Login", new Login(addettoPrenotazioniRepository.getUtentiRepository(), "addetto_prenotazioni")));
        } else if (session.getState().equals(State.LOGGED)) {
            vociMenu.add(new VoceMenu("Logout", new Logout()));
            vociMenu.add(new VoceMenu("Aggiungi prenotazione", new AggiungiPrenotazione(addettoPrenotazioniRepository)));
        }
    }

}
