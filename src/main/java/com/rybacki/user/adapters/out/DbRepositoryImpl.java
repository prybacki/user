package com.rybacki.user.adapters.out;

import com.rybacki.user.domain.model.Login;
import com.rybacki.user.domain.ports.out.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbRepositoryImpl implements UserRepository {

  private final JdbcTemplate jdbcTemplate;
  private final String updateSql;
  private final String insertSql;

  public DbRepositoryImpl(JdbcTemplate jdbcTemplate, @Value("${user.updateSql}") String updateSql,
      @Value("${user.insertSql}") String insertSql) {
    this.jdbcTemplate = jdbcTemplate;
    this.updateSql = updateSql;
    this.insertSql = insertSql;
  }

  @Override
  public void saveOrUpdate(Login login) {
    int numberOfUpdatedRecord = jdbcTemplate.update(updateSql, login.value());
    if (numberOfUpdatedRecord == 0) {
      jdbcTemplate.update(insertSql, login.value());
    }
  }
}
