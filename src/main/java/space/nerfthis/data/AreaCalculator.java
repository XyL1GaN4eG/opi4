package space.nerfthis.data;

public class AreaCalculator implements AreaCalculatorMBean {
    private double r = 1.0;

    @Override
    public double getR() {
        return r;
    }

    @Override
    public void setR(double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("R must be positive");
        }
        this.r = r;
    }

    // Площадь треугольника в левом верхнем квадранте: r (основание) × r/2 (высота) / 2
    @Override
    public double getTriangleArea() {
        return 0.5 * r * (r / 2.0);  // = r^2/4
    }

    // Площадь квадрата (0,0)-(r,0)-(r,-r)-(0,-r))
    @Override
    public double getRectangleArea() {
        return r * r;
    }

    // Площадь четверти круга радиуса r
    @Override
    public double getCircleArea() {
        return Math.PI * r * r / 4.0;
    }

    @Override
    public double getTotalArea() {
        return getTriangleArea()
                + getRectangleArea()
                + getCircleArea();
    }
}
