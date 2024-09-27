package main_package.persistence;

import main_package.model.MenuTematico;

import java.time.LocalDate;
import java.util.List;

public interface IMenuTematiciRepository {
    void insertMenuTematico(MenuTematico menuTematico);
    List<MenuTematico> getMenuTematici();
    List<String> getMenuTematiciDelGiorno(LocalDate dataDelGiorno);
    MenuTematico getMenuTematicoFromNome(String nomeMenuTematicoRicerca);

    void deleteMenuTematicoFromNome(String NomeMenuTematico);
}
