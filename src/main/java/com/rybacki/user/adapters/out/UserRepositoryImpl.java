package com.rybacki.user.adapters.out;

import com.rybacki.user.domain.ports.out.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

  @Override
  public void incrementCounter(String login) {

  }
}
