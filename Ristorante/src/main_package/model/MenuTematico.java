package main_package.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MenuTematico extends Ordinazione {
    private List<String> listaPiatti;
    private double caricoLavMenuTematico;
    private LocalDate dataInizioValidita;
    private LocalDate dataFineValidita;

    public MenuTematico(String nome, List<String> listaPiatti, LocalDate inizioValidita, LocalDate fineValidita, double caricoLavMenuTematico)  {
        this.setNome(nome);
        this.listaPiatti = listaPiatti;
        this.dataInizioValidita = inizioValidita;
        this.dataFineValidita = fineValidita;
        this.caricoLavMenuTematico = caricoLavMenuTematico;
    }

    public List<String> getListaPiatti() {
        return listaPiatti;
    }

    public void setListaPiatti(List<String> listaPiatti) {
        this.listaPiatti = listaPiatti;
    }

    public double getCaricoLavMenuTematico() {
        return caricoLavMenuTematico;
    }

    public void setCaricoLavMenuTematico(double caricoLavMenuTematico) {
        this.caricoLavMenuTematico = caricoLavMenuTematico;
    }

    public LocalDate getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(LocalDate dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public LocalDate getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(LocalDate dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public void aggiungiPiatto(String nomePiatto) {
        this.listaPiatti.add(nomePiatto);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuTematico menu = (MenuTematico) o;
        boolean value1 = Double.compare(caricoLavMenuTematico, menu.caricoLavMenuTematico) == 0;
        boolean value2 = areListaPiattiUguali(this.listaPiatti, menu.listaPiatti);
        boolean value3 = dataInizioValidita.equals(menu.dataInizioValidita);
        boolean value4 = dataFineValidita.equals(menu.dataFineValidita);
        return value1 && value2 && value3 && value4;
    }

    private boolean areListaPiattiUguali(List<String> list1, List<String> list2) {
        if (list1 == null && list2 == null) {
            return true;
        } else if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }
}
