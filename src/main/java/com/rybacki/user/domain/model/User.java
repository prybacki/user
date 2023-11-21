package com.rybacki.user.domain.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record User(
    Long id,
    String login,
    String name,
    String type,
    String avatarUrl,
    ZonedDateTime createdAt,
    Long followers,
    Long repos,
    BigDecimal calculations) {

  public User(Long id,
      String login,
      String name,
      String type,
      String avatarUrl,
      ZonedDateTime createdAt,
      Long followers,
      Long repos) {
    this(id, login, name, type, avatarUrl, createdAt, followers, repos, null);
    if (followers == null || repos == null) {
      throw new IllegalArgumentException("Followers and repos cannot be null");
    }
  }

  public User(Long id,
      String login,
      String name,
      String type,
      String avatarUrl,
      ZonedDateTime createdAt,
      BigDecimal calculations) {
    this(id, login, name, type, avatarUrl, createdAt, null, null, calculations);
  }
}