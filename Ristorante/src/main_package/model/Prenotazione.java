package main_package.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class Prenotazione {
    private UUID IDprenotazione;
    private int numeroCoperti;
    private LocalDate dataPrenotazione;
    private Map<String, Integer> listaPiattiOrdinati;
    private Map<String, Integer> listaMenuTematiciOrdinati;
    public Prenotazione(String idPrenotazione, int numeroCoperti, LocalDate dataPrenotazione, Map<String, Integer> listaPiattiOrdinati, Map<String, Integer> listaMenuTematiciOrdinati) {
        this.IDprenotazione = UUID.fromString(idPrenotazione);
        this.numeroCoperti = numeroCoperti;
        this.dataPrenotazione = dataPrenotazione;
        this.listaPiattiOrdinati = listaPiattiOrdinati;
        this.listaMenuTematiciOrdinati = listaMenuTematiciOrdinati;
    }

    public Prenotazione(int numeroCoperti, LocalDate dataPrenotazione, Map<String, Integer> listaPiattiOrdinati, Map<String, Integer> listaMenuTematiciOrdinati) {
        this.IDprenotazione = UUID.randomUUID();
        this.numeroCoperti = numeroCoperti;
        this.dataPrenotazione = dataPrenotazione;
        this.listaPiattiOrdinati = listaPiattiOrdinati;
        this.listaMenuTematiciOrdinati = listaMenuTematiciOrdinati;
    }

    public void aggiungiMenuTematicoOrdinato(String nomeMenuTematicoOrdinato) {
       if(listaMenuTematiciOrdinati.containsKey(nomeMenuTematicoOrdinato)) {
           listaMenuTematiciOrdinati.put(nomeMenuTematicoOrdinato, listaMenuTematiciOrdinati.get(nomeMenuTematicoOrdinato) + 1);
       }else {
           listaMenuTematiciOrdinati.put(nomeMenuTematicoOrdinato, 1);
       }
    }

    public void aggiungiPiattoOrdinato(String nomePiattoOrdinato) {
        if(listaPiattiOrdinati.containsKey(nomePiattoOrdinato)) {
            listaPiattiOrdinati.put(nomePiattoOrdinato, listaPiattiOrdinati.get(nomePiattoOrdinato) + 1);
        }else {
            listaPiattiOrdinati.put(nomePiattoOrdinato, 1);
        }
    }

    public String getIDprenotazione() {
        return IDprenotazione.toString();
    }

    public void setIDprenotazione(UUID IDprenotazione) {
        this.IDprenotazione = IDprenotazione;
    }

    public void setNumeroCoperti(int numeroCoperti) {
        this.numeroCoperti = numeroCoperti;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public Map<String, Integer> getListaPiattiOrdinati() {
        return listaPiattiOrdinati;
    }

    public void setListaPiattiOrdinati(Map<String, Integer> listaPiattiOrdinati) {
        this.listaPiattiOrdinati = listaPiattiOrdinati;
    }

    public Map<String, Integer> getListaMenuTematiciOrdinati() {
        return listaMenuTematiciOrdinati;
    }

    public void setListaMenuTematiciOrdinati(Map<String, Integer> listaMenuTematiciOrdinati) {
        this.listaMenuTematiciOrdinati = listaMenuTematiciOrdinati;
    }

    public LocalDate getDataPrenotazione(){
        return dataPrenotazione;
    }

    public int getNumeroCoperti() {
        return numeroCoperti;
    }
}
