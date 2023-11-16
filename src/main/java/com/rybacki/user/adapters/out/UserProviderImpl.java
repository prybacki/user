package com.rybacki.user.adapters.out;

import com.rybacki.user.domain.model.User;
import com.rybacki.user.domain.ports.out.UserProvider;
import org.springframework.stereotype.Component;

@Component
public class UserProviderImpl implements UserProvider {

  @Override
  public User get(String login) {
    return null;
  }
}
