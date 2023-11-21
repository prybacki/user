package com.rybacki.user.domain.ports.out;

import com.rybacki.user.domain.model.Login;

public interface UserRepository {
  void saveOrUpdate(Login login);
}
