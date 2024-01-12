package database.tables;

import models.Kontakt;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.util.List;

public class KontakteTable extends Table<Kontakt> {
    private static final RecordMapper<Record, Kontakt> KONTAKTE_MAPPER = (record -> Kontakt.builder()
            .kid((Integer) record.getValue("kid"))
            .name((String) record.getValue("name"))
            .nameI((String) record.getValue("nameI"))
            .vorname((String) record.getValue("vorname"))
            .strasse((String) record.getValue("strasse"))
            .email((String) record.getValue("email"))
            .tpriv((String) record.getValue("tpriv"))
            .tgesch((String) record.getValue("tgesch"))
            .plz((Integer) record.getValue("plz"))
            .ort((String) record.getValue("ort"))
            .build());

    public KontakteTable() {
        super("kontakte", KONTAKTE_MAPPER);
    }

    public List<Kontakt> getAll() {
        return this.readGeneric();
    }

    public List<Kontakt> getByName(String name) {
        return this.readGeneric(DSL.field("name").eq(name));
    }

    public List<Kontakt> getByIndex(String index) {
        return this.readGeneric(DSL.field("nameI").eq(index));
    }
}
