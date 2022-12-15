package WorkDB;

import StonesContent.Necklace;
import StonesContent.Stonetypes.Stone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static message.SendEmail.sendMessage;

public class DBOper {
    static String userName = "sa";
    static String password = "T91pOc78Jojo";
    static String connectionURL = "jdbc:mysql://localhost:3306/jewelryapp";

    public static void writeToDB(Necklace Content, Logger logger) throws Exception {

        try (Connection conn = DriverManager.getConnection(connectionURL, userName, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = conn.createStatement();
            st.executeUpdate("Truncate table necklace");

            ArrayList<Stone> stones = Content.getStones();

            for(int i = 0; i < stones.size(); i++){
                String insertSql = "Insert into necklace Values ('"
                        + stones.get(i).getType().toString() + "', '"
                        + stones.get(i).getStoneName() + "', "
                        + stones.get(i).getPrice() + ", "
                        + stones.get(i).getWeight() + ", "
                        + stones.get(i).getTransparency() + ")";

                st.executeUpdate(insertSql);
            }
            logger.fine("Запис пройшов успішно");

        }catch (Exception e){
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + e);
            e.printStackTrace();
        }
    }

    public static Necklace readFromDB(Logger logger) throws Exception {
        Necklace content = new Necklace("");
        try (Connection conn = DriverManager.getConnection(connectionURL, userName, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from necklace");

            String type = null, name = null;
            double price, weight, trancparency;

            System.out.println("Elements, which was read from db: \n");
            while(resultSet.next()){
                System.out.println("\tТип каменя: " + resultSet.getString("stType")
                        + "\n\tНазва каменя: " + resultSet.getString("stName")
                        + "\n\tЦіна каменя: " + resultSet.getDouble("stPrice")
                        + "\n\tВага каменя: " + resultSet.getDouble("stWeight")
                        +"\n\tПрозорість каменя: " + resultSet.getDouble("stTrans")  + "\n");
                type = resultSet.getString("stType");
                name = resultSet.getString("stName");
                price = Double.parseDouble(resultSet.getString("stPrice"));
                weight = Double.parseDouble(resultSet.getString("stWeight"));
                trancparency = Double.parseDouble(resultSet.getString("stTrans").trim());

                content.addStone(Stone.StoneType.valueOf(type),name,price,weight, trancparency);
            }
            logger.fine("Зчитування пройшло успішно");
        }catch (Exception e){
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + e);
            e.printStackTrace();
        }
        return content;
    }
}
