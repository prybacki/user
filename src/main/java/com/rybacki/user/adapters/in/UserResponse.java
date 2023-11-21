package com.rybacki.user.adapters.in;

import com.rybacki.user.domain.model.User;
import java.time.ZonedDateTime;

public record UserResponse(
    long id,
    String login,
    String name,
    String type,
    String avatarUrl,
    ZonedDateTime createdAt,
    double calculations) {
  public UserResponse(User user) {
    this(
        user.id(),
        user.login(),
        user.name(),
        user.type(),
        user.avatarUrl(),
        user.createdAt(),
        user.calculations().doubleValue()
    );
  }
}