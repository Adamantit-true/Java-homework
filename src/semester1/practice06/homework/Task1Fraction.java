package semester1.practice06.homework;

public class Task1Fraction {
    public static void main(String[] args) {
        System.out.println("=== КАЛЬКУЛЯТОР ЗВИЧАЙНИХ ДРОБІВ ===");

        Fraction fraction1 = new Fraction();
        fraction1.setValues(8, 12);

        System.out.print("Дріб 1: ");
        fraction1.printFraction();

        fraction1.simplify();
        System.out.print("Після скорочення: ");
        fraction1.printFraction();
        System.out.println("(десятковий вигляд: " + fraction1.toDecimal() + ")");
        System.out.println();

        System.out.println("Додаємо дріб 3/4 до 2/3...");
        System.out.println("Формула: 2/3 + 3/4 = (2*4 + 3*3) / (3*4) = 17/12");
        fraction1.add(3, 4);

        System.out.print("Результат додавання: ");
        fraction1.printFraction();
        System.out.println("(десятковий вигляд: " + fraction1.toDecimal() + ")");
        System.out.println("====================================");
    }
}

class Fraction {
    private int numerator;
    private int denominator;

    public void setValues(int num, int den) {
        if (den == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0!");
            numerator = num;
            denominator = 1;
        } else if (den < 0) {
            numerator = -num;
            denominator = -den;
        } else {
            numerator = num;
            denominator = den;
        }
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public void simplify() {
        int commonDivisor = gcd(numerator, denominator);
        numerator = numerator / commonDivisor;
        denominator = denominator / commonDivisor;
    }

    public double toDecimal() {
        return (double) numerator / denominator;
    }

    public void add(int otherNum, int otherDen) {
        numerator = numerator * otherDen + otherNum * denominator;
        denominator = denominator * otherDen;
        simplify();
    }

    public void printFraction() {
        if (denominator == 1) {
            System.out.println(numerator);
        } else {
            System.out.println(numerator + "/" + denominator);
        }
    }
}