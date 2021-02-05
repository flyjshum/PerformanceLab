package ru.performancelab;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // 8:00 - 1 элемент 1-я минута
        // 8:01 - 2 элемент 2-я минута
        //и т.д. каждый элемент масива это минута рабочего времени 720 минут это рабочий день с 8-20.
        int arrVisitor[] = new int[720];

//заполняем массив значениями из файла
        try {
            FileReader fileReader = new FileReader(args[0]);
            Scanner scan = new Scanner(fileReader);
            while (scan.hasNextLine()) {
                String arrTime[] = scan.nextLine().split(" ");
                String arrBegin[] = arrTime[0].split(":");
                int beginH = Integer.parseInt(arrBegin[0]);
                int beginM = Integer.parseInt(arrBegin[1]);
                String arrEnd[] = arrTime[1].split(":");
                int endH = Integer.parseInt(arrEnd[0]);
                int endM = Integer.parseInt(arrEnd[1]);
                int posBegin = (beginH - 8) * 60 + beginM;
                int posEnd = (endH - 8) * 60 + endM - 1;
                for (int i = posBegin; i <= posEnd; i++)
                    arrVisitor[i]++;
            }
            fileReader.close();

            //находи максимальное количество посетителей
            int maxVisitor = 0;
            for (int i = 0; i < 720; i++) {
                if (arrVisitor[i] > maxVisitor)
                    maxVisitor = arrVisitor[i];
            }

            //смотрим время начала и конца максимального количества посетителей и формируем строку вывода. в минуты добавляем  ведущии нули
            int i = 0;
            while (i < 720) {
                int timeBegin = 0;
                int timeEnd = 0;
                if (arrVisitor[i] == maxVisitor) {
                    timeBegin = 8 * 60 + i;
                    while (arrVisitor[i] == maxVisitor) {
                        i++;
                    }
                    timeEnd = 8 * 60 + i;
                    int beginH = timeBegin / 60;
                    int beginM = timeBegin - beginH * 60;
                    int endH = timeEnd / 60;
                    int endM = timeEnd - endH * 60;

                    System.out.println(beginH + ":" + addZero(beginM) + " " + endH + ":" + addZero(endM));
                }
                i++;
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }

    public static String addZero(int number) {
        if (number >= 0 && number <= 9)
            return "0" + String.valueOf(number);
        else return String.valueOf(number);
    }
}
