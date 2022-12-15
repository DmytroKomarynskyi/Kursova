package filework;

import StonesContent.Necklace;
import StonesContent.Stonetypes.Stone;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static message.SendEmail.sendMessage;

public class WorkFile {
    PrintStream standart;
    PrintStream fileStream;

    public Necklace FileReader(String destination, Logger logger) throws Exception {
        Necklace content = new Necklace("");
        try {
            FileReader fr = new FileReader(destination);
            Scanner reader = new Scanner(fr);

            while (reader.hasNextLine()) {
                Stone.StoneType type = Stone.StoneType.valueOf(reader.nextLine());
                String name = reader.nextLine();
                double price = Double.parseDouble(reader.nextLine());
                double weight = Double.parseDouble(reader.nextLine());
                double transparency = Double.parseDouble(reader.nextLine());
                content.addStone(type, name, price, weight, transparency);
            }
            System.out.println("Файл успішно прочитиано! :)");
            fr.close();

        } catch (IOException e) {
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            sendMessage("Знайдено критичну помилку: " + e);
            System.out.println("The error occurred.\n");
            e.printStackTrace();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    public void FileWiter(String destination, Necklace content, Logger logger) throws Exception {
        standart = System.out;
        try {
            File myFile = new File(destination);

            if (myFile.createNewFile())
                System.out.println("Створено файл " + myFile.getName() + "\n");
            else {
                System.out.println("Такий файл уже існує.\n");
                myFile.delete();
                myFile.createNewFile();
            }

            System.setOut(fileStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(myFile))));

        } catch (IOException e) {
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            sendMessage("Знайдено критичну помилку: " + e);
            System.out.println("The error occurred.\n");
            e.printStackTrace();

        }

        ArrayList<Stone> stones = content.getStones();
        for (int i = 0; i < stones.size(); i++) {
            System.out.println(stones.get(i).getType());
            System.out.println(stones.get(i).getStoneName() +
                    "\n" + stones.get(i).getPrice()+
                    "\n" + stones.get(i).getWeight()+
                    "\n" + stones.get(i).getTransparency());
        }
        System.out.flush();
        System.setOut(standart);
    }
}
