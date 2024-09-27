package main_package.persistence;

import main_package.model.Prenotazione;
import main_package.model.TabellaProdottoQuantita;

import java.time.LocalDate;
import java.util.List;

public interface IPrenotazioniRepository {
    void aggiungiPrenotazione(Prenotazione prenotazione);
    //prende di riferimento la data corrente
    void rimuoviPrenotazioniScadute();
    List<Prenotazione> getPrenotazioniPerData(LocalDate data);
    TabellaProdottoQuantita getIngredientiPrenotazioniPerData(LocalDate dataPrenotazione);

    TabellaProdottoQuantita getBevandeServitePrenotazioniPerData(LocalDate now);

    TabellaProdottoQuantita getGeneriExtraServitiPrenotazioniPerData(LocalDate now);

    void clearTable();
}
