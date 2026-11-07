package semester1.practice10.homework;

interface Scalable2D {
    double MIN_SCALE_FACTOR = 0.1;

    void scale(double factor);
}

interface Rotatable2D {
    void rotate(double deltaDegrees);

    double getRotationAngle();
}

abstract class AbstractShape2D {
    private final String name;
    private double centerX;
    private double centerY;

    public AbstractShape2D(String name, double centerX, double centerY) {
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public String getName() {
        return name;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public final double getIsoperimetricQuotient() {
        double p = getPerimeter();
        return 4 * Math.PI * getArea() / (p * p);
    }

    protected double round2(double x) {
        return Math.round(x * 100) / 100.0;
    }

    protected double round4(double x) {
        return Math.round(x * 10000) / 10000.0;
    }

    protected String centerText() {
        return "center=(" + centerX + "; " + centerY + ")";
    }

    protected String metricsText() {
        return "S=" + round2(getArea())
                + ", P=" + round2(getPerimeter())
                + ", Q=" + round4(getIsoperimetricQuotient());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "['" + name + "', "
                + centerText() + " | " + metricsText() + "]";
    }
}

class CircleShape extends AbstractShape2D implements Scalable2D {
    private double radius;

    public CircleShape(String name, double centerX, double centerY, double radius) {
        super(name, centerX, centerY);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        radius = radius * factor;
    }

    @Override
    public String toString() {
        return "CircleShape['" + getName() + "', " + centerText()
                + ", r=" + round2(radius) + " | " + metricsText() + "]";
    }
}

class RectangleShape extends AbstractShape2D implements Scalable2D, Rotatable2D {
    private double width;
    private double height;
    private double rotationAngle;

    public RectangleShape(String name, double centerX, double centerY, double width, double height) {
        super(name, centerX, centerY);
        this.width = width;
        this.height = height;
        this.rotationAngle = 0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        width = width * factor;
        height = height * factor;
    }

    @Override
    public void rotate(double deltaDegrees) {
        rotationAngle = rotationAngle + deltaDegrees;
        while (rotationAngle >= 360) {
            rotationAngle = rotationAngle - 360;
        }
        while (rotationAngle < 0) {
            rotationAngle = rotationAngle + 360;
        }
    }

    @Override
    public double getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public String toString() {
        return "RectangleShape['" + getName() + "', " + centerText()
                + ", " + round2(width) + "x" + round2(height)
                + ", angle=" + rotationAngle + "° | " + metricsText() + "]";
    }
}

final class ImmutableReferenceSquare extends AbstractShape2D {
    private final double side;

    public ImmutableReferenceSquare(String name, double centerX, double centerY, double side) {
        super(name, centerX, centerY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    public String toString() {
        return "ImmutableReferenceSquare['" + getName() + "', " + centerText()
                + ", side=" + round2(side) + " | " + metricsText() + "]";
    }
}

public class Task1PolymorphicShapesAndTransforms {

    public static double calculateTotalArea(AbstractShape2D[] shapes) {
        double total = 0;
        for (int i = 0; i < shapes.length; i++) {
            total = total + shapes[i].getArea();
        }
        return total;
    }

    public static void transformScene(AbstractShape2D[] shapes, double scaleFactor, double rotateDeg) {
        for (int i = 0; i < shapes.length; i++) {
            AbstractShape2D shape = shapes[i];
            boolean done = false;

            if (shape instanceof Scalable2D) {
                Scalable2D s = (Scalable2D) shape;
                s.scale(scaleFactor);

                String info = "";
                if (shape instanceof CircleShape) {
                    CircleShape c = (CircleShape) shape;
                    info = "новий r=" + Math.round(c.getRadius() * 100) / 100.0;
                }
                if (shape instanceof RectangleShape) {
                    RectangleShape r = (RectangleShape) shape;
                    info = Math.round(r.getWidth() * 100) / 100.0 + "x"
                            + Math.round(r.getHeight() * 100) / 100.0;
                }

                System.out.println("    [Scalable2D] '" + shape.getName()
                        + "' масштабовано в " + scaleFactor + " разів (" + info + ")");
                done = true;
            }

            if (shape instanceof Rotatable2D) {
                Rotatable2D r = (Rotatable2D) shape;
                r.rotate(rotateDeg);
                System.out.println("    [Rotatable2D] '" + shape.getName()
                        + "' повернуто на +" + rotateDeg
                        + "° (поточний кут: " + r.getRotationAngle() + "°)");
                done = true;
            }

            if (!done) {
                System.out.println("    [Info] '" + shape.getName()
                        + "' (final class) пропущено — не підтримує трансформації.");
            }
        }
    }

    public static void printScene(AbstractShape2D[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.println("  " + (i + 1) + ". " + shapes[i]);
        }
    }

    public static double round2(double x) {
        return Math.round(x * 100) / 100.0;
    }

    public static void main(String[] args) {
        AbstractShape2D[] shapes = new AbstractShape2D[3];
        shapes[0] = new CircleShape("Коло-Лінза", 0.0, 0.0, 5.0);
        shapes[1] = new RectangleShape("Рамка-A", 2.0, 3.0, 4.0, 6.0);
        shapes[2] = new ImmutableReferenceSquare("Еталон-Квадрат", 10.0, 10.0, 5.0);

        System.out.println("================ ПОЛІМОРФНА ГЕОМЕТРІЯ ТА ІНТЕРФЕЙСИ ТРАНСФОРМАЦІЇ ================");
        System.out.println("[СТАН СЦЕНИ ДО ТРАНСФОРМАЦІЇ]:");
        printScene(shapes);
        System.out.println("Сумарна площа сцени: " + round2(calculateTotalArea(shapes)) + " кв.од.");
        System.out.println();

        System.out.println("-> Виконуємо пакетну трансформацію (scale x2.0, rotate +45.0°) через instanceof...");
        transformScene(shapes, 2.0, 45.0);

        System.out.println();
        System.out.println("[СТАН СЦЕНИ ПІСЛЯ ТРАНСФОРМАЦІЇ]:");
        printScene(shapes);
        System.out.println("Нова сумарна площа сцени: " + round2(calculateTotalArea(shapes)) + " кв.од.");
        System.out.println("================================================================================");
    }
}
