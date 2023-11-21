package com.rybacki.user.domain.ports.out;

import com.rybacki.user.domain.model.Login;
import com.rybacki.user.domain.model.User;

public interface UserProvider {
  User get(Login login);
}
