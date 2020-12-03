package fr.univtln.bruno.demos.jaxrs.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@Getter  @Setter
public class Person {
    public Person() { }

    @EqualsAndHashCode.Include
    int id;

    String name;

    String email;
}
