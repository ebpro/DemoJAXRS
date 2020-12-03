package fr.univtln.bruno.demos.jaxrs.model;

import lombok.NoArgsConstructor;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.time.LocalTime;

@NoArgsConstructor(staticName = "of")
public class NumberListDAO {
    //A Int list Model
    private static MutableIntList intList = new IntArrayList();
    private static LocalTime changeTime;

    static {
        updateChangeTime();
    }

    private static void updateChangeTime() {
        changeTime = LocalTime.now();
    }

    public boolean add(int i) {
        updateChangeTime();
        return intList.asSynchronized().add(i);
    }

    public void clear() {
        updateChangeTime();
        intList.asSynchronized().clear();
    }

    public int get(int i) {
        return intList.asSynchronized().get(i);
    }

    public int[] get() {
        return intList.asSynchronized().toArray(new int[intList.size()]);
    }

    public LocalTime getChangeTime() {
        return changeTime;
    }

    public int[] multiply(int factor) {
        MutableIntList resultList = new IntArrayList();
        intList.asSynchronized().collectInt(x -> factor * x, resultList);
        return (intList = resultList).toArray(new int[resultList.size()]);
    }
}
