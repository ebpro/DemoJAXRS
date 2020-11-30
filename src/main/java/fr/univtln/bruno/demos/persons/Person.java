package fr.univtln.bruno.demos.persons;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = "name,email")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Person {
    int id;

    String name;

    String email;
}
