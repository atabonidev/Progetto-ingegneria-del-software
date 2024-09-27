package main_package.persistence;

import main_package.model.Prodotto;

import java.util.Map;

public interface IGeneriExtraRepository {
    void setInsiemeGeneriExtra(Map<Prodotto, Double> insiemeGeneriExtra);
    //ritorna solo la lista dei generi extra SENZA il consumo pro capite associato
    Map<Prodotto, Double> getInsiemeGeneriExtra();

    void removeGenereExtra(Prodotto genereExtra);

    boolean isListaGeneriExtraVuota();
}
