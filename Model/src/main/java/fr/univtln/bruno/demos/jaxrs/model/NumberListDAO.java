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

import lombok.NoArgsConstructor;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.time.LocalTime;

/**
 * The type Number list dao.
 */
@NoArgsConstructor(staticName = "of")
public class NumberListDAO {
    //A Int list Model
    private static final MutableIntList intList = new IntArrayList();
    private static LocalTime changeTime;

    static {
        updateChangeTime();
    }

    private static void updateChangeTime() {
        changeTime = LocalTime.now();
    }

    /**
     * Add boolean.
     *
     * @param i the
     * @return the boolean
     */
    public boolean add(int i) {
        updateChangeTime();
        return intList.asSynchronized().add(i);
    }

    /**
     * Clear.
     */
    public void clear() {
        updateChangeTime();
        intList.asSynchronized().clear();
    }

    /**
     * Get int.
     *
     * @param i the
     * @return the int
     */
    public int get(int i) {
        return intList.asSynchronized().get(i);
    }

    /**
     * Get int [ ].
     *
     * @return the int [ ]
     */
    public int[] get() {
        return intList.asSynchronized().toArray(new int[intList.size()]);
    }

    /**
     * Gets change time.
     *
     * @return the change time
     */
    public LocalTime getChangeTime() {
        return changeTime;
    }

    /**
     * Multiply int [ ].
     *
     * @param factor the factor
     * @return the int [ ]
     */
    public int[] multiply(int factor) {
        MutableIntList resultList = new IntArrayList();
        intList.asSynchronized().collectInt(x -> factor * x, resultList);
        return intList.toArray(new int[resultList.size()]);
    }
}
