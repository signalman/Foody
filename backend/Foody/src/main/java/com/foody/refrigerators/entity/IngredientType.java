package com.foody.refrigerators.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientType {
   INITIAL(0),
   CUSTOM(1);

   private final int typeLevel;
}
