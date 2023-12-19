package database.tables;

import database.DatabaseConnection;
import models.Kontakt;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

public class KontakteTable {
    private static final String TABLE_NAME = "kontakte";
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

    public List<Kontakt> getAll() {
        return new ArrayList<>(DSL.using(DatabaseConnection.getInstance())
                .select()
                .from(TABLE_NAME)
                // TODO: Revisit, This is just for testing
                .limit(10)
                .fetch()
                .map(KONTAKTE_MAPPER)
        );
    }

    public List<Kontakt> getByName(String name) {
        return new ArrayList<>(DSL.using(DatabaseConnection.getInstance())
                .select()
                .from(TABLE_NAME)
                .where(DSL.field("name").eq(name))
                // TODO: Revisit, This is just for testing
                .limit(10)
                .fetch()
                .map(KONTAKTE_MAPPER)
        );
    }

    public List<Kontakt> getByIndex(String index) {
        return new ArrayList<>(DSL.using(DatabaseConnection.getInstance())
                .select()
                .from(TABLE_NAME)
                .where(DSL.field("nameI").eq(index))
                // TODO: Revisit, This is just for testing
                .limit(10)
                .fetch()
                .map(KONTAKTE_MAPPER)
        );
    }
}
