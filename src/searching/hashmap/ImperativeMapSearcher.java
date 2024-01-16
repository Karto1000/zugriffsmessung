package searching.hashmap;

import searching.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ImperativeMapSearcher<T> implements Searchable<T> {
    private final Map<String, T> map;

    public ImperativeMapSearcher(Map<String, T> map) {
        this.map = map;
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        ArrayList<T> result = new ArrayList<>();
        for (T element : this.map.values()) if (predicate.test(element)) result.add(element);
        return result;
    }
}
