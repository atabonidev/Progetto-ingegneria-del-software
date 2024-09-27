package main_package.persistence;

import main_package.model.Piatto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPiattiRepository {
    List<String> getNomiPiattiDelGiorno(LocalDate data);  //usato per l'inizializzazione del menu normale
    void insertPiatto(Piatto piatto);
    Map<String, String> getCorrispondenzaPiattiRicette();
    //ritorna la lista di piatti il cui periodo di validità è compreso o uguale a quello passato come parametro
    List<String> getNomiPiattiPerPeriodoDiValidita(LocalDate inizioValiditaMenuTematico, LocalDate fineValiditaMenuTematico);
    Piatto getPiattoFromName(String nomePiatto);
    boolean isListaPiattiVuota();

    double getCaricoDiLavoroInsiemePiatti(List<String> listaPiatti);
}
