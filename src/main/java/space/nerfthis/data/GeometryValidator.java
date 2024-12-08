package space.nerfthis.data;

public class GeometryValidator {
    public static boolean isInsideArea(double x, double y, double r) {

        if (x <= 0 && y >= 0) {
            if (y <= x + (r / 2.0)) {
                return true;
            }
        }

        if (x >= -r && x <= 0) {
            if (y >= -(r / 2.0)) {
                return true;
            }
        }

        if (x >= 0 && y >= 0) {
            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) {
                return true;
            }
        }

        return false;
    }
}