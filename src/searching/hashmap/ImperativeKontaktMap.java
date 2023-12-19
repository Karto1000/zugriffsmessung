package searching.hashmap;

import models.Kontakt;
import searching.Searchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class ImperativeKontaktMap extends HashMap<String, List<Kontakt>> implements Searchable<Kontakt> {
    public void fromList(List<Kontakt> element) {
        for (Kontakt kontakt : element) {
            ArrayList<Kontakt> sameNameContact = new ArrayList<>();

            for (Kontakt otherKontakt : element) {
                if (otherKontakt.getName().equals(kontakt.getName())) sameNameContact.add(otherKontakt);
            }

            this.put(kontakt.getName(), sameNameContact);
        }
    }

    @Override
    public List<Kontakt> search(Predicate<? super Kontakt> predicate) {
        ArrayList<Kontakt> flatList = new ArrayList<>();
        for (List<Kontakt> kontakte : this.values()) flatList.addAll(kontakte);

        ArrayList<Kontakt> result = new ArrayList<>();
        for (Kontakt kontakt : flatList) if (predicate.test(kontakt)) result.add(kontakt);

        return result;
    }
}