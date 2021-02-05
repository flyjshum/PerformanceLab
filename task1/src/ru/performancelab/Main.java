package ru.performancelab;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException {
        ArrayList<Short> input = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(args[0]);
            Scanner scan = new Scanner(fileReader);
            while (scan.hasNextLine()) {
                input.add(Short.parseShort(scan.nextLine()));
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //если ошибок нет и есть входной набор данных, то выполняем дальнейшие вычисления
        if (input.size() != 0) {

            //пеербираем набор ищем мин, макс и среднее
            int sum = 0;
            short max = input.get(0);
            short min = input.get(0);

            for (short item : input) {
                sum += item;
                if (item > max) {
                    max = item;
                }
                if (item < min) {
                    min = item;
                }
            }
            double avg = (double) sum / input.size();

            //сортируем для поиска медианы и 90 перцентиль
            Collections.sort(input);

            //если нечетное количество в наборе данных, то медиана значение по середине набора,
            //если четное - то сумма двух средних, разделенная пополам
            double med;
            if (input.size() % 2 == 1) {
                med = input.get(input.size() / 2);
            } else {
                med = (input.get((input.size() / 2) - 1) + input.get(input.size() / 2)) / 2.0;

            }

            double perc90;
            //вычесляем порядковый номер в наборе данных
            double n = ((90 * ((input.size() - 1) / 100.0)) + 1);

            //получаем дробную часть (с точностью до сотых) для расчета перцентиля с использованием линейной интерполяции
            DecimalFormat f = new DecimalFormat("0.00");
            double x = n - (int) n;
            x = (double) f.parse(f.format(x));
            perc90 = input.get((int) n - 1) + x * (input.get((int) n) - input.get((int) n - 1));

            System.out.println(f.format(perc90));
            System.out.println(f.format(med));
            System.out.println(f.format(max));
            System.out.println(f.format(min));
            System.out.println(f.format(avg));
        }
    }
}
