package com.rybacki.user.adapters.out;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static reactor.core.publisher.Mono.*;

import com.rybacki.user.domain.model.Login;
import com.rybacki.user.domain.model.User;
import com.rybacki.user.domain.ports.out.UserProvider;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExternalUserProvider implements UserProvider {

  private final WebClient client;

  ExternalUserProvider(@Value("${user.url}") String baseUrl) {
    this.client = WebClient.create(baseUrl);
  }

  @Override
  public User get(Login login) {
    UserResponse user = client
        .method(HttpMethod.GET)
        .uri("/{login}", login.value())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(status -> NOT_FOUND == status,
            clientResponse -> error(new IllegalArgumentException("User does not exist")))
        .bodyToMono(UserResponse.class)
        .onErrorMap(Predicate.not(IllegalArgumentException.class::isInstance), otherException -> new IllegalStateException("Problem with retrieve user"))
        .block();
    return new User(user.id(), user.login(), user.name(), user.type(), user.avatarUrl(),
        user.createdAt(), user.followers(), user.repos());
  }
}