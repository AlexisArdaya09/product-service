package dev.alexisardaya.productservice.exception;

public class DuplicateResourceException extends RuntimeException {

  public DuplicateResourceException(String message) {
    super(message);
  }
}

