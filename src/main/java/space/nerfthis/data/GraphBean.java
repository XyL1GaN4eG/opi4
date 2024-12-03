package space.nerfthis.data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class GraphBean {
    private Double x;
    private Double y;
    private Double r;
    private List<Point> points = new ArrayList<>();
    private String pointsJson = "";




    @PostConstruct
    public void init(){
        r = 1.0;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public boolean isFormValid() {
        return x != null && y != null && r != null;
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getPointsJson() {
        return pointsJson;
    }
    public void setPointsJson(String pointsJson) {
        this.pointsJson = pointsJson;
    }

    public void addPoint() {
        if (x != null && y != null && r != null) {
            Point newPoint = new Point(x, y, r, GeometryValidator.isInsideArea(x, y, r));
            points.add(newPoint);
            pointsJson = JSONBuilder.buildJson(points);
        }
    }
}
