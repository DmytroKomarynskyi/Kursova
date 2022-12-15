package StonesContent.Sorting;

import StonesContent.Stonetypes.Stone;
import java.util.Comparator;

public class AscSorting implements Comparator<Stone> {
    @Override
    public int compare(Stone a, Stone b){
        return (int)Math.ceil(a.getPrice() - b.getPrice());
    }
}
