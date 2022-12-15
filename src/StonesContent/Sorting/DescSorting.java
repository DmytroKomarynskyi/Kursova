package StonesContent.Sorting;

import StonesContent.Stonetypes.Stone;
import java.util.Comparator;

public class DescSorting implements Comparator<Stone> {
    Stone stone;
    @Override
    public int compare(Stone a, Stone b){
        return (int)Math.ceil(b.getPrice() - a.getPrice());
    }
}
