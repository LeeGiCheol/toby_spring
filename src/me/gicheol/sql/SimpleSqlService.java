package me.gicheol.sql;

import me.gicheol.exception.SqlRetrievalFailureException;

import java.util.Map;

public class SimpleSqlService implements SqlService {

    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }


    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);

        if (sql == null) {
            throw new SqlRetrievalFailureException("SQL을 찾을 수 없습니다.");
        } else {
            return sql;
        }
    }

}
