package main_package.persistence;

import java.time.LocalDate;

public interface IParametriConfigurazioneRepository {
    void setAccessoAbilitato(boolean isAbilitato);
    boolean isAccessoAbilitato();
    /*
    Una volta che rimuovo le prenotazioni segno la data in cui le ho rimosse
     */
    void setOperazioniAutomaticheAvvenute(LocalDate dataDiRimozione);
    boolean areOperazioniAutomaticheAvvenute();
    void setSpesaEffettuata(LocalDate dataDiRimozione);
    boolean isSpesaEffettuata();
    boolean areUtentiDefaultInseriti();
    void setUtentiDefaultInseriti(boolean b);
}
