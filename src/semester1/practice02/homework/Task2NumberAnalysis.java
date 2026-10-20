package semester1.practice02.homework;

public class Task2NumberAnalysis {
    public static void main(String[] args) {

        int number = 742;
        int hundreds = number / 100;
        int tens = (number / 10) % 10;
        int units = number % 10;
        int sumNum = hundreds + tens + units;
        int prodNum = hundreds * tens * units;
        int reversedNumber = units * 100 + tens * 10 + hundreds;
        boolean isSumEven = sumNum % 2 == 0;

        System.out.println("Початкове число:            "+number);
        System.out.print("Разряди:                    Сотні="+hundreds);
        System.out.print(", Десятки = " +tens);
        System.out.println(", Одиниці = " +units);
        System.out.println("Сума цифр:                  "+sumNum);
        System.out.println("Добуток цифр:               "+prodNum);
        System.out.println("Перевернуте число:          "+reversedNumber);
        System.out.println("Чи є сума цифр парною?      "+isSumEven);

    }
}
