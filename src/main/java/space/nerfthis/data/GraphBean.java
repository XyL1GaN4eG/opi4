package space.nerfthis.data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
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
    @Inject
    private DataBaseBean dataBaseBean;

    private PointStats pointStats;
    private AreaCalculator areaCalculator;


    @PostConstruct
    public void init() {
        dataBaseBean.getAllPoints();
        points = dataBaseBean.getPoints();
        pointsJson = JSONBuilder.buildJson(points);
        r = 1.0;

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            pointStats = new PointStats();
            ObjectName statsName = new ObjectName("space.nerfthis.jmx:type=PointStats");
            mbs.registerMBean(pointStats, statsName);

            areaCalculator = new AreaCalculator();
            // синхронизируем начальный r
            areaCalculator.setR(r);
            ObjectName areaName = new ObjectName("space.nerfthis.jmx:type=AreaCalculator");
            mbs.registerMBean(areaCalculator, areaName);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            dataBaseBean.addPoint(newPoint);
            pointsJson = JSONBuilder.buildJson(points);

            if (pointStats != null)
                pointStats.notifyNewPoint(newPoint);

            // синхронизируем r в случае если пользователь сменил r
            if (areaCalculator != null && areaCalculator.getR()!=r)
                areaCalculator.setR(r);
        }
    }

    public boolean isFormValid() {
        return x != null && y != null && r != null;
    }
}
