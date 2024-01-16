package searching.hashmap;

import searching.Searchable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalMapSearcher<T> implements Searchable<T> {
    private final Map<String, T> map;

    public FunctionalMapSearcher(Map<String, T> map) {
        this.map = map;
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        return this.map.values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
