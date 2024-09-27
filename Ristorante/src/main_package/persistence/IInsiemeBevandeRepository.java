package main_package.persistence;

import main_package.model.Prodotto;

import java.util.Map;

public interface IInsiemeBevandeRepository {
    void setInsiemeBevande(Map<Prodotto, Double> insiemeBevande);
    //ritorna solo la lista delle bevande SENZA il consumo pro capite associato
    Map<Prodotto, Double> getInsiemeBevande();

    void removeBevanda(Prodotto bevanda);

    boolean isListaBevandeVuota();
}
