package main_package.persistence.impl;

import main_package.model.MenuTematico;
import main_package.persistence.IMenuTematiciRepository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class MenuTematiciRepository implements IMenuTematiciRepository {

    private Connection connection;

    public MenuTematiciRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertMenuTematico(MenuTematico menuTematico) {
        String query1 = "INSERT INTO MenuTematico (NomeMenuTematico, DataInizioValidita, DataFineValidita, CaricoLavoroMenuTematico) " +
            "VALUES (?, ?, ?, ?)";

        StringBuilder query2 = new StringBuilder("INSERT INTO MenuTematicoPiatto(MenuTematico, Piatto) VALUES ");
        query2.append("(?, ?),".repeat(Math.max(0, menuTematico.getListaPiatti().size() - 1)));
        query2.append("(?, ?)");

        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            //Settiamo dati del menuTematico
            statement.setString(1, menuTematico.getNome());
            statement.setDate(2, Date.valueOf(menuTematico.getDataInizioValidita()));
            statement.setDate(3, Date.valueOf(menuTematico.getDataFineValidita()));
            statement.setDouble(4, menuTematico.getCaricoLavMenuTematico());
            statement.executeUpdate();

            int index = 1;
            //Settiamo righe corrispondenza menu-piatto
            statement = connection.prepareStatement(query2.toString());
            for (String p : menuTematico.getListaPiatti()) {
                statement.setString(index++, menuTematico.getNome());
                statement.setString(index++, p);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuTematico> getMenuTematici() {
        List<MenuTematico> menuTematici = new ArrayList<>();
        String sql = "SELECT MT.NomeMenuTematico, MT.DataInizioValidita, MT.DataFineValidita, MT.CaricoLavoroMenuTematico, P.NomePiatto FROM MenuTematico MT " +
                "INNER JOIN MenuTematicoPiatto MTP ON MT.NomeMenuTematico = MTP.MenuTematico " +
                "INNER JOIN Piatto P ON MTP.Piatto = P.NomePiatto";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String nomeMenuTematico = resultSet.getString("NomeMenuTematico");
                LocalDate dataInizioValidita = resultSet.getDate("DataInizioValidita").toLocalDate();
                LocalDate dataFineValidita = resultSet.getDate("DataFineValidita").toLocalDate();
                String nomePiatto = resultSet.getString("NomePiatto");
                double caricoLavoroMenuTematico = resultSet.getDouble("CaricoLavoroMenuTematico");

                MenuTematico menuTematico = menuTematici.stream()
                        .filter(m -> m.getNome().equals(nomeMenuTematico))
                        .findFirst()
                        .orElse(null);

                if (menuTematico == null) {
                    menuTematico = new MenuTematico(nomeMenuTematico, new ArrayList<>(Collections.singleton(nomePiatto)), dataInizioValidita, dataFineValidita, caricoLavoroMenuTematico);
                    menuTematici.add(menuTematico);
                } else {
                    menuTematico.aggiungiPiatto(nomePiatto);
                }
            }

            return menuTematici;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MenuTematico getMenuTematicoFromNome(String nomeMenuTematicoRicerca) {
        MenuTematico menuTematico = null;

        String sql = "SELECT MT.NomeMenuTematico, MT.DataInizioValidita, MT.DataFineValidita, MT.CaricoLavoroMenuTematico, P.NomePiatto " +
                "FROM MenuTematico MT " +
                "INNER JOIN MenuTematicoPiatto MTP ON MT.NomeMenuTematico = MTP.MenuTematico " +
                "INNER JOIN Piatto P ON MTP.Piatto = P.NomePiatto " +
                "WHERE MT.NomeMenuTematico = ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomeMenuTematicoRicerca);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeMenuTematico = resultSet.getString("NomeMenuTematico");
                LocalDate dataInizioValidita = resultSet.getDate("DataInizioValidita").toLocalDate();
                LocalDate dataFineValidita = resultSet.getDate("DataFineValidita").toLocalDate();
                String nomePiatto = resultSet.getString("NomePiatto");
                double caricoLavoroMenuTematico = resultSet.getDouble("CaricoLavoroMenuTematico");

                if (menuTematico == null) {
                    menuTematico = new MenuTematico(nomeMenuTematico, new ArrayList<>(Collections.singleton(nomePiatto)), dataInizioValidita, dataFineValidita, caricoLavoroMenuTematico);
                } else {
                    menuTematico.aggiungiPiatto(nomePiatto);
                }
            }

            return menuTematico;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getMenuTematiciDelGiorno(LocalDate dataDelGiorno) {
        List<String> menuTematici = new ArrayList<>();

        String sql = "SELECT NomeMenuTematico FROM MenuTematico WHERE DataInizioValidita <= ? AND DataFineValidita >= ?";

        try {
            Date dataConvertita = Date.valueOf(dataDelGiorno);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, dataConvertita);
            statement.setDate(2, dataConvertita);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeMenuTematico = resultSet.getString("NomeMenuTematico");
                menuTematici.add(nomeMenuTematico);
            }

            return menuTematici;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteMenuTematicoFromNome(String nomeMenuTematico) {
        String deleteRicettaQuery = "DELETE FROM MenuTematico WHERE NomeMenuTematico = ?";

        try {
            PreparedStatement deleteRicettaStatement = connection.prepareStatement(deleteRicettaQuery);
            deleteRicettaStatement.setString(1, nomeMenuTematico);
            deleteRicettaStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
