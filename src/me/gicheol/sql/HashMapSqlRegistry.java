package me.gicheol.sql;

import me.gicheol.exception.SqlNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {

    private Map<String, String> sqlMap = new HashMap<String, String>();

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);

        if (sql == null) {
            throw new SqlNotFoundException("SQL을 찾을 수 없습니다.");
        } else {
            return sql;
        }
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

}
