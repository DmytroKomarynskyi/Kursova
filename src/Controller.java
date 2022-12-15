import StonesContent.Necklace;
import StonesContent.Stonetypes.Stone;
import WorkDB.DBOper;
import filework.WorkFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static message.SendEmail.sendMessage;

public class Controller implements Initializable {
    @FXML
    private Label TotalStonePrice;
    @FXML
    private Label TotalStoneWeight;
    @FXML
    private AnchorPane insertNode, deleteNode, filterNode;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField stoneName, Price, Weight, Trans;
    @FXML
    private TextField Index, First, Second;
    @FXML
    private Label stonePrice, stoneWeight, stoneTrance, delStone;
    @FXML
    private ListView<String> listOfStones;
    @FXML
    private Label necklaceName;

    private String[] types = {"PREC","SEMI"};

    private String type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Stone> stones = Necklace.getStones();
        for (Stone stone : stones) {
            listOfStones.getItems().addAll(stone.toString());
        }
        choiceBox.getItems().addAll(types);
        choiceBox.setOnAction(this::getStoneType);
    }

    public void getStoneType(javafx.event.ActionEvent e){
        type = choiceBox.getValue();
    }

    public void UpdateList(){
        ArrayList<Stone> stones = Necklace.getStones();
        listOfStones.getItems().clear();
        for (Stone stone : stones) {
            listOfStones.getItems().addAll(stone.toString());
        }
    }
    public void UpdateData(){
        double TotalPrice = Necklace.getTotalPrice(Necklace.getStones(),Main.logger);
        TotalStonePrice.setText("Total Stone Price: " + TotalPrice);
        Necklace.setTotalPrice(TotalPrice);

        double TotalWeight = Necklace.getTotalWeight(Necklace.getStones(),Main.logger);
        TotalStoneWeight.setText("Total Stone Weight: " + TotalWeight);
        Necklace.setTotalWeight(TotalWeight);
    }
    public void SortByAsc(javafx.event.ActionEvent e){
        Necklace.setStones(Stone.sortStones(Necklace.getStones(), Stone.rangeSorting.ASC, Main.logger));
        UpdateList();
    }

    public void SortByDesc(javafx.event.ActionEvent e){
        Necklace.setStones(Stone.sortStones(Necklace.getStones(), Stone.rangeSorting.DESC, Main.logger));
        UpdateList();
    }

    public void Insert(javafx.event.ActionEvent e){
        insertNode.setVisible(true);
        insertNode.setDisable(false);
    }

    public void Delete(javafx.event.ActionEvent e){
        deleteNode.setVisible(true);
        deleteNode.setDisable(false);
    }

    public void Filter(javafx.event.ActionEvent e){
        filterNode.setVisible(true);
        filterNode.setDisable(false);
    }

    public void add(javafx.event.ActionEvent e) throws Exception {

        if(type == null)
            type = "PREC";

        Main.logger.info("Вводимо назву каменя");
        String name = stoneName.getText();

        if(name.equals(""))
            name = "DIAMOND";

        Main.logger.info("Вводимо данні про камінь");
        double price, weight, trancparency;

        try {
            price = Double.parseDouble(Price.getText());
            weight = Double.parseDouble(Weight.getText());
            trancparency = Double.parseDouble(Trans.getText());
        }
        catch (NumberFormatException numb) {
            price = 100;
            weight = 0.2;
            trancparency = 0.1;
        }

        Necklace.addStone(Stone.StoneType.valueOf(type), name, price, weight, trancparency);
        UpdateData();
        UpdateList();

        insertNode.setVisible(false);
        insertNode.setDisable(true);
    }

    public void delete(javafx.event.ActionEvent e) throws Exception {

        Main.logger.info("Видаляємо камінь");
        int index = 0;

        try {
            index = Integer.parseInt(Index.getText());
        }
        catch (NumberFormatException numb) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",numb);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + numb);
            numb.printStackTrace();
        }
        Necklace.delStone(index);

        UpdateData();
        UpdateList();

        deleteNode.setVisible(false);
        deleteNode.setDisable(true);
    }

    public void filter(javafx.event.ActionEvent e) throws Exception {

        Main.logger.info("Вводимо діапазон сортування");
        double first = 0, second = 0;

        try {
            first = Double.parseDouble(First.getText());
            second = Double.parseDouble(Second.getText());
        }
        catch (NumberFormatException numb) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",numb);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + numb);
            numb.printStackTrace();
        }

        Necklace.setStones(Stone.filterStones(Necklace.getStones(), Main.logger, first, second));

        UpdateData();
        UpdateList();

        filterNode.setVisible(false);
        filterNode.setDisable(true);
    }

    public void CreateNecklace(javafx.event.ActionEvent e){
        Main.logger.finer("Створюємо намисто...");

        if(Objects.equals(Main.content.getNecklaceName(), "")) {

            Main.logger.finer("Вписуємо назву намиста");

            Main.content.setNecklaceName("NecklaceOne");
            necklaceName.setText("Necklace Name: NecklaceOne");
        }else {
            Main.logger.finer("Намисто вже існує");
        }
    }

    public void ReadFile(javafx.event.ActionEvent e) throws Exception {

        WorkFile fileRead = new WorkFile();
        Necklace.getStones().clear();

        try {
            Main.content = fileRead.FileReader("Necklace", Main.logger);
        } catch (Exception ex) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",ex);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + ex);
            ex.printStackTrace();
        }

        UpdateData();
        UpdateList();
    }

    public void WriteFile(javafx.event.ActionEvent e) throws Exception {
        new WorkFile().FileWiter("Necklace", Main.content, Main.logger);
    }

    public void WriteToDB(javafx.event.ActionEvent e) throws Exception {
        DBOper.writeToDB(Main.content, Main.logger);
    }

    public void ReadFromDB(javafx.event.ActionEvent e) throws Exception {
        Necklace.getStones().clear();

        try {
            Main.content = DBOper.readFromDB(Main.logger);
        } catch (Exception ex) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",ex);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + ex);
            ex.printStackTrace();
        }
        UpdateData();
        UpdateList();
    }
}
