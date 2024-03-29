package fr.univtln.bruno.demos.jaxrs.model;

/*-
 * #%L
 * JAXRS Model
 * %%
 * Copyright (C) 2020 - 2021 Université de Toulon
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;

import java.util.List;


/**
 * The type Person model.
 */
public class PersonModel {
    private static final MutableIntObjectMap<Person> people = new IntObjectHashMap<>();
    private static int lastId = 0;

    static {
        people.put(1, Person.builder().id(1).name("Pierre").email("pierre@ici.fr").build());
        people.put(2, Person.builder().id(2).name("Jacques").email("jacques@ici.fr").build());
    }

    private PersonModel() {
    }

    /**
     * Put person.
     *
     * @param person the person
     * @return the person
     */
    public static Person put(Person person) {
        person.setId(++lastId);
        return people.put(lastId, person);
    }

    /**
     * Get person.
     *
     * @param i the
     * @return the person
     */
    public static Person get(int i) {
        return people.get(i);
    }

    /**
     * Get list.
     *
     * @return the list
     */
    public static List<Person> get() {
        return people.toList();
    }
}
