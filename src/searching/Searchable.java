package searching;

import java.util.List;
import java.util.function.Predicate;

public interface Searchable<T> {
    List<T> search(Predicate<? super T> predicate);
}
