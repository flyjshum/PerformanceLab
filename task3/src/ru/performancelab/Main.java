package ru.performancelab;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //для каждой кассы создадим объект в котором будем  хранить имя файла кассы с данными и мапу (период-длина)
        class Cashbox {
            public String name;
            public Map<Byte, Double> queue;

            public Cashbox(String name, Map<Byte, Double> queue) {
                this.name = name;
                this.queue = queue;
            }
        }

        //путь до файла с учетом кроссплатформенности
        String fileSeparator = System.getProperty("file.separator");
        String folder = args[0];
        Path path = Paths.get(folder); // если каталог существует, то создаем объекты касс
        if (Files.exists(path)) {
            ArrayList<Cashbox> cashboxes = new ArrayList<>();
            cashboxes.add(new Cashbox("cash1.txt", new HashMap<>()));
            cashboxes.add(new Cashbox("cash2.txt", new HashMap<>()));
            cashboxes.add(new Cashbox("cash3.txt", new HashMap<>()));
            cashboxes.add(new Cashbox("cash4.txt", new HashMap<>()));
            cashboxes.add(new Cashbox("cash5.txt", new HashMap<>()));

            Map<Byte, Double> lenghtQueueInShop = new HashMap<>(); //мапа период-максимальная длина в кассы

            //перебираем все кассы и заполняем начальными значениями из файла
            for (Cashbox cashbox : cashboxes) {
                try {
                    FileReader fileReader = new FileReader(folder + fileSeparator + cashbox.name);
                    Scanner scan = new Scanner(fileReader);
                    byte i = 1;
                    while (scan.hasNextLine()) {
                        cashbox.queue.put(i, Double.parseDouble(scan.nextLine()));
                        i++;
                    }
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //заполняем мапу максимальной длинной очереди в каждом конкретном периоде
            //не все периоды могут быть в файле, заполняем существующие, не существующие добавляем
            for (Cashbox cashbox : cashboxes) {
                for (byte key : cashbox.queue.keySet()) {
                    if (lenghtQueueInShop.containsKey(key)) {
                        lenghtQueueInShop.replace(key, lenghtQueueInShop.get(key) + cashbox.queue.get(key));
                    } else {
                        lenghtQueueInShop.put(key, cashbox.queue.get(key));
                    }
                }
            }

            // поимск максимальной длины очереди
            double maxLength = 0;
            byte firstQueue = 0;
            for (Map.Entry<Byte, Double> entry : lenghtQueueInShop.entrySet())
                if (entry.getValue() > maxLength) {
                    maxLength = entry.getValue();
                    firstQueue = entry.getKey();
                }
            System.out.println(firstQueue);
        } else System.out.println("folder not found");
    }
}
