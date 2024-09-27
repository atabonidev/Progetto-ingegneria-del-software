package main_package.utilities;

import main_package.model.Prodotto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperazioniSuMappe {
    public static List<String> calcolaIntersezione(Map<Prodotto, Double> map1, Map<Prodotto, Double> map2) {
        List<String> intersection = new ArrayList<>();
        for (Prodotto p : map1.keySet()) {
            if(map2.containsKey(p)) {
                intersection.add(p.getNome());
            }
        }

        return intersection;
    }

    public static Map<Prodotto, Double> calcolaDifferenza(Map<Prodotto, Double> map1, Map<Prodotto, Double> map2) {
        Map<Prodotto, Double> difference = new HashMap<>();
        for (Prodotto p : map1.keySet()) {
            if(!map2.containsKey(p)) {
                difference.put(p, map1.get(p));
            }
        }

        return difference;
    }
}
