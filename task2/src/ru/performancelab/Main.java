package ru.performancelab;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //массив координат фигуры
        double[] inputRectX = new double[4];
        double[] inputRectY = new double[4];
        //список точек
        ArrayList<Double> inputX = new ArrayList<>();
        ArrayList<Double> inputY = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(args[0]);
            Scanner scan = new Scanner(fileReader);
            String arr[];
            int j = 0;
            while (scan.hasNextLine()) {
                arr = scan.nextLine().split(" ");
                inputRectX[j] = Double.parseDouble(arr[0]);
                inputRectY[j] = Double.parseDouble(arr[1]);
                j++;
            }
            fileReader.close();

            fileReader = new FileReader(args[1]);
            scan = new Scanner(fileReader);
            j = 0;
            while (scan.hasNextLine()) {
                arr = scan.nextLine().split(" ");
                inputX.add(Double.parseDouble(arr[0]));
                inputY.add(Double.parseDouble(arr[1]));
                j++;
            }
            fileReader.close();


            for (int i = 0; i < inputX.size(); i++) {
                double x = inputX.get(i);
                double y = inputY.get(i);
/// в вершинах, проверка точно по координатам
                if ((x == inputRectX[0] && y == inputRectY[0]) ||
                        (x == inputRectX[1] && y == inputRectY[1]) ||
                        (x == inputRectX[2] && y == inputRectY[2]) ||
                        (x == inputRectX[3] && y == inputRectY[3])) {
                    System.out.println("[" + x + "," + y + "]-0 на вершине");
                } else {
//на стороне, проверка через уравнение прямой
                    if ((x - inputRectX[0]) / (inputRectX[1] - inputRectX[0]) == (y - inputRectY[0]) / (inputRectY[1] - inputRectY[0]) ||
                            (x - inputRectX[1]) / (inputRectX[2] - inputRectX[1]) == (y - inputRectY[1]) / (inputRectY[2] - inputRectY[1]) ||
                            (x - inputRectX[3]) / (inputRectX[2] - inputRectX[3]) == (y - inputRectY[3]) / (inputRectY[2] - inputRectY[3]) ||
                            (x - inputRectX[0]) / (inputRectX[3] - inputRectX[0]) == (y - inputRectY[0]) / (inputRectY[3] - inputRectY[0]) ||
                            (x == inputRectX[0] && x == inputRectX[1] && y > inputRectY[0] && y < inputRectY[1]) ||
                            (x == inputRectX[2] && x == inputRectX[3] && y > inputRectY[3] && y < inputRectY[2]) ||
                            (y == inputRectY[0] && y == inputRectY[1] && x > inputRectX[0] && x < inputRectX[1]) ||
                            (y == inputRectY[2] && y == inputRectY[3] && x > inputRectX[3] && x < inputRectX[2])) {
                        System.out.println("[" + x + "," + y + "]-1 на стороне");
                    } else {
//внутри, сначала разделим прямоугольник на два треугольника и посмотрим не попала ли точка на вновь образованную сторону
                        if ((x - inputRectX[0]) / (inputRectX[2] - inputRectX[0]) == (y - inputRectY[0]) / (inputRectY[2] - inputRectY[0])) {
                            System.out.println("[" + x + "," + y + "]-2 внутри");
                        } else {
//тогда смотрим попала ли точка в один из треугольник
//использую точку делим треугольник на три треугольника и сравниваем сумму площадей одного большого с тремя маленькими
                            if (square(inputRectX[0], inputRectY[0], inputRectX[1], inputRectY[1], inputRectX[2], inputRectY[2]) ==
                                square(inputRectX[0], inputRectY[0], x, y, inputRectX[2], inputRectY[2]) +
                                square(inputRectX[0], inputRectY[0], inputRectX[1], inputRectY[1], x, y) +
                                square(x, y, inputRectX[1], inputRectY[1], inputRectX[2], inputRectY[2]) ||
// то же для второго треугольника
                                square(inputRectX[0], inputRectY[0], inputRectX[2], inputRectY[2], inputRectX[3], inputRectY[3]) ==
                                square(inputRectX[0], inputRectY[0], x, y, inputRectX[3], inputRectY[3]) +
                                square(inputRectX[0], inputRectY[0], inputRectX[2], inputRectY[2], x, y) +
                                square(x, y, inputRectX[2], inputRectY[2], inputRectX[3], inputRectY[3])
                            ) {
                                System.out.println("[" + x + "," + y + "]-2 внутри");
//иначе точка снаружи
                            } else {
                                System.out.println("[" + x + "," + y + "]-3 снаружи");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Площадь треугольника — половина векторного произведения двух его сторон, взятая по модулю
    public static double square(double x0, double y0, double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0), 2)) / 2;
    }
}







