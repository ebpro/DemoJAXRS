package fr.univtln.bruno.demos.persons;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;

import java.util.List;

public class PersonModel {
    private static MutableIntObjectMap<Person> people = new IntObjectHashMap<>();
    private static int lastId = 0;

    static {
        people.put(1, Person.builder().id(1).name("Pierre").email("pierre@ici.fr").build());
        people.put(2, Person.builder().id(2).name("Pierre").email("pierre@ici.fr").build());
    }

    public static Person put(Person person) {
        person.setId(++lastId);
        return people.put(lastId, person);
    }

    public static Person get(int i) {
        return people.get(i);
    }

    public static List<Person> get() {
        return people.toList();
    }
}
