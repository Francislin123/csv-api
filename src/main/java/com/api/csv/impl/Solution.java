package com.api.csv.impl;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Solution() {
    public static void main(String[] args) {
        System.out.println(oddOrEvenUsingMore(59));
    }

    static void fizzBuzz() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
        sc.close();
    }

    static void teste() {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) System.out.print("Fizz");
            if (i % 5 == 0) System.out.print("Buzz");
            else if (i % 3 != 0) System.out.print(i);
            System.out.println();
        }
    }

    static void teste2() {
        String regex = "\\b(\\w+)(\\s+\\1\\b)+";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt(scanner.nextLine());
        while (testCases > 0) {
            String input = scanner.nextLine();
            Matcher m = p.matcher(input);

            while (m.find()) {
                input = input.replaceAll(m.group(), m.group(1));
            }
            System.out.println(input);
        }
        scanner.close();
    }

    static void teste3() {

        final String regularExpression = "^[a-zA-Z]\\w{7,29}$";

        final Scanner scan = new Scanner(System.in);

        int n = Integer.parseInt(scan.nextLine());

        while (n-- != 0) {
            String userName = scan.nextLine();

            if (userName.matches(regularExpression)) {
                System.out.println("Valid");
            } else {
                System.out.println("Invalid");
            }
        }
    }

    static void teste4() {

        Pattern pattern = Pattern.compile("<([a-zA-Z0-9]+)[^>]*>(.*?)</\\1>");

        Scanner in = new Scanner(System.in);
        int testCases = Integer.parseInt(in.nextLine());

        while (testCases > 0) {

            String line = in.nextLine();

            Matcher matcher = pattern.matcher(line);

            boolean found = false;

            while (matcher.find()) {
                System.out.println(matcher.group(2));
                found = true;
            }

            if (!found) {
                System.out.println("None");
            }

            //Write your code here

            testCases--;
        }
    }

    static long fibo(int n) {
        if (n < 2) {
            return n;
        } else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }

    static int multiplicadorWhile(int a, int b) {
        int soma = 0;
        int i = 0;
        while (i < b) {
            soma += a;
            i++;
        }
        System.out.println(soma);
        return soma;
    }

    static int multiplicadorFor(int a, int b) {
        int resultado = 0;
        for (int i = 0; i < b; i++) {
            resultado += a;
        }
        System.out.println(resultado);
        return resultado;
    }

    static void extractContent() {

        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        while (n > 0) {

            String line = scanner.nextLine();
            Pattern pattern = Pattern.compile("<([^>]+)>([^<]+)</\\1>");
            Matcher matcher = pattern.matcher(line);
            boolean found = false;

            while (matcher.find()) {
                System.out.println(matcher.group(2));
                found = true;
            }

            if (!found) {
                System.out.println("None");
            }
        }
        scanner.close();
    }

    static String division(int numb) {
        if (((numb % 2) == 0)) {
            return "true";
        }
        return "false";
    }

    static String oddOrEven(int num) {
        if (num % 2 == 0) {
            return "par";
        }
        return "ímpar";
    }

    static String oddOrEvenUsingMore(int num) {
        if ((num + 1) / 2 * 2 == num) {
            return "O número é ímpar.";
        } else {
            return "O número é par.";
        }
    }
}