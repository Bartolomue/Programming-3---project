import javafx.util.Pair;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by bartek on 1/21/17.
 */
public class UnorderedPair<T> implements Serializable{
    public final Set<T> set = new LinkedHashSet<T>();

    public UnorderedPair(T a, T b) {
        set.add(a);
        if (!set.add(b)) {
            //throw new IllegalArgumentException();
        }
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public boolean equals(Object b) {
        if (b == this) {
            return true;
        }
        if (!(b instanceof UnorderedPair<?>)) {
            return false;
        }
        return set.equals(((UnorderedPair<?>) b).set);
    }
}
