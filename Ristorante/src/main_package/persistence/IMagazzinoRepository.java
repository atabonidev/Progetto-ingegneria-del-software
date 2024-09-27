package main_package.persistence;

import main_package.model.TabellaProdottoQuantita;

public interface IMagazzinoRepository {
    /*
   Cicla tutti i prodotti della mappa e per ognuno di questi incrementa (o decrementa) la quantità presente nel sistema di memorizzazione
    */
    void aggiornaMagazzino(TabellaProdottoQuantita prodottiDaAggiungere);
    TabellaProdottoQuantita getMagazzino();

    void clearTable();
}
