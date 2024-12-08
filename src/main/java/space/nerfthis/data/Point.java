package space.nerfthis.data;

import javax.persistence.*;

@Entity
@Table(name = "Points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private double r;
    @Column(name = "isInside")
    private boolean flag;


    public Point(){}

    public Point(double x, double y, double r, boolean flag) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.flag = flag;
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

    public boolean isFlag() {
        return flag;
    }
}
