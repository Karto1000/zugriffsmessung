package database.tables;

import database.DatabaseConnection;
import database.Table;
import models.Kontakt;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class KontakteTable implements Table<Kontakt> {
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

    @Override
    public ArrayList<Kontakt> getAll() {
        return this.getGeneric(TABLE_NAME, KONTAKTE_MAPPER);
    }

    @Override
    public Connection getConnection() {
        return DatabaseConnection.getInstance();
    }

    public List<Kontakt> getByName(String name) {
        return DSL.using(this.getConnection())
                .select()
                .from(TABLE_NAME)
                .where(DSL.field("name").eq(name))
                .fetch()
                .map(KONTAKTE_MAPPER);
    }

    public List<Kontakt> getByIndex(String index) {
        return DSL.using(this.getConnection())
                .select()
                .from(TABLE_NAME)
                .where(DSL.field("nameI").eq(index))
                .fetch()
                .map(KONTAKTE_MAPPER);
    }

}
