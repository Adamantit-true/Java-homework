package semester1.practice09.homework;

public class Task2WorkstationConfigurator {

    public static void main(String[] args) {
        Motherboard motherboard = new Motherboard("ASUS ROG B650", "AM5", 4);
        PowerSupplyUnit psu = new PowerSupplyUnit("Seasonics 750W Gold", 750, 0.90);
        WorkstationPC pc = new WorkstationPC("DeepLearning-Rig", motherboard, psu);

        ProcessorUnit intel = new ProcessorUnit("Intel Core i9", 21000, "LGA1700", 24, 5.8, 125);
        ProcessorUnit ryzen = new ProcessorUnit("AMD Ryzen 9 7950X", 28000, "AM5", 16, 5.7, 170);
        GraphicsAccelerator gpu = new GraphicsAccelerator("NVIDIA RTX 4080 Super", 42000, 16, 552, 320);
        int[] samples = {7450, 7300, 7400};
        NvmeSsd ssd = new NvmeSsd("Samsung 990 Pro 2TB", 8900, 2000, 7450, samples);

        System.out.println("================ КОНФІГУРАТОР РОБОЧОЇ СТАНЦІЇ (КОМПОЗИЦІЯ) ================");
        System.out.println("[ПЕРЕВІРКА СУМІСНОСТІ]:");
        pc.installComponent(intel);
        pc.installComponent(ryzen);
        pc.installComponent(gpu);
        pc.installComponent(ssd);

        System.out.println();
        System.out.println(pc);
        System.out.println("-----------------------------------------------------------------------");

        System.out.println("[ДОДАТКОВІ ПЕРЕВІРКИ]:");
        GraphicsAccelerator hungryGpu = new GraphicsAccelerator("NVIDIA RTX 4090 OC", 90000, 24, 512, 450);
        pc.installComponent(hungryGpu);

        samples[0] = 1;
        int[] copy = ssd.getSpeedSamples();
        copy[1] = 2;
        System.out.println("  Змінили вихідний масив і копію з геттера.");
        System.out.println("  Перший замір SSD (не змінився): " + ssd.getSpeedSamples()[0]);
        System.out.println("  Другий замір SSD (не змінився): " + ssd.getSpeedSamples()[1]);
        System.out.println("=======================================================================");
    }
}

class NumFmt {
    public static String fmt(double value) {
        long cents = Math.round(Math.abs(value) * 100);
        long whole = cents / 100;
        long fraction = cents % 100;

        String result = "";
        if (value < 0 && cents != 0) {
            result = "-";
        }
        result = result + whole + ".";
        if (fraction < 10) {
            result = result + "0";
        }
        result = result + fraction;
        return result;
    }
}

class Motherboard {
    private final String model;
    private final String socketType;
    private final int maxExpansionSlots;

    public Motherboard(String model, String socketType, int maxExpansionSlots) {
        if (maxExpansionSlots < 1 || maxExpansionSlots > 8) {
            throw new IllegalArgumentException("Кількість слотів має бути від 1 до 8");
        }
        this.model = model;
        this.socketType = socketType;
        this.maxExpansionSlots = maxExpansionSlots;
    }

    public String getModel() {
        return model;
    }

    public String getSocketType() {
        return socketType;
    }

    public int getMaxExpansionSlots() {
        return maxExpansionSlots;
    }

    public boolean isSocketCompatible(String cpuSocket) {
        return socketType.equalsIgnoreCase(cpuSocket);
    }
}

class PowerSupplyUnit {
    private final String model;
    private final int maxOutputWatts;
    private final double efficiencyCoefficient;

    public PowerSupplyUnit(String model, int maxOutputWatts, double efficiencyCoefficient) {
        if (maxOutputWatts <= 0) {
            throw new IllegalArgumentException("Потужність БЖ має бути > 0");
        }
        if (efficiencyCoefficient < 0.80 || efficiencyCoefficient > 0.96) {
            throw new IllegalArgumentException("ККД має бути в межах 0.80 - 0.96");
        }
        this.model = model;
        this.maxOutputWatts = maxOutputWatts;
        this.efficiencyCoefficient = efficiencyCoefficient;
    }

    public String getModel() {
        return model;
    }

    public int getMaxOutputWatts() {
        return maxOutputWatts;
    }

    public double getEfficiencyCoefficient() {
        return efficiencyCoefficient;
    }

    public int getSafeContinuousWatts() {
        return (int) (maxOutputWatts * 0.85);
    }

    public double calculateWallPowerDraw(int internalLoadWatts) {
        return internalLoadWatts / efficiencyCoefficient;
    }
}

abstract class AbstractHardwareComponent {
    private final String modelName;
    private final double priceUah;

    public AbstractHardwareComponent(String modelName, double priceUah) {
        if (priceUah < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
        this.modelName = modelName;
        this.priceUah = priceUah;
    }

    public String getModelName() {
        return modelName;
    }

    public double getPriceUah() {
        return priceUah;
    }

    public abstract int getPowerWatts();

    public abstract double getBenchmarkScore();

    public abstract String getCategory();

    @Override
    public String toString() {
        return "[" + getCategory() + "] " + modelName
                + " (" + getPowerWatts() + " Вт | Score: " + getBenchmarkScore() + ")";
    }
}

class ProcessorUnit extends AbstractHardwareComponent {
    private final String socket;
    private final int cores;
    private final double boostClockGhz;
    private final int tdpWatts;

    public ProcessorUnit(String modelName, double priceUah, String socket,
                         int cores, double boostClockGhz, int tdpWatts) {
        super(modelName, priceUah);
        if (cores <= 0 || boostClockGhz <= 0 || tdpWatts <= 0) {
            throw new IllegalArgumentException("Ядра, частота і TDP мають бути > 0");
        }
        this.socket = socket;
        this.cores = cores;
        this.boostClockGhz = boostClockGhz;
        this.tdpWatts = tdpWatts;
    }

    public String getSocket() {
        return socket;
    }

    @Override
    public int getPowerWatts() {
        return tdpWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return cores * boostClockGhz * 120.0;
    }

    @Override
    public String getCategory() {
        return "CPU";
    }
}

class GraphicsAccelerator extends AbstractHardwareComponent {
    private final int vramGb;
    private final int tensorCores;
    private final int boardPowerWatts;

    public GraphicsAccelerator(String modelName, double priceUah, int vramGb,
                               int tensorCores, int boardPowerWatts) {
        super(modelName, priceUah);
        if (vramGb <= 0 || tensorCores <= 0 || boardPowerWatts <= 0) {
            throw new IllegalArgumentException("VRAM, тензорні ядра і потужність мають бути > 0");
        }
        this.vramGb = vramGb;
        this.tensorCores = tensorCores;
        this.boardPowerWatts = boardPowerWatts;
    }

    @Override
    public int getPowerWatts() {
        return boardPowerWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return vramGb * 250.0 + tensorCores * 1.5;
    }

    @Override
    public String getCategory() {
        return "GPU";
    }
}

class NvmeSsd extends AbstractHardwareComponent {
    private final int capacityGb;
    private final int readSpeedMbSec;
    private final int[] speedSamples;

    public NvmeSsd(String modelName, double priceUah, int capacityGb,
                   int readSpeedMbSec, int[] speedSamples) {
        super(modelName, priceUah);
        if (capacityGb <= 0 || readSpeedMbSec <= 0) {
            throw new IllegalArgumentException("Об'єм і швидкість мають бути > 0");
        }
        this.capacityGb = capacityGb;
        this.readSpeedMbSec = readSpeedMbSec;
        if (speedSamples != null) {
            this.speedSamples = speedSamples.clone();
        } else {
            this.speedSamples = new int[0];
        }
    }

    public int[] getSpeedSamples() {
        return speedSamples.clone();
    }

    @Override
    public int getPowerWatts() {
        return 12;
    }

    @Override
    public double getBenchmarkScore() {
        return readSpeedMbSec * 0.25;
    }

    @Override
    public String getCategory() {
        return "NVMe SSD";
    }
}

class WorkstationPC {
    private final String buildName;
    private final Motherboard motherboard;
    private final PowerSupplyUnit psu;
    private final AbstractHardwareComponent[] components;
    private int componentCount;

    public WorkstationPC(String buildName, Motherboard motherboard, PowerSupplyUnit psu) {
        if (motherboard == null || psu == null) {
            throw new IllegalArgumentException("Потрібні материнська плата і блок живлення");
        }
        this.buildName = buildName;
        this.motherboard = motherboard;
        this.psu = psu;
        this.components = new AbstractHardwareComponent[motherboard.getMaxExpansionSlots()];
        this.componentCount = 0;
    }

    public boolean installComponent(AbstractHardwareComponent comp) {
        if (comp == null) {
            System.out.println("❌ Відмова: деталь не вказана!");
            return false;
        }

        if (componentCount >= motherboard.getMaxExpansionSlots()) {
            System.out.println("❌ Відмова: немає вільних слотів для " + comp.getModelName() + "!");
            return false;
        }

        if (comp.getCategory().equals("CPU")) {
            ProcessorUnit cpu = (ProcessorUnit) comp;
            if (!motherboard.isSocketCompatible(cpu.getSocket())) {
                System.out.println("❌ Відмова: Процесор " + cpu.getModelName()
                        + " (" + cpu.getSocket() + ") несумісний із платою "
                        + motherboard.getModel() + " (Сокет " + motherboard.getSocketType() + ")!");
                return false;
            }
        }

        int newPower = getTotalPowerWatts() + comp.getPowerWatts();
        if (newPower > psu.getSafeContinuousWatts()) {
            System.out.println("❌ Відмова: ризик перевантаження БЖ для " + comp.getModelName()
                    + "! Потрібно " + newPower + " Вт, безпечний ліміт " + psu.getSafeContinuousWatts() + " Вт");
            return false;
        }

        components[componentCount] = comp;
        componentCount++;
        System.out.println("✅ Встановлено: " + comp);
        return true;
    }

    public int getTotalPowerWatts() {
        int sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getPowerWatts();
        }
        return sum;
    }

    public double getTotalSystemScore() {
        double sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getBenchmarkScore();
        }
        return sum;
    }

    public double getTotalBuildPrice() {
        double sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getPriceUah();
        }
        return sum;
    }

    @Override
    public String toString() {
        int power = getTotalPowerWatts();
        String status = "СТАБІЛЬНО!";
        if (power > psu.getSafeContinuousWatts()) {
            status = "ПЕРЕВАНТАЖЕННЯ!";
        }
        long efficiencyPercent = Math.round(psu.getEfficiencyCoefficient() * 100);

        String result = "[ПАСПОРТ ЗБІРКИ '" + buildName + "']:\n";
        result = result + "  Материнська плата: " + motherboard.getModel()
                + " (Socket: " + motherboard.getSocketType()
                + ", Слотів: " + componentCount + "/" + motherboard.getMaxExpansionSlots() + ")\n";
        result = result + "  Блок живлення:     " + psu.getModel()
                + " (Безпечний ліміт 85%: " + psu.getSafeContinuousWatts()
                + " Вт, ККД: " + efficiencyPercent + "%)\n";
        result = result + "  Внутрішнє споживання: " + power
                + " Вт (З розетки з урахуванням ККД: " + NumFmt.fmt(psu.calculateWallPowerDraw(power))
                + " Вт) -> " + status + "\n";
        result = result + "  Сумарний індекс продуктивності: " + NumFmt.fmt(getTotalSystemScore()) + " балів\n";
        result = result + "  Загальна вартість конфігурації: " + NumFmt.fmt(getTotalBuildPrice()) + " грн";
        return result;
    }
}