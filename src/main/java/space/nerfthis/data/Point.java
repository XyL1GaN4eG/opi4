package space.nerfthis.data;

public class Point {
    private double x;
    private double y;
    private double r;
    private boolean isInside;

    public Point(double x, double y, double r, boolean isInside) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isInside = isInside;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isInside() {
        return isInside;
    }
}
