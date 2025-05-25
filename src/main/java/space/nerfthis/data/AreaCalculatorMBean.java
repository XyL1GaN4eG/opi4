package space.nerfthis.data;

public interface AreaCalculatorMBean {
    double getR();
    void setR(double r);

    double getTriangleArea();

    double getRectangleArea();

    double getCircleArea();

    double getTotalArea();
}
