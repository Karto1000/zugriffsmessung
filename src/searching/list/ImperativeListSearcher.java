package searching.list;

import searching.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ImperativeListSearcher<T> implements Searchable<T> {
    private final List<T> list;

    public ImperativeListSearcher(List<T> list) {
        this.list = list;
    }

    @Override
    public List<T> search(Predicate<? super T> predicate) {
        ArrayList<T> result = new ArrayList<>();

        for (T element : this.list) {
            if (predicate.test(element)) result.add(element);
        }

        return result;
    }
}
