package semester1.practice06.homework;

public class Task2QuadraticEquation {
    public static void main(String[] args) {
        System.out.println("=== РОЗВ'ЯЗУВАЧ КВАДРАТНИХ РІВНЯНЬ ===");

        QuadraticEquation eq = new QuadraticEquation();

        System.out.println("Тест 1:");
        eq.setCoefficients(1.0, -5.0, 6.0);
        eq.printSolution();
        System.out.println("---------------------------------------");

        System.out.println("Тест 2:");
        eq.setCoefficients(1.0, -4.0, 4.0);
        eq.printSolution();
        System.out.println("---------------------------------------");

        System.out.println("Тест 3:");
        eq.setCoefficients(1.0, 2.0, 5.0);
        eq.printSolution();
        System.out.println("=======================================");
    }
}

class QuadraticEquation {
    private double a;
    private double b;
    private double c;

    public void setCoefficients(double a, double b, double c) {
        if (a == 0) {
            System.out.println("Увага: коефіцієнт 'a' не може дорівнювати 0 (це лінійне рівняння)!");
            this.a = 1.0;
        } else {
            this.a = a;
        }
        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }

    public boolean hasRealRoots() {
        if (getDiscriminant() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public double getRoot1() {
        double d = getDiscriminant();
        if (d < 0) {
            return Double.NaN;
        }
        return (-b + Math.sqrt(d)) / (2 * a);
    }

    public double getRoot2() {
        double d = getDiscriminant();
        if (d < 0) {
            return Double.NaN;
        }
        return (-b - Math.sqrt(d)) / (2 * a);
    }

    public void printSolution() {
        String bStr = (b < 0) ? "(" + b + ")" : "" + b;
        String cStr = (c < 0) ? "(" + c + ")" : "" + c;

        System.out.println("Рівняння: " + a + "*x^2 + " + bStr + "*x + " + cStr + " = 0");

        double d = getDiscriminant();
        System.out.println("Дискримінант D = " + d);

        if (d > 0) {
            System.out.println("Рівняння має 2 дійсні корені:");
            System.out.println("  x1 = " + getRoot1());
            System.out.println("  x2 = " + getRoot2());
        } else if (d == 0) {
            System.out.println("Рівняння має один дійсний корінь:");
            System.out.println("  x = " + (-b / (2 * a)));
        } else {
            System.out.println("Рівняння не має дійсних коренів (D < 0)");
        }
    }
}