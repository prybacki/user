package com.rybacki.user.domain.ports.in;

import com.rybacki.user.domain.model.Login;
import com.rybacki.user.domain.model.User;

public interface Users {
  User get(Login login) throws ArithmeticException;
}
