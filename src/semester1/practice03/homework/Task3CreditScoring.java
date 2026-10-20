package semester1.practice03.homework;
import java.util.Scanner;

public class Task3CreditScoring {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== СИСТЕМА БАНКІВСЬКОГО СКОРИНГУ ===");

        System.out.print("Введіть вік позичальника: ");
        int age = scanner.nextInt();

        System.out.print("Введіть офіційний місячний дохід (грн): ");
        double monthlyIncome = scanner.nextDouble();

        System.out.print("Чи є негативна кредитна історія? (true/false): ");
        boolean hasBadCreditHistory = scanner.nextBoolean();

        System.out.print("Введіть запитувану суму кредиту (грн): ");
        double loanAmount = scanner.nextDouble();

        System.out.print("Введіть бажаний термін (місяців): ");
        int loanMonths = scanner.nextInt();

        boolean isAgeValid = (age >= 21 && age <= 65);
        boolean isCreditHistoryOk = !hasBadCreditHistory;
        boolean isPaymentAffordable = (loanMonths > 0) && ((loanAmount / loanMonths) <= (monthlyIncome * 0.5));

        double interestRate = (monthlyIncome >= 35000) ? 14.5 : 21.0;
        boolean isApproved = isAgeValid && isCreditHistoryOk && isPaymentAffordable;

        System.out.println();
        System.out.println("------------- РІШЕННЯ СКОРИНГУ -------------");
        if (isApproved) {
            double monthlyPayment = loanAmount / loanMonths;
            double precentOfIncome = monthlyPayment / monthlyIncome * 100;
            System.out.println("Статус заявки:        СХВАЛЕНО ✅");
            System.out.println("Орієнтовний платіж/міс:   " + monthlyPayment + " грн (" + precentOfIncome + "% від доходу)");
            System.out.println("Персональна ставка:   " + interestRate + "% річних");
            System.out.println("Максимально допустимий платіж: " + (monthlyIncome * 0.5) + " грн/міс");
        } else if (!isAgeValid) {
            System.out.println("Статус заявки:        ВІДХИЛЕНО ❌");
            System.out.println("Відмова: вік не відповідає критеріям (21-65 років)");
        } else if (!isCreditHistoryOk) {
            System.out.println("Статус заявки:        ВІДХИЛЕНО ❌");
            System.out.println("Відмова: виявлено негативну кредитну історію");
        } else if (!isPaymentAffordable) {
            System.out.println("Статус заявки:        ВІДХИЛЕНО ❌");
            System.out.println("Відмова: термін кредиту некоректний або щомісячний платіж перевищує 50% доходу");
        }

        System.out.println("====================================");

        scanner.close();
        }
    }