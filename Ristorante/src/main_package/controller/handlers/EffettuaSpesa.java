package main_package.controller.handlers;

import main_package.model.Prodotto;
import main_package.model.TabellaProdottoQuantita;
import main_package.model.user.Session;
import main_package.persistence.IMagazzinoRepository;
import main_package.persistence.IPrenotazioniRepository;
import main_package.persistence.user.MagazziniereRepository;
import main_package.view.View;

import java.time.LocalDate;
import java.util.HashMap;

public class EffettuaSpesa implements Handler{
    private MagazziniereRepository magazziniereRepository;

    public EffettuaSpesa(MagazziniereRepository magazziniereRepository) {
        this.magazziniereRepository = magazziniereRepository;
    }

    @Override
    public Session execute(Session session, View view) {
        this.effettuaSpesa();
        return session;
    }

    private void effettuaSpesa(){
        TabellaProdottoQuantita listaDellaSpesa = generaListaDellaSpesa();
        magazziniereRepository.getMagazzinoRepository().aggiornaMagazzino(listaDellaSpesa);
        magazziniereRepository.getParametriConfigurazioneRepository().setSpesaEffettuata(LocalDate.now());
    }

    private TabellaProdottoQuantita generaListaDellaSpesa(){
        IMagazzinoRepository magazzino = magazziniereRepository.getMagazzinoRepository();
        IPrenotazioniRepository prenotazioni = magazziniereRepository.getPrenotazioniRepository();

        TabellaProdottoQuantita listaProdottiNecessariPrenotazioni = new TabellaProdottoQuantita(new HashMap<>());

        listaProdottiNecessariPrenotazioni.inserisciProdotti(prenotazioni.getIngredientiPrenotazioniPerData(LocalDate.now()));
        listaProdottiNecessariPrenotazioni.inserisciProdotti(prenotazioni.getBevandeServitePrenotazioniPerData(LocalDate.now()));
        listaProdottiNecessariPrenotazioni.inserisciProdotti(prenotazioni.getGeneriExtraServitiPrenotazioniPerData(LocalDate.now()));

        //incremento 10%
        for (Prodotto prodotto: listaProdottiNecessariPrenotazioni.getTabella().keySet()) {
            Double incremento = listaProdottiNecessariPrenotazioni.get(prodotto) * 0.1;
            listaProdottiNecessariPrenotazioni.inserisciProdotto(prodotto, incremento);
        }

        TabellaProdottoQuantita scorteMagazzino = magazzino.getMagazzino();
        TabellaProdottoQuantita listaDellaSpesa = TabellaProdottoQuantita.differenzaTraTabelle(listaProdottiNecessariPrenotazioni, scorteMagazzino);

        return listaDellaSpesa;
    }
}
