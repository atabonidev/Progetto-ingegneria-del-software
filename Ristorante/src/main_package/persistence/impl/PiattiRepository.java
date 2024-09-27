package main_package.persistence.impl;

import main_package.model.*;
import main_package.persistence.IPiattiRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiattiRepository implements IPiattiRepository {

    private Connection connection;

    public PiattiRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getNomiPiattiDelGiorno(LocalDate data) {
        String query = "SELECT NomePiatto FROM Piatto WHERE (DataInizioValidita < ?) AND (DataFineValidita > ?) ";

        List<String> nomiPiattiDelGiorno = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(data));
            statement.setDate(2, Date.valueOf(data));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomePiatto = resultSet.getString("NomePiatto");
                nomiPiattiDelGiorno.add(nomePiatto);
            }

            return nomiPiattiDelGiorno;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void insertPiatto(Piatto piatto) {
        String query = "INSERT INTO Piatto (NomePiatto, Ricetta, DataInizioValidita, DataFineValidita, CaricoLavoroPiatto) VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            //Settiamo dati del piatto da aggiungere
            statement.setString(1, piatto.getNome());
            statement.setString(2, piatto.getRicetta());
            statement.setDate(3, Date.valueOf(piatto.getInizioValidita()));
            statement.setDate(4, Date.valueOf(piatto.getFineValidita()));
            statement.setDouble(5, piatto.getCaricoLavoroPiatto());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getCorrispondenzaPiattiRicette() {
        String query = "SELECT NomePiatto, Ricetta FROM Piatto";

        Map<String, String> corrispondenzaPiattoRicetta = new HashMap<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nomePiatto = resultSet.getString("NomePiatto");
                String ricetta = resultSet.getString("Ricetta");
                corrispondenzaPiattoRicetta.put(nomePiatto, ricetta);
            }

            return corrispondenzaPiattoRicetta;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getNomiPiattiPerPeriodoDiValidita(LocalDate inizioValiditaMenuTematico, LocalDate fineValiditaMenuTematico) {
        String query = "SELECT NomePiatto FROM Piatto WHERE (DataInizioValidita <= ?) AND (DataFineValidita >= ?)";

        List<String> nomiPiattiPerPeriodoDiValidita = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(inizioValiditaMenuTematico));
            statement.setDate(2, Date.valueOf(fineValiditaMenuTematico));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomePiatto = resultSet.getString("NomePiatto");
                nomiPiattiPerPeriodoDiValidita.add(nomePiatto);
            }

            return nomiPiattiPerPeriodoDiValidita;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Piatto getPiattoFromName(String nomePiatto) {
        String query = "SELECT * FROM Piatto WHERE NomePiatto = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomePiatto);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String ricetta = resultSet.getString("Ricetta");
                LocalDate dataInizioValidita = resultSet.getDate("DataInizioValidita").toLocalDate();
                LocalDate dataFineValidita = resultSet.getDate("DataFineValidita").toLocalDate();
                double caricoLavoroPiatto = resultSet.getDouble("CaricoLavoroPiatto");
                return new Piatto(nomePiatto, ricetta, caricoLavoroPiatto, dataInizioValidita, dataFineValidita);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isListaPiattiVuota() {
        String query = "SELECT COUNT(*) FROM Piatto";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return (rowCount == 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public double getCaricoDiLavoroInsiemePiatti(List<String> listaPiatti) {
        StringBuilder query = new StringBuilder("SELECT SUM(CaricoLavoroPiatto) FROM Piatto WHERE NomePiatto IN (");
        query.append("?, ".repeat(Math.max(0, listaPiatti.size() - 1)));
        query.append("?)");

        try {
            PreparedStatement statement = connection.prepareStatement(query.toString());

            for (int i = 0; i < listaPiatti.size(); i++) {
                statement.setString(i + 1, listaPiatti.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double caricoDiLavoroInsiemePiatti = resultSet.getDouble(1);
                return caricoDiLavoroInsiemePiatti;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
