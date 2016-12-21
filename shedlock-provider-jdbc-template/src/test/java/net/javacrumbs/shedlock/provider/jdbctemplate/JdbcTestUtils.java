/**
 * Copyright 2009-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.shedlock.provider.jdbctemplate;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

class JdbcTestUtils {

    private final HikariDataSource datasource;
    private final JdbcTemplate jdbcTemplate;

    JdbcTestUtils(DbConfig dbConfig) {
        datasource = new HikariDataSource();
        datasource.setJdbcUrl(dbConfig.getJdbcUrl());
        datasource.setUsername(dbConfig.getUsername());
        datasource.setPassword(dbConfig.getPassword());

        jdbcTemplate = new JdbcTemplate(datasource);
        jdbcTemplate.execute("CREATE TABLE shedlock(name VARCHAR(64), lock_until TIMESTAMP, locked_at TIMESTAMP, locked_by  VARCHAR(255), PRIMARY KEY (name))");
    }

    void clean() {
        jdbcTemplate.execute("DROP TABLE shedlock");
        datasource.close();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public DataSource getDatasource() {
        return datasource;
    }
}
