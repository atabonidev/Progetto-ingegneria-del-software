package main_package.model;

import java.time.LocalDate;
import java.util.Objects;

public class Piatto extends Ordinazione {
    private String nomeRicettaAssociata;
    private double caricoLavoroPiatto;
    private LocalDate inizioValidita;
    private LocalDate fineValidita;

    public Piatto(String nome, String nomeRicettaAssociata, double caricoLavoroPiatto, LocalDate inizioValidita, LocalDate fineValidita) {
        this.setNome(nome);
        this.nomeRicettaAssociata = nomeRicettaAssociata;
        this.caricoLavoroPiatto = caricoLavoroPiatto;
        this.inizioValidita = inizioValidita;
        this.fineValidita = fineValidita;
    }

    public String getRicetta() {
        return nomeRicettaAssociata;
    }

    public void setNomeRicettaAssociata(String nomeRicettaAssociata) {
        this.nomeRicettaAssociata = nomeRicettaAssociata;
    }

    public double getCaricoLavoroPiatto() {
        return caricoLavoroPiatto;
    }

    public void setCaricoLavoroPiatto(double caricoLavoroPiatto) {
        this.caricoLavoroPiatto = caricoLavoroPiatto;
    }

    public LocalDate getInizioValidita() {
        return inizioValidita;
    }

    public void setInizioValidita(LocalDate inizioValidita) {
        this.inizioValidita = inizioValidita;
    }

    public LocalDate getFineValidita() {
        return fineValidita;
    }

    public void setFineValidita(LocalDate fineValidita) {
        this.fineValidita = fineValidita;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piatto piatto = (Piatto) o;
        return Double.compare(piatto.getCaricoLavoroPiatto(), getCaricoLavoroPiatto()) == 0 && nomeRicettaAssociata.equals(piatto.nomeRicettaAssociata) && getInizioValidita().equals(piatto.getInizioValidita()) && getFineValidita().equals(piatto.getFineValidita());
    }
}