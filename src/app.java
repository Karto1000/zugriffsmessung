import models.Kontakt;
import searching.hashmap.FunctionalMapSearcher;
import searching.hashmap.ImperativeMapSearcher;
import searching.list.FunctionalListSearcher;
import searching.list.ImperativeListSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class app {
    public static void measure(String message, Runnable runnable) {
        System.out.printf("\n----< %s >-----\n", message);
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.printf(" > Function took %d milliseconds to execute\n-----\n", end - start);
    }

    public static <T> T measure(String message, Supplier<T> runnable) {
        System.out.printf("\n----< Running Function %s >-----\n", message);
        long start = System.currentTimeMillis();
        T result = runnable.get();
        long end = System.currentTimeMillis();
        System.out.printf(" > Function took %d milliseconds to execute\n-----\n", end - start);
        return result;
    }

    public static void main(String[] args) {
        // DB lesen und ArrayList erstellen
//        KontakteTable kontakteTable = new KontakteTable();
//        List<Kontakt> kontakte = measure("Reading Data from Database", kontakteTable::getAll);

        List<Kontakt> kontakte = new ArrayList<>();
        kontakte.add(Kontakt.builder()
                .kid(1)
                .email("test.test@test.com")
                .nameI("Lol")
                .name("Lol")
                .strasse("TestStreet 12")
                .vorname("Test")
                .plz(1234)
                .tgesch("wtf")
                .tpriv("wtf")
                .build()
        );

        kontakte.add(Kontakt.builder()
                .kid(2)
                .email("test2.test@test.com")
                .nameI("Lol2")
                .name("Lol2")
                .strasse("TestStreet 12")
                .vorname("Test2")
                .plz(1234)
                .tgesch("wtf")
                .tpriv("wtf")
                .build()
        );

        kontakte.add(Kontakt.builder()
                .kid(3)
                .email("test2.test@test.com")
                .nameI("Lol2")
                .name("Lol2")
                .strasse("TestStreet 12")
                .vorname("Test2")
                .plz(1234)
                .tgesch("wtf")
                .tpriv("wtf")
                .build()
        );


        FunctionalListSearcher<Kontakt> functionalListSearcher = new FunctionalListSearcher<>(kontakte);
        ImperativeListSearcher<Kontakt> imperativeListSearcher = new ImperativeListSearcher<>(kontakte);

        FunctionalMapSearcher<List<Kontakt>> functionalMapSearcher = new FunctionalMapSearcher<>();
        ImperativeMapSearcher<List<Kontakt>> imperativeMapSearcher = new ImperativeMapSearcher<>();

        // [HashMap Erstellen Imperativ]
        measure("Loading Imperative Map", () -> {
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
        });

        // [HashMap Erstellen Funktional]
        measure("Loading Function Map", () -> {
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
        });

        // [Zufälligen Kontakt auslesen]
        Kontakt randomKontakt = measure("Loading Random Kontakt", () -> {
            // Zufälliger Datensatz bestimmen
            Random random = new Random();
            return kontakte.get(random.nextInt(kontakte.size() - 1));
        });

        // [DB Suchen ohne Index]
        measure("Reading DB without Index", () -> {
            // DB lesen ohne Index
            // System.out.println(kontakteTable.getByName(randomKontakt.getName()));
        });

        // [DB Suchen mit Index]
        measure("Reading DB with Index", () -> {
            // DB lesen mit Index
            // System.out.println(kontakteTable.getByIndex(randomKontakt.getNameI()));
        });

        // [ArrayList durchsuchen imperativ]
        measure("Searching ArrayList Imperative", () -> {
            // ArrayList lesen imperativ
            imperativeListSearcher.search(kontakt -> kontakt.getName().equals(randomKontakt.getName())
                    && kontakt.getVorname().equals(randomKontakt.getVorname()));
        });

        // [ArrayList durchsuchen funktional]
        measure("Searching Arraylist Functional", () -> {
            // ArrayList lesen funktional
            functionalListSearcher.search(kontakt -> kontakt.getName().equals(randomKontakt.getName())
                    && kontakt.getVorname().equals(randomKontakt.getVorname()));
        });

        // [HashMap durchsuchen imperativ]
        measure("Searching Hashmap Imperative", () -> {
            // HashMap lesen imperativ
            imperativeMapSearcher.search(v -> {
                for (Kontakt element : v) {
                    if (element.getName().equals(randomKontakt.getName()) && element.getVorname().equals(randomKontakt.getVorname())) {
                        return true;
                    }
                }
                return false;
            });
        });

        // [HashMap durchsuchen funktional]
        measure("Searching Hashmap Functional", () -> {
            // HashMap lesen funktional
            functionalMapSearcher.search(
                    v -> v.stream().anyMatch(k -> k.getName().equals(randomKontakt.getName()) && k.getVorname().equals(randomKontakt.getVorname()))
            );
        });
    }

}