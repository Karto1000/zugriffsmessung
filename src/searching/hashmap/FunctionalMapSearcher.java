package searching.hashmap;

import searching.Searchable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalMapSearcher<T> implements Searchable<T> {
    private final Map<String, T> map;

    public FunctionalMapSearcher(Map<String, T> map) {
        this.map = map;
    }

    public FunctionalMapSearcher() {
        this.map = new HashMap<>();
    }

    public <U> void loadList(List<U> element, Function<U, T> value, Function<U, String> key) {
        element.forEach(e -> this.map.put(key.apply(e), value.apply(e)));
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        return this.map.values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
