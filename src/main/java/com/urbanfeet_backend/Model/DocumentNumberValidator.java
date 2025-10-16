package com.urbanfeet_backend.Model;

import com.urbanfeet_backend.Entity.User;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentNumberValidator implements ConstraintValidator<ValidDocumentNumber, User> {

  @Override
  public boolean isValid(User user, ConstraintValidatorContext ctx) {
    if (user == null)
      return true;
    if (user.getDocumentType() == null || user.getDocumentNumber() == null)
      return true;

    String number = user.getDocumentNumber();

    switch (user.getDocumentType()) {
      case DNI:
        // Exactamente 8 dígitos
        return number.matches("\\d{8}");
      default:
        return false; // no debería ocurrir porque el enum solo tiene 2 valores
    }
  }
}
