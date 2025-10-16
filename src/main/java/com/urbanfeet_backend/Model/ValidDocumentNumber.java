package com.urbanfeet_backend.Model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentNumberValidator.class)
public @interface ValidDocumentNumber {
  String message() default "El número de documento no coincide con el tipo seleccionado";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
