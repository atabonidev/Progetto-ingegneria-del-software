package main_package.persistence.impl;
import main_package.model.*;
import main_package.persistence.IPrenotazioniRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrenotazioniRepository implements IPrenotazioniRepository {

    private Connection connection;

    public PrenotazioniRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void aggiungiPrenotazione(Prenotazione prenotazione) {
        String query1 = "INSERT INTO Prenotazione (IdPrenotazione, NumeroCoperti, DataDiPrenotazione) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            //Settiamo dati della Prenotazione
            statement.setString(1, prenotazione.getIDprenotazione());
            statement.setInt(2, prenotazione.getNumeroCoperti());
            statement.setDate(3, Date.valueOf(prenotazione.getDataPrenotazione()));
            statement.executeUpdate();

            int index;

            if(!prenotazione.getListaMenuTematiciOrdinati().isEmpty()) {
                index = 1;

                StringBuilder query2 = new StringBuilder("INSERT INTO PrenotazioneMenuTematico(Prenotazione, MenuTematico, Quantita) VALUES ");
                query2.append("(?, ?, ?),".repeat(Math.max(0, prenotazione.getListaMenuTematiciOrdinati().size() - 1)));
                query2.append("(?, ?, ?);");

                statement = connection.prepareStatement(query2.toString());
                //Settiamo righe corrispondenza Prenotazione-MenuTematico
                for (String nomeMenuTematico : prenotazione.getListaMenuTematiciOrdinati().keySet()) {
                    statement.setString(index++, prenotazione.getIDprenotazione());
                    statement.setString(index++, nomeMenuTematico);
                    statement.setInt(index++, prenotazione.getListaMenuTematiciOrdinati().get(nomeMenuTematico));
                }
                statement.executeUpdate();
            }

            if(!prenotazione.getListaPiattiOrdinati().isEmpty()) {
                index = 1;

                StringBuilder query3 = new StringBuilder("INSERT INTO PrenotazionePiatto(Prenotazione, Piatto, Quantita) VALUES ");
                query3.append("(?, ?, ?),".repeat(Math.max(0, prenotazione.getListaPiattiOrdinati().size() - 1)));
                query3.append("(?, ?, ?);");

                statement = connection.prepareStatement(query3.toString());
                //Settiamo righe corrispondenza Prenotazione-Piatto
                for (String nomePiatto : prenotazione.getListaPiattiOrdinati().keySet()) {
                    statement.setString(index++, prenotazione.getIDprenotazione());
                    statement.setString(index++, nomePiatto);
                    statement.setInt(index++, prenotazione.getListaPiattiOrdinati().get(nomePiatto));
                }
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rimuoviPrenotazioniScadute() {
        String query = "DELETE FROM Prenotazione WHERE DataDiPrenotazione < date('now');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Prenotazione> getPrenotazioniPerData(LocalDate data) {
        String query = "SELECT * FROM Prenotazione WHERE DataDiPrenotazione = ?";
        List<Prenotazione> prenotazioni = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(data));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idPrenotazione = resultSet.getString("IdPrenotazione");
                int numeroCoperti = resultSet.getInt("NumeroCoperti");
                LocalDate dataDiPrenotazione = resultSet.getDate("DataDiPrenotazione").toLocalDate();

                prenotazioni.add(new Prenotazione(idPrenotazione, numeroCoperti, dataDiPrenotazione, new HashMap<>(), new HashMap<>()));
            }

            return prenotazioni;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TabellaProdottoQuantita getIngredientiPrenotazioniPerData(LocalDate dataPrenotazione) {
        String query1 = """
                SELECT RicettaIngrediente.Ingrediente AS Ingrediente, SUM(RicettaIngrediente.Quantita) AS QuantitaTotale
                FROM Prenotazione
                JOIN PrenotazioneMenuTematico ON Prenotazione.IdPrenotazione = PrenotazioneMenuTematico.Prenotazione
                JOIN MenuTematico ON PrenotazioneMenuTematico.MenuTematico = MenuTematico.NomeMenuTematico
                JOIN MenuTematicoPiatto ON MenuTematico.NomeMenuTematico = MenuTematicoPiatto.MenuTematico
                JOIN Piatto ON MenuTematicoPiatto.Piatto = Piatto.NomePiatto
                JOIN Ricetta ON Piatto.Ricetta = Ricetta.NomeRicetta
                JOIN RicettaIngrediente ON Ricetta.NomeRicetta = RicettaIngrediente.Ricetta
                WHERE Prenotazione.DataDiPrenotazione = ?
                GROUP BY RicettaIngrediente.Ingrediente;""";

        String query2 = """
                SELECT RicettaIngrediente.Ingrediente, SUM(RicettaIngrediente.Quantita) AS QuantitaTotale
                FROM Prenotazione
                JOIN PrenotazionePiatto ON Prenotazione.IdPrenotazione = PrenotazionePiatto.Prenotazione
                JOIN Piatto ON Piatto.NomePiatto = PrenotazionePiatto.Piatto
                JOIN Ricetta ON Piatto.Ricetta = Ricetta.NomeRicetta
                JOIN RicettaIngrediente ON Ricetta.NomeRicetta = RicettaIngrediente.Ricetta
                WHERE Prenotazione.DataDiPrenotazione = ?
                GROUP BY RicettaIngrediente.Ingrediente;""";

        TabellaProdottoQuantita ingredientiPrenotazioniPerData = new TabellaProdottoQuantita(new HashMap<>());

        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setDate(1, Date.valueOf(dataPrenotazione));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ingrediente = resultSet.getString("Ingrediente");
                double quantita = resultSet.getDouble("QuantitaTotale");

                ingredientiPrenotazioniPerData.inserisciProdotto(new Prodotto(ingrediente, TipoProdotto.INGREDIENTE, UnitaMisura.KG), quantita);
            }

            statement = connection.prepareStatement(query2);
            statement.setDate(1, Date.valueOf(dataPrenotazione));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ingrediente = resultSet.getString("Ingrediente");
                double quantita = resultSet.getDouble("QuantitaTotale");

                ingredientiPrenotazioniPerData.inserisciProdotto(new Prodotto(ingrediente, TipoProdotto.INGREDIENTE, UnitaMisura.KG), quantita);
            }

            return ingredientiPrenotazioniPerData;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public TabellaProdottoQuantita getBevandeServitePrenotazioniPerData(LocalDate dataPrenotazione) {
        String query = """
                SELECT Prodotto.NomeProdotto AS Bevanda, SUM(Prodotto.ConsumoProCapite * Prenotazione.NumeroCoperti) AS Quantita
                FROM Prenotazione, Prodotto
                WHERE (Prenotazione.DataDiPrenotazione = ?) AND (Prodotto.TipoProdotto = 'bevanda_servita')
                GROUP BY Prodotto.NomeProdotto;""";

        TabellaProdottoQuantita bevandeServitePrenotazioniPerData = new TabellaProdottoQuantita(new HashMap<>());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(dataPrenotazione));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bevanda = resultSet.getString("Bevanda");
                double quantita = Math.ceil(resultSet.getDouble("Quantita"));

                bevandeServitePrenotazioniPerData.inserisciProdotto(new Prodotto(bevanda, TipoProdotto.BEVANDA_SERVITA, UnitaMisura.LITRI), quantita);
            }

            return bevandeServitePrenotazioniPerData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TabellaProdottoQuantita getGeneriExtraServitiPrenotazioniPerData(LocalDate dataPrenotazione) {
        String query = """
                SELECT Prodotto.NomeProdotto AS GenereExtra, SUM(Prodotto.ConsumoProCapite * Prenotazione.NumeroCoperti) AS Quantita
                FROM Prenotazione, Prodotto
                WHERE (Prenotazione.DataDiPrenotazione = ?) AND (Prodotto.TipoProdotto = 'genere_extra')
                GROUP BY Prodotto.NomeProdotto;""";

        TabellaProdottoQuantita generiExtraServitiPrenotazioniPerData = new TabellaProdottoQuantita(new HashMap<>());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(dataPrenotazione));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String genereExtra = resultSet.getString("GenereExtra");
                double quantita = Math.ceil(resultSet.getDouble("Quantita"));

                generiExtraServitiPrenotazioniPerData.inserisciProdotto(new Prodotto(genereExtra, TipoProdotto.GENERE_EXTRA, UnitaMisura.KG), quantita);
            }

            return generiExtraServitiPrenotazioniPerData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void clearTable(){
        String deleteQuery = "DELETE FROM Prenotazione";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
