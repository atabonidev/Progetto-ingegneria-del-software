package main_package.persistence;

import main_package.model.Ricetta;

import java.util.List;

public interface IRicetteRepository {
    /*
    - inserisco ricetta in tabella ricetta
    - inserisco tutti gli ingredienti nella tabella ingredienti mettendo il valore -1 come consumo pro capite e come tipo
    "ingrediente" e unità di misura "kg"
     */
    void insertRicetta(Ricetta ricetta);
    //ritorna la lista intera di tutte le ricette
    List<Ricetta> getRicette();
    //se c'è restituisce l'istanza, altrimenti null
    Ricetta getRicettaFromName(String nome);

    boolean isRicettaPresente(String nome);

    double getCaricoDiLavoroDellaRicetta(String nomeRicetta);

    void deleteRicetta(String nomeRicetta);
}
