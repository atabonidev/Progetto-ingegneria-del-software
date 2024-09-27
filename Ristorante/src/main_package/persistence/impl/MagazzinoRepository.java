package main_package.persistence.impl;

import main_package.model.Prodotto;
import main_package.model.TabellaProdottoQuantita;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.persistence.IMagazzinoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MagazzinoRepository implements IMagazzinoRepository {

    private Connection connection;

    public MagazzinoRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void aggiornaMagazzino(TabellaProdottoQuantita aggiornamenti) {
        Map<Prodotto, Double> tabellaProdottiDaAggiungere = aggiornamenti.getTabella();

        for (Map.Entry<Prodotto, Double> entry : tabellaProdottiDaAggiungere.entrySet()) {
            Prodotto prodotto = entry.getKey();
            double quantitaDaAggiungere = entry.getValue();

            // Controllo se il prodotto è già presente nel magazzino
            String selectSql = "SELECT Quantita FROM Magazzino WHERE NomeProdotto = ? AND TipoProdotto = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, prodotto.getNome());
                selectStatement.setString(2, prodotto.getTipo().toString().toLowerCase());

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Il prodotto è già presente nel magazzino, quindi si aggiorna la quantità
                        double quantitaPrecedente = resultSet.getDouble("Quantita");
                        double nuovaQuantita = quantitaPrecedente + quantitaDaAggiungere;
                        aggiornaProdottoGiaEsistente(prodotto, nuovaQuantita);
                    } else {
                        //il prodotto non è presente in magazzino
                        inserimentoNuovoProdotto(prodotto, quantitaDaAggiungere);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public TabellaProdottoQuantita getMagazzino () {
        Map<Prodotto, Double> prodottiInMagazzino = new HashMap<>();

        String sql = "SELECT p.NomeProdotto, p.TipoProdotto, p.UnitaDiMisura, m.Quantita " +
                "FROM Prodotto p " +
                "JOIN Magazzino m ON p.NomeProdotto = m.NomeProdotto AND p.TipoProdotto = m.TipoProdotto";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nomeProdotto = resultSet.getString("NomeProdotto");
                String tipoProdotto = resultSet.getString("TipoProdotto");
                String unitaDiMisura = resultSet.getString("UnitaDiMisura");
                double quantita = resultSet.getDouble("Quantita");

                Prodotto prodotto = new Prodotto(nomeProdotto, TipoProdotto.valueOf(tipoProdotto.toUpperCase()), UnitaMisura.valueOf(unitaDiMisura.toUpperCase()));

                prodottiInMagazzino.put(prodotto, quantita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new TabellaProdottoQuantita(prodottiInMagazzino);
    }

    private void aggiornaProdottoGiaEsistente(Prodotto prodotto, double nuovaQuantita){
        String updateSql = "UPDATE Magazzino SET Quantita = ? WHERE NomeProdotto = ? AND TipoProdotto = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setDouble(1, nuovaQuantita);
            updateStatement.setString(2, prodotto.getNome());
            updateStatement.setString(3, prodotto.getTipo().toString().toLowerCase());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiornamento di prodotto già presente");
            e.printStackTrace();
        }
    }

    private void inserimentoNuovoProdotto(Prodotto prodotto, double quantita){
        String insertSql = "INSERT INTO Magazzino  " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, prodotto.getNome());
            insertStatement.setString(2, prodotto.getTipo().toString().toLowerCase());
            insertStatement.setDouble(3, quantita);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'inserimento del nuovo prodotto");
            e.printStackTrace();
        }
    }

    @Override
    public void clearTable(){
        String deleteQuery = "DELETE FROM Magazzino";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

