package searching.hashmap;

import models.Kontakt;
import searching.Searchable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalKontaktMap extends HashMap<String, List<Kontakt>> implements Searchable<Kontakt> {
    public void loadList(List<Kontakt> element) {
        element.forEach(e -> this.put(
                e.getName(),
                element.stream()
                        .filter(f -> f
                                .getName()
                                .equals(e.getName()))
                        .collect(Collectors.toList())
        ));
    }

    @Override
    public List<Kontakt> search(Predicate<? super Kontakt> predicate) {
        return this.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
