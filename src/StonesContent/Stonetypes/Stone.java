package StonesContent.Stonetypes;
import StonesContent.Sorting.*;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Stone {
    String name;
    double price, weight, transparency;

    public enum StoneType{
        PREC,
        SEMI
    }
    StoneType type;

    public enum rangeSorting{
        ASC,
        DESC
    }

    public static ArrayList<Stone> sortStones(ArrayList<Stone> stones, rangeSorting type, Logger logger) {

        logger.fine("Створюємо ArrayList, де відсортуємо наш оригінальний список. " +
                "Типи сортування не можливо вибрати таким чином, щоб програма аварійно завершилася.");

        ArrayList<Stone> sortedArray = new ArrayList<>(stones);
        int N = sortedArray.size();
        if (type == rangeSorting.ASC) {
            sortedArray.sort(new AscSorting());
        }else {
            System.out.println("Сортуємо за спаданням");
            sortedArray.sort(new DescSorting());
        }
        while (N-- != 0) {
            System.out.print(sortedArray.get(N));
        }
        return sortedArray;
    }

    public static ArrayList<Stone> filterStones(ArrayList<Stone> list, Logger logger, double first, double second){

        logger.fine("Фільтруємо наші камені за показником прозорості.");
        ArrayList<Stone> newArr = new ArrayList<>();

        for(Stone stone : list){
            if(stone.getTransparency() > first && stone.getTransparency() < second){
                newArr.add(stone);
            }
        }

        if(newArr.size() == 0){
            System.out.println("\nТаких каменів нема! :(");
        }

        return newArr;
    }

    public String getStoneName() { return name; }
    public StoneType getType() { return type; }
    public double getPrice(){return price;}
    public double getWeight(){return weight;}
    public double getTransparency(){return transparency;}

    @Override
    public String toString() {
        return    " Назва-: "+ name +
                ", Ціна-: " + price +
                ", Вага-: " + weight +
                ", Прозорість-: " + transparency
                + '\'' + '\n';
    }
}
