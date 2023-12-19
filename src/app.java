import database.tables.KontakteTable;
import models.Kontakt;
import searching.hashmap.FunctionalKontaktMap;
import searching.hashmap.ImperativeKontaktMap;
import searching.list.FunctionalSearchableList;
import searching.list.ImperativeSearchableList;

import java.util.ArrayList;

public class app {
    public static void main(String[] args) {
        // DB lesen und ArrayList erstellen
        KontakteTable kontakteTable = new KontakteTable();
        ArrayList<Kontakt> kontakte = kontakteTable.getAll();

        FunctionalSearchableList<Kontakt> functionalSearchableList = new FunctionalSearchableList<>(kontakte);
        ImperativeSearchableList<Kontakt> imperativeSearchableList = new ImperativeSearchableList<>(kontakte);

        // HashMap initialisieren
        FunctionalKontaktMap funktionalKontaktMap = new FunctionalKontaktMap();
        ImperativeKontaktMap imperativKontaktMap = new ImperativeKontaktMap();

        // HashMap erstellen - imperativ
        imperativKontaktMap.loadList(kontakte);

        // HashMap erstellen - funktional
        funktionalKontaktMap.loadList(kontakte);

        // DB lesen ohne Index
        System.out.println(kontakteTable.getByName("Doria"));

        // DB lesen mit Index
        System.out.println(kontakteTable.getByIndex("Doria"));

        // ArrayList lesen imperativ
        System.out.println(imperativeSearchableList.search(kontakt -> kontakt.getName().equals("Doria")
                && kontakt.getVorname().equals("Noelle-Anna")));

        // ArrayList lesen funktional
        System.out.println(functionalSearchableList.search(kontakt -> kontakt.getName().equals("Doria")
                && kontakt.getVorname().equals("Noelle-Anna")));

        // HashMap lesen imperativ
        System.out.println(imperativKontaktMap.search(kontakt -> kontakt.getName().equals("Doria")
                && kontakt.getVorname().equals("Noelle-Anna")));

        // HashMap lesen funktional
        System.out.println(funktionalKontaktMap.search(kontakt -> kontakt.getName().equals("Doria")
                && kontakt.getVorname().equals("Noelle-Anna")));

    }

}