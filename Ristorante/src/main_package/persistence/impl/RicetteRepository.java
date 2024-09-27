package main_package.persistence.impl;

import main_package.model.Ricetta;
import main_package.persistence.IRicetteRepository;

import java.sql.*;
import java.util.*;

public class RicetteRepository implements IRicetteRepository {

    private Connection connection;

    public RicetteRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertRicetta(Ricetta ricetta) {
        String query1 = "INSERT INTO Ricetta (NomeRicetta, Porzioni, CaricoLavoroPorzione) VALUES (?, ?, ?)";

        String query2 = """
                    INSERT INTO Prodotto (NomeProdotto, TipoProdotto, UnitaDiMisura, ConsumoProCapite)
                    SELECT ?, "ingrediente", "kg", -1
                    WHERE NOT EXISTS (
                      SELECT *
                      FROM Prodotto
                      WHERE NomeProdotto = ?
                    );""";

        StringBuilder query3 = new StringBuilder("INSERT INTO RicettaIngrediente(Ricetta, Ingrediente, TipoProdotto, Quantita) VALUES ");
        query3.append("(?, ?, ?, ?),".repeat(Math.max(0, ricetta.getIngredienti().size() - 1)));
        query3.append("(?, ?, ?, ?)");

        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            //Settiamo dati della Ricetta
            statement.setString(1, ricetta.getNome());
            statement.setInt(2, ricetta.getPorzioni());
            statement.setDouble(3, ricetta.getCaricoLavPerPorzione());
            statement.executeUpdate();

            //Inserisco ingredienti utilizzati nella tabella prodotti se non sono già presenti

            statement = connection.prepareStatement(query2);
            for(String ingrediente : ricetta.getIngredienti().keySet()){
                statement.setString(1, ingrediente);
                statement.setString(2, ingrediente);
                statement.executeUpdate();
            }

            //Settiamo righe corrispondenza Ricetta-Ingrediente
            int index = 1;
            statement = connection.prepareStatement(query3.toString());
            for (String ingrediente : ricetta.getIngredienti().keySet()) {
                statement.setString(index++, ricetta.getNome());
                statement.setString(index++, ingrediente);
                statement.setString(index++, "ingrediente");
                statement.setDouble(index++, ricetta.getIngredienti().get(ingrediente));
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ricetta> getRicette() {
        String query = """
                SELECT R.*, RI.Ingrediente AS Ingrediente, RI.Quantita AS Quantita
                FROM Ricetta R
                INNER JOIN RicettaIngrediente RI ON R.NomeRicetta = RI.Ricetta;""";

        List<Ricetta> ricette = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nomeRicetta = resultSet.getString("NomeRicetta");
                int porzioni = resultSet.getInt("Porzioni");
                double caricoLavoroPorzione = resultSet.getDouble("CaricoLavoroPorzione");
                String ingrediente = resultSet.getString("Ingrediente");
                double quantita = resultSet.getDouble("Quantita");

                Ricetta ricetta = ricette.stream()
                        .filter(r -> r.getNome().equals(nomeRicetta))
                        .findFirst()
                        .orElse(null);

                if (ricetta == null) {
                    ricetta = new Ricetta(nomeRicetta, new HashMap<>(), porzioni, caricoLavoroPorzione);
                    ricetta.aggiungiIngrediente(ingrediente, quantita);
                    ricette.add(ricetta);
                } else {
                    ricetta.aggiungiIngrediente(ingrediente, quantita);
                }
            }

            return ricette;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Ricetta getRicettaFromName(String nome) {
        String query = """
                SELECT R.*, RI.Ingrediente AS Ingrediente, RI.Quantita AS Quantita
                FROM Ricetta R
                INNER JOIN RicettaIngrediente RI ON R.NomeRicetta = RI.Ricetta
                WHERE R.NomeRicetta = ?;""";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            Ricetta ricetta = null;

            if(resultSet.next()) {
                int porzioni = resultSet.getInt("Porzioni");
                double caricoLavoroPorzione = resultSet.getDouble("CaricoLavoroPorzione");
                String ingrediente = resultSet.getString("Ingrediente");
                double quantita = resultSet.getDouble("Quantita");

                ricetta = new Ricetta(nome, new HashMap<>(), porzioni, caricoLavoroPorzione);
                ricetta.aggiungiIngrediente(ingrediente, quantita);
            }

            while (resultSet.next()) {
                String ingrediente = resultSet.getString("Ingrediente");
                double quantita = resultSet.getDouble("Quantita");

                assert ricetta != null;
                ricetta.aggiungiIngrediente(ingrediente, quantita);
            }

            return ricetta;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isRicettaPresente(String nome) {
        String query = "SELECT COUNT(*) FROM Ricetta WHERE NomeRicetta = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return (rowCount != 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public double getCaricoDiLavoroDellaRicetta(String nomeRicetta) {
        String query = "SELECT CaricoLavoroPorzione FROM Ricetta WHERE NomeRicetta = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeRicetta);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double caricoLavororicetta = resultSet.getDouble("CaricoLavoroPorzione");
                return caricoLavororicetta;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void deleteRicetta(String nomeRicetta) {
        String deleteRicettaQuery = "DELETE FROM Ricetta WHERE NomeRicetta = ?";

        try {
            PreparedStatement deleteRicettaStatement = connection.prepareStatement(deleteRicettaQuery);
            deleteRicettaStatement.setString(1, nomeRicetta);
            deleteRicettaStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
