package searching.list;

import searching.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FunctionalSearchableList<T> extends ArrayList<T> implements Searchable<T> {
    public FunctionalSearchableList(List<T> list) {
        this.addAll(list);
    }

    public FunctionalSearchableList() {
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        return this.stream().filter(predicate).toList();
    }
}
