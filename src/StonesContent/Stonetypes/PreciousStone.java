package StonesContent.Stonetypes;

public class PreciousStone extends Stone {
    final StoneType type = StoneType.PREC;
    public PreciousStone(String Name, double Price,
                         double Weight, double Transparency) {
        name = Name;
        price = Price;
        weight = Weight;
        transparency = Transparency;
    }
    @Override
    public StoneType getType() { return type; }
}