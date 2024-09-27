package main_package.controller.handlers;

import main_package.controller.GestioneVociMenu;
import main_package.controller.handlers.aggiungiPrenotazioneHandlers.OrdinaMenuAllaCarta;
import main_package.controller.handlers.aggiungiPrenotazioneHandlers.OrdinaMenuTematico;
import main_package.model.user.Session;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.view.View;
import main_package.controller.VoceMenu;
import main_package.model.*;

import java.time.LocalDate;

import java.util.HashMap;

import java.util.List;

public class AggiungiPrenotazione extends GestioneVociMenu implements Handler{
    private Prenotazione prenotazioneDaAggiungere;
    private List<String> listaPiattiDelGiorno = null;
    private List<String> listaNomiMenuTematici = null;

    private AddettoPrenotazioniRepository addettoPrenotazioniRepository;

    public AggiungiPrenotazione(AddettoPrenotazioniRepository addettoPrenotazioniRepository) {
        this.prenotazioneDaAggiungere = null;
        this.addettoPrenotazioniRepository = addettoPrenotazioniRepository;
    }

    @Override
    public Session execute(Session session, View view) {
        this.aggiungiPrenotazione(session, view);
        return session;
    }

    private void aggiungiPrenotazione(Session session, View view){
        int numeroCopertiCorrente = view.leggiInteroPositivo("Inserire numero di coperti prenotazione >>  ");
        LocalDate dataPrenotazioneCorrente = view.leggiDataConMinimo("Inserire data di prenotazione >>  ", LocalDate.now().plusDays(1));
        this.setPiattiEMenuDisponibili(dataPrenotazioneCorrente);

        if(!(listaPiattiDelGiorno.isEmpty() && listaNomiMenuTematici.isEmpty())){
            if(arePostiDisponibili(numeroCopertiCorrente, dataPrenotazioneCorrente)){
                this.prenotazioneDaAggiungere = new Prenotazione(numeroCopertiCorrente, dataPrenotazioneCorrente, new HashMap<>(), new HashMap<>());
                gestioneSceltaOrdinazioni(session, view, numeroCopertiCorrente);
                addettoPrenotazioniRepository.getPrenotazioniRepository().aggiungiPrenotazione(this.prenotazioneDaAggiungere);
            }else {
                view.print("Impossibile effettuare prenotazione: non ci sono abbastanza posti");
            }
        } else {
            view.print("Impossibile effettuare prenotazione per questa data");
        }
    }

    //supponiamo che tutti debbano per forza ordinare
    private void gestioneSceltaOrdinazioni(Session session, View view, int numeroCopertiCorrente) {
        this.setOption();
        for(int i = 0; i < numeroCopertiCorrente; i++) {
            view.creaMenu("***** Ordinazione " + (i + 1) + " *****");
            view.setVociMenu(this.getVoci());
            int scelta = view.scegliVoceMenu();
            Handler handler = vociMenu.get(scelta).getHandler();
            handler.execute(session, view);
        }
    }

    private boolean arePostiDisponibili(int postiRichiesti, LocalDate dataDiPrenotazione){
        List<Prenotazione> prenotazioniDelGiorno = addettoPrenotazioniRepository.getPrenotazioniRepository().getPrenotazioniPerData(dataDiPrenotazione);
        int numPostiOccupati = 0;
        int numCopertiTotaliDisponibili = addettoPrenotazioniRepository.getParametriRistoranteRepository().getNumeroPostiASedere();

        for (Prenotazione prenotazione : prenotazioniDelGiorno) {
            numPostiOccupati += prenotazione.getNumeroCoperti();
        }

        return (numPostiOccupati + postiRichiesti) <= numCopertiTotaliDisponibili;
    }

    private void setPiattiEMenuDisponibili(LocalDate dataPrenotazioneCorrente) {
        listaPiattiDelGiorno = addettoPrenotazioniRepository.getPiattiRepository().getNomiPiattiDelGiorno(dataPrenotazioneCorrente);
        listaNomiMenuTematici = addettoPrenotazioniRepository.getMenuTematiciRepository().getMenuTematiciDelGiorno(dataPrenotazioneCorrente);
    }

    @Override
    protected void setOption() {
        if(!listaPiattiDelGiorno.isEmpty()) {
            vociMenu.add(new VoceMenu("Menu alla carta", new OrdinaMenuAllaCarta(prenotazioneDaAggiungere, listaPiattiDelGiorno)));
        }
        if (!listaNomiMenuTematici.isEmpty()) {
            vociMenu.add(new VoceMenu("Menu Tematico", new OrdinaMenuTematico(prenotazioneDaAggiungere, listaNomiMenuTematici)));
        }
    }
}
