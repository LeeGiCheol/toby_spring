package me.gicheol.annotation;

import me.gicheol.config.SqlServiceContext;
import org.springframework.context.annotation.Import;

@Import(value = SqlServiceContext.class)
public @interface EnableSqlService {
}
