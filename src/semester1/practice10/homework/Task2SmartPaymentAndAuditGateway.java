package semester1.practice10.homework;

interface CashbackCapable {
    double MAX_CASHBACK_UAH = 500.0;

    double getCashbackRate();

    default double calculateCashback(double baseAmount) {
        return Math.min(MAX_CASHBACK_UAH, baseAmount * getCashbackRate());
    }
}

interface Refundable {
    boolean requestRefund(String reason);

    boolean isRefunded();
}

interface BlockchainVerifiable {
    String getTxHash();

    int getConfirmations();

    default boolean isFinalized() {
        return getConfirmations() >= 12;
    }
}

abstract class AbstractPaymentTransaction {
    private final String txId;
    private final double baseAmountUah;
    private boolean processed;

    public AbstractPaymentTransaction(String txId, double baseAmountUah) {
        this.txId = txId;
        this.baseAmountUah = baseAmountUah;
        this.processed = false;
    }

    public String getTxId() {
        return txId;
    }

    public double getBaseAmountUah() {
        return baseAmountUah;
    }

    public boolean isProcessed() {
        return processed;
    }

    public abstract double calculateFee();

    public abstract String getPaymentChannel();

    public final boolean process() {
        if (baseAmountUah <= 0 || processed == true) {
            return false;
        }
        processed = true;
        return true;
    }

    public final double getTotalCharged() {
        return baseAmountUah + calculateFee();
    }
}

class PremiumCardPayment extends AbstractPaymentTransaction implements CashbackCapable, Refundable {
    private String maskedPan;
    private double cashbackRate;
    private boolean refunded;

    public PremiumCardPayment(String txId, double baseAmountUah, String maskedPan) {
        super(txId, baseAmountUah);
        this.maskedPan = maskedPan;
        this.cashbackRate = 0.03;
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return getBaseAmountUah() * 0.01;
    }

    @Override
    public String getPaymentChannel() {
        return "Visa Infinite";
    }

    @Override
    public double getCashbackRate() {
        return cashbackRate;
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

class CryptoUsdtPayment extends AbstractPaymentTransaction implements CashbackCapable, BlockchainVerifiable {
    private String txHash;
    private int confirmations;

    public CryptoUsdtPayment(String txId, double baseAmountUah, String txHash, int confirmations) {
        super(txId, baseAmountUah);
        this.txHash = txHash;
        this.confirmations = confirmations;
    }

    @Override
    public double calculateFee() {
        return 40.0;
    }

    @Override
    public String getPaymentChannel() {
        return "USDT TRC-20";
    }

    @Override
    public double getCashbackRate() {
        return 0.01;
    }

    @Override
    public String getTxHash() {
        return txHash;
    }

    @Override
    public int getConfirmations() {
        return confirmations;
    }
}

final class TaxTreasuryPayment extends AbstractPaymentTransaction implements Refundable {
    private String taxCode;
    private boolean refunded;

    public TaxTreasuryPayment(String txId, double baseAmountUah, String taxCode) {
        super(txId, baseAmountUah);
        this.taxCode = taxCode;
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getPaymentChannel() {
        return "ДержКазначейство";
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

public class Task2SmartPaymentAndAuditGateway {

    public static double round2(double x) {
        return Math.round(x * 100) / 100.0;
    }

    public static void runFinancialAudit(AbstractPaymentTransaction[] ledger) {
        double totalTurnover = 0;
        double totalFees = 0;
        double totalCashback = 0;

        System.out.println("[1. ПРОВЕДЕННЯ ПАКЕТУ ТРАНЗАКЦІЙ (final process())]:");
        for (int i = 0; i < ledger.length; i++) {
            AbstractPaymentTransaction tx = ledger[i];
            boolean ok = tx.process();
            String mark = "❌";
            if (ok) {
                mark = "✅";
            }
            System.out.println("  " + mark + " [" + tx.getTxId() + " | " + tx.getPaymentChannel() + "] Сума: "
                    + round2(tx.getBaseAmountUah()) + " грн | Комісія: " + round2(tx.calculateFee())
                    + " грн | Всього: " + round2(tx.getTotalCharged()) + " грн");
            if (ok) {
                totalTurnover = totalTurnover + tx.getBaseAmountUah();
                totalFees = totalFees + tx.calculateFee();
            }
        }

        System.out.println();
        System.out.println("[2. АВТОМАТИЧНИЙ АУДИТ МОЖЛИВОСТЕЙ (Pattern Matching instanceof)]:");
        boolean refundTested = false;

        for (int i = 0; i < ledger.length; i++) {
            AbstractPaymentTransaction tx = ledger[i];

            if (tx instanceof CashbackCapable) {
                CashbackCapable c = (CashbackCapable) tx;
                double calculated = tx.getBaseAmountUah() * c.getCashbackRate();
                double cashback = c.calculateCashback(tx.getBaseAmountUah());
                long percent = Math.round(c.getCashbackRate() * 100);
                totalCashback = totalCashback + cashback;

                if (calculated > CashbackCapable.MAX_CASHBACK_UAH) {
                    System.out.println("  💰 [CashbackCapable] " + tx.getTxId() + ": розрахунковий " + percent
                            + "% = " + (int) calculated + " грн -> нараховано " + round2(cashback)
                            + " грн (Ліміт MAX_CASHBACK_UAH!)");
                } else if (tx instanceof CryptoUsdtPayment) {
                    System.out.println("  💰 [CashbackCapable] " + tx.getTxId() + ": нараховано " + percent
                            + "% крипто-бонусу = " + round2(cashback) + " грн");
                } else {
                    System.out.println("  💰 [CashbackCapable] " + tx.getTxId() + ": нараховано " + percent
                            + "% кешбеку = " + round2(cashback) + " грн");
                }
            }

            if (tx instanceof BlockchainVerifiable) {
                BlockchainVerifiable b = (BlockchainVerifiable) tx;
                System.out.println("  ⛓️ [BlockchainVerifiable] " + tx.getTxId() + ": Hash=" + b.getTxHash()
                        + ", Підтверджень=" + b.getConfirmations() + " (Finalized: " + b.isFinalized() + ")");
            }

            if (tx instanceof Refundable && refundTested == false) {
                Refundable r = (Refundable) tx;
                refundTested = true;
                boolean refundOk = r.requestRefund("Подвійне списання");
                String result = "ВІДМОВЛЕНО";
                if (refundOk) {
                    result = "УСПІШНО ПОВЕРНЕНО!";
                }
                System.out.println("  🔄 [Refundable] Спроба повернення " + tx.getTxId()
                        + " ('Подвійне списання'): " + result);
            }
        }

        System.out.println();
        System.out.println("[ПІДСУМКОВИЙ ЗВІТ ШЛЮЗУ]:");
        System.out.println("  Сумарний базовий оборот:  " + round2(totalTurnover) + " грн");
        System.out.println("  Сумарна комісія шлюзу:      " + round2(totalFees) + " грн");
        System.out.println("  Сумарний виплачений кешбек: " + round2(totalCashback) + " грн");
    }

    public static void main(String[] args) {
        AbstractPaymentTransaction[] ledger = new AbstractPaymentTransaction[3];
        ledger[0] = new PremiumCardPayment("TX-101", 20000.0, "4111****1111");
        ledger[1] = new CryptoUsdtPayment("TX-102", 8500.0, "0x9f8a...c4", 18);
        ledger[2] = new TaxTreasuryPayment("TX-103", 5000.0, "TAX-2026");

        System.out.println("================ ПРОЦЕСИНГОВИЙ ЦЕНТР FINTECH (POLYMORPHISM & INTERFACES) ================");
        runFinancialAudit(ledger);
        System.out.println("=========================================================================================");
    }
}
