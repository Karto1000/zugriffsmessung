package database;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface Table<T> {
    default ArrayList<T> getGeneric(String tableName, RecordMapper<Record, T> mapper) {
        return new ArrayList<>(DSL.using(this.getConnection())
                .select()
                .from(tableName)
                // TODO: Revisit, This is just for testing
                .limit(10)
                .fetch()
                .map(mapper));

    }

    Connection getConnection();

    List<T> getAll();
}
