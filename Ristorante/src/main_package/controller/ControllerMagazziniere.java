package main_package.controller;

import main_package.controller.handlers.*;
import main_package.model.user.State;
import main_package.persistence.IMagazzinoRepository;
import main_package.persistence.IPrenotazioniRepository;
import main_package.persistence.user.MagazziniereRepository;
import main_package.view.View;
import main_package.model.*;

import java.time.LocalDate;
import java.util.*;

public class ControllerMagazziniere extends ControllerBase{
    private MagazziniereRepository magazziniereRepository;

    public ControllerMagazziniere(View view, MagazziniereRepository magazziniereRepository){
        super(view);
        this.magazziniereRepository = magazziniereRepository;
    }

    @Override
    protected boolean isAccessoAbilitato() {
        return magazziniereRepository.getParametriConfigurazioneRepository().isAccessoAbilitato();
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
            vociMenu.add(new VoceMenu("Login", new Login(magazziniereRepository.getUtentiRepository(),"magazziniere")));
        } else if (session.getState().equals(State.LOGGED)) {
            vociMenu.add(new VoceMenu("Logout", new Logout()));
            if (!(magazziniereRepository.getParametriConfigurazioneRepository().isSpesaEffettuata())){
                vociMenu.add(new VoceMenu("Effettua spesa", new EffettuaSpesa(magazziniereRepository)));
            }
        }
    }

    @Override
    protected void impostazioneMenu() {
        super.impostazioneMenu();
        if (session.getState().equals(State.LOGGED)) {
            operazioniAutomaticheDelGiorno();
        }
    }

    /**
     * Genera un numero casuale compreso tra 0.0 e 0.1 che rappresenta la percentuale di prodotti
     * supplementare da eliminare a fine giornata a causa di scadenze, qualità prodotto scarsa, etc...
     */
    private Double getPercentualeDaRimuovere(){
        Random random = new Random();
        int valore = random.nextInt(11);
        return valore /100.0;
    }

    /**
     * Metodo con il quale vengono eliminati dal magazzino gli ingredienti utilizzati il giorno precedente (rispetto al
     * giorno in cui è in esecuzione il programma) dal ristorante.
     */
    private void rimuoviIngredientiUsati(){
        IMagazzinoRepository magazzino = magazziniereRepository.getMagazzinoRepository();
        IPrenotazioniRepository prenotazioni = magazziniereRepository.getPrenotazioniRepository();

        LocalDate giornoPrecedente = LocalDate.now().minusDays(1);

        TabellaProdottoQuantita listaProdottiDaRimuovere = new TabellaProdottoQuantita(new HashMap<>());

        listaProdottiDaRimuovere.inserisciProdotti(prenotazioni.getIngredientiPrenotazioniPerData(giornoPrecedente));
        listaProdottiDaRimuovere.inserisciProdotti(prenotazioni.getBevandeServitePrenotazioniPerData(giornoPrecedente));
        listaProdottiDaRimuovere.inserisciProdotti(prenotazioni.getGeneriExtraServitiPrenotazioniPerData(giornoPrecedente));

        for (Prodotto prodotto: listaProdottiDaRimuovere.getTabella().keySet()) {
            double incremento = listaProdottiDaRimuovere.get(prodotto) * getPercentualeDaRimuovere();
            double quantitaDaRimuovere = listaProdottiDaRimuovere.get(prodotto) + incremento;
            listaProdottiDaRimuovere.sostituisciProdotto(prodotto, -quantitaDaRimuovere);
        }

        magazzino.aggiornaMagazzino(listaProdottiDaRimuovere);
    }

    private void operazioniAutomaticheDelGiorno(){
        if(!magazziniereRepository.getParametriConfigurazioneRepository().areOperazioniAutomaticheAvvenute()){
            rimuoviIngredientiUsati();
            magazziniereRepository.getPrenotazioniRepository().rimuoviPrenotazioniScadute();
            magazziniereRepository.getParametriConfigurazioneRepository().setOperazioniAutomaticheAvvenute(LocalDate.now());
        }
    }
}
