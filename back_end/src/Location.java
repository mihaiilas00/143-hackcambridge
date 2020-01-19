import java.util.Random;

public class Location {
    public double x;
    public double y;
    public Location(double xmin, double xmax, double ymin, double ymax){
        double random = new Random().nextDouble();
        x = xmin + (random * (xmax - xmin));

        random = new Random().nextDouble();
        y = ymin + (random * (ymax - ymin));
    }
}
