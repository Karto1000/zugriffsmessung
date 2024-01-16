import database.tables.KontakteTable;
import models.Kontakt;
import searching.hashmap.FunctionalMapSearcher;
import searching.hashmap.ImperativeMapSearcher;
import searching.list.FunctionalListSearcher;
import searching.list.ImperativeListSearcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
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
        System.out.printf("\n----< %s >-----\n", message);
        long start = System.currentTimeMillis();
        T result = runnable.get();
        long end = System.currentTimeMillis();
        System.out.printf(" > Function took %d milliseconds to execute\n-----\n", end - start);
        return result;
    }

    /**
     * <strong>Falls
     * Die Datenbank Verbindung nicht funktioniert kann man den Connectionstring in der
     * {@link database.DatabaseConnection}
     * Klasse Anpassen
     * </strong>
     *
     * <p>
     * <strong>DB lesen und ArrayList erstellen </strong>
     * <ul>
     * <li>{@link database.tables.KontakteTable}</li>
     * </ul>
     *
     * <strong>Liste erstellen Funktional</strong>
     * <ul>
     * <li>{@link searching.list.FunctionalListSearcher}</li>
     * </ul>
     *
     * <strong>Liste erstellen Imperativ</strong>
     * <ul>
     * <li>{@link searching.list.ImperativeListSearcher}</li>
     * </ul>
     *
     * <strong>HashMap erstellen Imperativ</strong>
     * <ul>
     * <li>{@link searching.hashmap.ImperativeMapSearcher}</li>
     * </ul>
     *
     * <strong>HashMap erstellen Funktional</strong>
     * <ul>
     * <li>{@link searching.hashmap.FunctionalMapSearcher}</li>
     * </ul>
     *
     * <strong>DB suchen ohne index</strong>
     * <ul>
     * <li>{@link database.tables.KontakteTable#getByName(String)}</li>
     * </ul>
     *
     * <strong>DB suchen mit index</strong>
     * <ul>
     * <li>{@link database.tables.KontakteTable#getByIndex(String)}</li>
     * </ul>
     *
     * <strong>ArrayList durchsuchen Imperativ</strong>
     * <ul>
     * <li>{@link searching.list.ImperativeListSearcher#search(Predicate)}</li>
     * </ul>
     *
     * <strong>ArrayList durchsuchen Funktional</strong>
     * <ul>
     * <li>{@link searching.list.FunctionalListSearcher#search(Predicate)}</li>
     * </ul>
     *
     * <strong>HashMap durchsuchen Imperativ</strong>
     * <ul>
     * <li>{@link searching.hashmap.ImperativeMapSearcher#search(Predicate)}</li>
     * </ul>
     *
     * <strong>HashMap durchsuchen Funktional</strong>
     * <ul>
     * <li>{@link searching.hashmap.FunctionalMapSearcher#search(Predicate)}</li>
     * </ul>
     */
    public static void main(String[] args) {
        // DB lesen und ArrayList erstellen
        KontakteTable kontakteTable = new KontakteTable();
        List<Kontakt> kontakte = measure("Reading Data from Database", kontakteTable::getAll);

        // [Liste / Sucher erstellen Funktional]
        FunctionalListSearcher<Kontakt> functionalListSearcher = new FunctionalListSearcher<>(kontakte);

        // [Liste / Sucher erstellen Imperativ]
        ImperativeListSearcher<Kontakt> imperativeListSearcher = new ImperativeListSearcher<>(kontakte);

        // [HashMap erstellen Imperativ]
        ImperativeMapSearcher<List<Kontakt>> imperativeMapSearcher = measure("Loading Imperative Map", () -> {
            HashMap<String, List<Kontakt>> hashMap = new HashMap<>();

            for (Kontakt contact : kontakte) {
                ArrayList<Kontakt> sameNameContacts = new ArrayList<>();

                for (Kontakt otherKontakt : kontakte) {
                    if (otherKontakt.getName().equals(contact.getName())) sameNameContacts.add(otherKontakt);
                }

                hashMap.put(contact.getName(), sameNameContacts);
            }

            return new ImperativeMapSearcher<>(hashMap);
        });

        // [HashMap erstellen Funktional]
        FunctionalMapSearcher<List<Kontakt>> functionalMapSearcher = measure("Loading Function Map", () -> {
            HashMap<String, List<Kontakt>> hashMap = new HashMap<>();

            kontakte.forEach(k -> {
                List<Kontakt> sameNameContacts = kontakte.stream()
                        .filter(f -> f
                                .getName()
                                .equals(k.getName())
                        )
                        .collect(Collectors.toList());
                hashMap.put(k.getName(), sameNameContacts);
            });

            return new FunctionalMapSearcher<>(hashMap);
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
            kontakteTable.getByName(randomKontakt.getName());
        });

        // [DB Suchen mit Index]
        measure("Reading DB with Index", () -> {
            // DB lesen mit Index
            kontakteTable.getByIndex(randomKontakt.getNameI());
        });

        Predicate<Kontakt> listSearchPredicate = kontakt -> {
            boolean isVornameEqual = kontakt.getVorname().equals(randomKontakt.getVorname());
            boolean isNachnameEqual = kontakt.getName().equals(randomKontakt.getName());

            return isVornameEqual && isNachnameEqual;
        };

        // [ArrayList durchsuchen Imperativ]
        measure("Searching ArrayList Imperative", () -> {
            imperativeListSearcher.search(listSearchPredicate);
        });

        // [ArrayList durchsuchen Funktional]
        measure("Searching Arraylist Functional", () -> {
            functionalListSearcher.search(listSearchPredicate);
        });

        // [HashMap durchsuchen Imperativ]
        measure("Searching Hashmap Imperative", () -> {
            imperativeMapSearcher.search(v -> {
                for (Kontakt element : v) {
                    boolean isVornameEqual = element.getVorname().equals(randomKontakt.getVorname());
                    boolean isNachnameEqual = element.getName().equals(randomKontakt.getName());

                    if (isVornameEqual && isNachnameEqual) return true;
                }

                return false;
            });
        });

        // [HashMap durchsuchen Funktional]
        measure("Searching Hashmap Functional", () -> {
            functionalMapSearcher.search(
                    v -> v.stream().anyMatch(
                            k -> k.getName()
                                    .equals(randomKontakt.getName()) &&
                                    k.getVorname().equals(randomKontakt.getVorname())
                    )
            );
        });
    }

}