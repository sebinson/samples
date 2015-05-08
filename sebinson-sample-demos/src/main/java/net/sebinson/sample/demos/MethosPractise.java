package net.sebinson.sample.demos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

class ConstructionMethodTest {
    int m;

    ConstructionMethodTest(int i) {
        m = i;
    }
}

public class MethosPractise {

    public static void printGrade(double grade) {
        if (grade >= 90) {
            System.out.println("A");
        } else if (grade >= 80) {
            System.out.println("B");
        } else if (grade >= 70) {
            System.out.println("C");
        } else if (grade >= 60) {
            System.out.println("D");
        } else {
            System.out.println("F");
        }
    }

    public static int max(int num1, int num2) {
        int result;
        if (num1 > num2)
            result = num1;
        else
            result = num2;

        return result;
    }

    public static double max(double num1, double num2) {
        double result;
        if (num1 > num2)
            result = num1;
        else
            result = num2;

        return result;
    }

    public static void main(String args[]) {
        System.out.println("重载练习：the max is :" + max(15.6, 14.6));
        //方法调用
        Scanner sc = new Scanner(System.in);
        System.out.println("方法调用练习.please input your score:");
        double score = sc.nextInt();
        printGrade(score);

        //构造方法
        ConstructionMethodTest constru = new ConstructionMethodTest(10);
        System.out.println("构造方法：" + constru.m);

        //读取控制台输入,以下执行有问题
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("please input your another score:");
            double anotherscore = Double.valueOf(br.readLine()).doubleValue();
            printGrade(anotherscore);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
