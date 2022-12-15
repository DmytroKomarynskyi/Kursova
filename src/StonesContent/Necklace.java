
package StonesContent;
import StonesContent.Stonetypes.PreciousStone;
import StonesContent.Stonetypes.SemiPreciousStone;
import StonesContent.Stonetypes.Stone;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Necklace{
    String NecklaceName;
    static double totalPrice = 0,  totalWeight = 0;
    static ArrayList<Stone> stones = new ArrayList<>();

    public Necklace(){
        NecklaceName = "";
    }

    public Necklace(String necklaceName){
        NecklaceName = necklaceName;
    }

    public String getNecklaceName(){ return NecklaceName;}
    public void setNecklaceName(String necklaceName){
        NecklaceName = necklaceName;
    }

    public static ArrayList<Stone> getStones() { return stones; }
    public static void setStones(ArrayList<Stone> stones) { Necklace.stones = stones; }

    public static void setTotalPrice(double newPrice) { totalPrice = newPrice; }
    public static void setTotalWeight(double newWeight) { totalWeight = newWeight; }

    public static void addStone(Stone.StoneType type, String Name, double Price,
                                double Weight, double Transparency) {
        if (type == Stone.StoneType.PREC)
            Necklace.getStones().add(new PreciousStone(Name, Price, Weight, Transparency));
        else
            Necklace.getStones().add(new SemiPreciousStone(Name, Price, Weight, Transparency));
    }

    public static void delStone(int i) {
        if (i > Necklace.getStones().size() - 1 || i < 0) {
            System.out.println(" НЕВІРНО ВВЕДЕНІ ДАННІ! \nСПРОБУЙТЕ ЩЕ РАЗ ");
        }else{
            Necklace.getStones().remove(i);
        }
    }

    public static double getTotalPrice(ArrayList<Stone> list, Logger logger) {
        logger.finer("Занулюємо загальну ціну каменів, для очищення від можливої фальшивої інформації" +
                " та знаходимо загальну ціну каменів у намисті.");
        totalPrice = 0;
        list.forEach((stone) -> totalPrice += stone.getPrice());

        System.out.println("\nЗагальна ціна: " + totalPrice);
        return totalPrice;
    }
    public static double getTotalWeight(ArrayList<Stone> list, Logger logger) {
        logger.finer("Занулюємо загальну вагу каменів, для очищення від можливої фальшивої інформації" +
                " та знаходимо загальну вагу каменів у намисті.");
        totalWeight = 0;
        list.forEach((stone) -> totalWeight += stone.getWeight());

        System.out.println("\nЗагальна вага: " + totalWeight + " карат");
        return totalWeight;
    }
}