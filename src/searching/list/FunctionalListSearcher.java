package searching.list;

import searching.Searchable;

import java.util.List;
import java.util.function.Predicate;

public class FunctionalListSearcher<T> implements Searchable<T> {
    private final List<T> list;

    public FunctionalListSearcher(List<T> list) {
        this.list = list;
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        return this.list.stream().filter(predicate).toList();
    }
}
