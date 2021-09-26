package me.gicheol.sql;

import me.gicheol.exception.SqlNotFoundException;

public interface SqlRegistry {

    String findSql(String key) throws SqlNotFoundException;

    void registerSql(String key, String sql);

}
