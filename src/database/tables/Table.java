package database.tables;

import database.DatabaseConnection;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

public abstract class Table<T> {
    private final String tableName;
    private final RecordMapper<Record, T> recordMapper;

    public Table(String tableName, RecordMapper<Record, T> recordMapper) {
        this.tableName = tableName;
        this.recordMapper = recordMapper;
    }

    public List<T> readGeneric(Condition... conditions) {
        return new ArrayList<>(DSL.using(DatabaseConnection.getInstance())
                .select()
                .from(tableName)
                .where(conditions)
                .limit(50000)
                .fetch()
                .map(recordMapper)
        );
    }

    public List<T> readGeneric() {
        return new ArrayList<>(DSL.using(DatabaseConnection.getInstance())
                .select()
                .from(tableName)
                .limit(50000)
                .fetch()
                .map(recordMapper)
        );
    }

}
