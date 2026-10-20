package semester1.practice03.homework;
import java.util.Scanner;

public class Task1FuelCalculator {
    public static void main(String[] args){

        System.out.println();
        System.out.println("=== КАЛЬКУЛЯТОР ПОЇЗДКИ ТА ПАЛЬНОГО ===");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть відстань поїздки (км): ");
        double distance = scanner.nextDouble();
        System.out.print("Введіть середню витрату на 100 км (л): ");
        double fuelConsumption = scanner.nextDouble();
        System.out.print("Оберіть тип пального (1 - А-95, 2 - Дизель, 3 - Газ): ");
        int fuelType = scanner.nextInt();


        if (distance <= 0 || fuelConsumption <= 0 ) {
            System.out.println("Помилка");
        }else {
            double pricePerLiter;
            String fuelName;
            boolean isFuelTypeValid = true;

            switch(fuelType) {
                case 1 -> {
                    pricePerLiter = 56.50;
                    fuelName = "Бензин А-95";
                }
                case 2 -> {
                    pricePerLiter = 52.80;
                    fuelName = "Дизель";
                }
                case 3 -> {
                    pricePerLiter = 29.40;
                    fuelName = "Газ";
                }
                default -> {
                    fuelName = null;
                    pricePerLiter = 0;
                    isFuelTypeValid = false;
                }
        }
        if(!isFuelTypeValid) {
            System.out.println("Невідомий тип пального");
    }else {
            double  neededFuel = distance * fuelConsumption / 100;
            double baseCost = neededFuel * pricePerLiter;

            double discountPrecent = (baseCost >= 2000.0) ? 5 : 0;
            double discountAmount = baseCost * discountPrecent / 100;
            double finalCost = (baseCost >= 2000.0 ) ? baseCost * 0.95 : baseCost;

            System.out.println();
            System.out.println("---------------- ЗВІТ ПОЇЗДКИ ----------------");
            System.out.println("Обране пальне:                 " + fuelName + " (" + pricePerLiter + " грн/л)");
            System.out.println("Необхідно пального:            " + neededFuel + " л");
            System.out.println("Базова вартість:               " + baseCost + " грн");
            System.out.println("Знижка АЗС (" + discountPrecent + "%):             " + discountAmount + " грн");
            System.out.println("-----------------------------------------------");
            System.out.println("Підсумкова сума до сплати:     " + finalCost + " грн");
            System.out.println("===============================================");

            }
        scanner.close();
        }
}
}
