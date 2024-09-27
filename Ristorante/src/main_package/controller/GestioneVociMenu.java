package main_package.controller;

import java.util.ArrayList;
import java.util.List;

public abstract class GestioneVociMenu {
    final protected List<VoceMenu> vociMenu;

    protected GestioneVociMenu() {
        this.vociMenu = new ArrayList<>();
    }

    protected ArrayList<String> getVoci() {
        ArrayList<String> voci = new ArrayList<>();
        for (VoceMenu opt : vociMenu) {
            voci.add(opt.getNomeVoce());
        }
        return voci;
    }

    protected abstract void setOption();

}
