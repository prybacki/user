package com.rybacki.user.domain;

import com.rybacki.user.domain.model.Login;
import com.rybacki.user.domain.model.User;
import com.rybacki.user.domain.ports.in.Users;
import com.rybacki.user.domain.ports.out.UserRepository;
import com.rybacki.user.domain.ports.out.UserProvider;
import java.math.BigDecimal;

public class UserService implements Users {

  private final UserProvider userProvider;
  private final UserRepository userRepository;
  private final CalculationAlgorithm calculationAlgorithm;

  public UserService(UserProvider userProvider, UserRepository userRepository,
      CalculationAlgorithm calculationAlgorithm) {
    this.userProvider = userProvider;
    this.userRepository = userRepository;
    this.calculationAlgorithm = calculationAlgorithm;
  }

  @Override
  public User get(Login login) {
    userRepository.saveOrUpdate(login);
    User user = userProvider.get(login);
    BigDecimal calculations = calculationAlgorithm.calculate(user.followers(), user.repos());
    return new User(user.id(), user.login(), user.name(), user.type(), user.avatarUrl(),
        user.createdAt(), calculations);
  }
}
