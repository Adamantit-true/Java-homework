package semester1.practice02.homework;

class Task1CurrencyExchange {
    public static void main(String[] args) {
        double usdAmount = 250.0;
        final double EXCHANGE_RATE = 41.25;
        final double COMMISSION_PERCENT = 1.5; // 1.5%

        double totalUah = usdAmount * EXCHANGE_RATE;
        double totalCommission = totalUah * (COMMISSION_PERCENT / 100.0);
        double pureUahAmount = totalUah - totalCommission;
        long cash = Math.round(pureUahAmount);

        System.out.println("================ КВИТАНЦІЯ ОБМІНУ ================");
        System.out.println("Сума до обміну:         " +usdAmount+ " USD");
        System.out.println("Курс Валют:             " +EXCHANGE_RATE+ " UAH/USD");
        System.out.println("Нараховано до комісії)  " +totalUah+ " UAH");
        System.out.println("Комісія Банку (1.5%)    " +totalCommission+ " UAH");
        System.out.println("--------------------------------------------------");
        System.out.println("чиста сума до видачі:   " +pureUahAmount+ " UAH");
        System.out.println("До видачі готівкою:     " +cash+ " UAH");
        System.out.println("==================================================");

    }
}