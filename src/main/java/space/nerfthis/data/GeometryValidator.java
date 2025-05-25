package space.nerfthis.data;

public class GeometryValidator {
    public static boolean isInsideArea(double x, double y, double r) {
        if (x <= 0 && y >= 0)
            return y <= x + (r / 2.0);

        if (x >= -r && x <= 0)
            return y >= -(r / 2.0);

        if (x >= 0 && y >= 0)
            return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2);

        return false;
    }
}