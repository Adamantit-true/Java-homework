package semester1.practice04.homework;

import java.util.Scanner;

public class Task2DigitAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== АНАЛІЗАТОР РОЗРЯДІВ ЧИСЛА ===");
        System.out.print("Введіть додатне ціле число: ");
        int number = scanner.nextInt();

        int originalNumber = number;
        int temp = number;

        int digitCount = 0;
        int digitSum = 0;
        int maxDigit = 0;
        int reversedNumber = 0;

        while (temp > 0) {
            int digit = temp % 10;

            digitCount++;
            digitSum += digit;
            if (digit > maxDigit) {
                maxDigit = digit;
            }
            reversedNumber = reversedNumber * 10 + digit;

            temp /= 10;
        }

        System.out.println();
        System.out.println("--- СТАТИСТИКА ЧИСЛА ---");
        System.out.println("Початкове число:     " + originalNumber);
        System.out.println("Кількість цифр:      " + digitCount);
        System.out.println("Сума цифр:           " + digitSum);
        System.out.println("Найбільша цифра:     " + maxDigit);
        System.out.println("Перевернуте число:   " + reversedNumber);

        if (originalNumber == reversedNumber) {
            System.out.println("Результат:           ✨ Це число є ПАЛІНДРОМОМ!");
        } else {
            System.out.println("Результат:           Це звичайне число (не паліндром).");
        }
        System.out.println("================================");

        scanner.close();
    }
}