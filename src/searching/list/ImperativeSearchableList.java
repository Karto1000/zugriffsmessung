package searching.list;

import searching.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ImperativeSearchableList<T> extends ArrayList<T> implements Searchable<T> {
    public ImperativeSearchableList(List<T> list) {
        this.addAll(list);
    }

    public ImperativeSearchableList() {
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        ArrayList<T> result = new ArrayList<>();

        for (T element : this) {
            if (predicate.test(element)) result.add(element);
        }

        return result;
    }
}
