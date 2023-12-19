import database.tables.KontakteTable;
import models.Kontakt;
import searching.hashmap.FunctionalMapSearcher;
import searching.hashmap.ImperativeMapSearcher;
import searching.list.FunctionalListSearcher;
import searching.list.ImperativeListSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class app {
    public static void main(String[] args) {
        // DB lesen und ArrayList erstellen
        KontakteTable kontakteTable = new KontakteTable();
        List<Kontakt> kontakte = kontakteTable.getAll();

        FunctionalListSearcher<Kontakt> functionalListSearcher = new FunctionalListSearcher<>(kontakte);
        ImperativeListSearcher<Kontakt> imperativeListSearcher = new ImperativeListSearcher<>(kontakte);

        FunctionalMapSearcher<List<Kontakt>> functionalMapSearcher = new FunctionalMapSearcher<>();
        ImperativeMapSearcher<List<Kontakt>> imperativeMapSearcher = new ImperativeMapSearcher<>();

        // HashMap erstellen - imperativ
        imperativeMapSearcher.loadList(
                kontakte,
                k -> {
                    ArrayList<Kontakt> sameNameContact = new ArrayList<>();

                    for (Kontakt otherKontakt : kontakte) {
                        if (otherKontakt.getName().equals(k.getName())) sameNameContact.add(otherKontakt);
                    }

                    return sameNameContact;
                },
                Kontakt::getName
        );

        // HashMap erstellen - funktional
        functionalMapSearcher.loadList(
                kontakte,
                k -> kontakte.stream()
                        .filter(f -> f
                                .getName()
                                .equals(k.getName())
                        )
                        .collect(Collectors.toList()),
                Kontakt::getName
        );

        // Zufälliger Datensatz bestimmen
        Random random = new Random();
        Kontakt randomKontakt = kontakte.get(random.nextInt(kontakte.size() - 1));

        // DB lesen ohne Index
        System.out.println(kontakteTable.getByName(randomKontakt.getName()));

        // DB lesen mit Index
        System.out.println(kontakteTable.getByIndex(randomKontakt.getName()));

        // ArrayList lesen imperativ
        System.out.println(imperativeListSearcher.search(kontakt -> kontakt.getName().equals(randomKontakt.getName())
                && kontakt.getVorname().equals(randomKontakt.getVorname())));

        // ArrayList lesen funktional
        System.out.println(functionalListSearcher.search(kontakt -> kontakt.getName().equals(randomKontakt.getName())
                && kontakt.getVorname().equals(randomKontakt.getVorname())));

        // HashMap lesen imperativ
        System.out.println(imperativeMapSearcher.search(v -> {
            for (Kontakt element : v) {
                if (element.getName().equals(randomKontakt.getName()) && element.getVorname().equals(randomKontakt.getVorname())) {
                    return true;
                }
            }
            return false;
        }));

        // HashMap lesen funktional
        System.out.println(functionalMapSearcher.search(v -> v.stream().anyMatch(k -> k.getName().equals(randomKontakt.getName()) && k.getVorname().equals(randomKontakt.getVorname()))));
    }

}