package com.rybacki.user.adapters.in;

import com.rybacki.user.domain.UserService;
import com.rybacki.user.domain.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{login}")
  public UserResponse getUser(@PathVariable("login") String login) {
    return new UserResponse(userService.get(new Login(login)));
  }

  @ExceptionHandler({ArithmeticException.class})
  public ResponseEntity<Error> handleException(ArithmeticException exception) {
    log.info("Exception occurs", exception);
    return new ResponseEntity<>(new Error("Cannot perform calculation for given login"),
        new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Error> handleException(IllegalArgumentException exception) {
    log.info("Exception occurs", exception);
    return new ResponseEntity<>(new Error( exception.getMessage()),
        new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({IllegalStateException.class})
  public ResponseEntity<Error> handleException(IllegalStateException exception) {
    log.info("Exception occurs", exception);
    return new ResponseEntity<>(new Error( exception.getMessage()),
        new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Error> handleException(Exception exception) {
    log.info("Exception occurs", exception);
    return new ResponseEntity<>(new Error("Internal Server Exception"),
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
