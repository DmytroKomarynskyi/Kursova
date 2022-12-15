package StonesContent.Stonetypes;

public class SemiPreciousStone extends Stone {
    final StoneType type = StoneType.SEMI;
    public SemiPreciousStone(String Name, double Price,
                             double Weight, double Transparency) {
        name = Name;
        price = Price;
        weight = Weight;
        transparency = Transparency;
    }
    @Override
    public StoneType getType() { return type; }
}