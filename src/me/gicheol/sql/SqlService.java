package me.gicheol.sql;

import me.gicheol.exception.SqlRetrievalFailureException;

public interface SqlService {

    String getSql(String key) throws SqlRetrievalFailureException;

}
