package com.rybacki.user.adapters.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rybacki.user.domain.model.User;
import java.time.ZonedDateTime;

public record UserResponse(
    long id,
    String login,
    String name,
    String type,
    @JsonProperty("avatar_url") String avatarUrl,
    @JsonProperty("created_at") ZonedDateTime createdAt,
    long followers,
    @JsonProperty("public_repos") Long repos) {

  public UserResponse(User user) {
    this(
        user.id(),
        user.login(),
        user.name(),
        user.type(),
        user.avatarUrl(),
        user.createdAt(),
        user.followers(),
        user.repos()
    );
  }
}