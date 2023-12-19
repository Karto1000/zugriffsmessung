package searching.hashmap;

import searching.Searchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ImperativeMapSearcher<T> implements Searchable<T> {
    private final Map<String, T> map;

    public ImperativeMapSearcher(Map<String, T> map) {
        this.map = map;
    }

    public ImperativeMapSearcher() {
        this.map = new HashMap<>();
    }

    public <U> void loadList(List<U> element, Function<U, T> value, Function<U, String> key) {
        for (U e : element) {
            this.map.put(key.apply(e), value.apply(e));
        }
    }

    public void loadList(List<T> element, Function<T, String> key) {
        for (T e : element) {
            this.map.put(key.apply(e), e);
        }
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        ArrayList<T> result = new ArrayList<>();
        for (T element : this.map.values()) if (predicate.test(element)) result.add(element);
        return result;
    }
}
