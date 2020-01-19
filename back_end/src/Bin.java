public class Bin {
    public int id;

    private Location location;
    private double percFull;
    private double percGlass;
    private double percPlastic;
    private double percPaper;

    public double getPercFull(){
        return percFull;
    }
    public double getPercGlass(){
        return percGlass;
    }
    public double getPercPlastic(){
        return percPlastic;
    }
    public double getPercPaper(){
        return percPaper;
    }

    public Bin(int id, double newFull, double newGlass, double newPlastic, double newPaper){
        this.id = id;
        percFull = newFull;
        percGlass = newGlass;
        percPlastic = newPlastic;
        percPaper = newPaper;
    }

    public double getDistance(Bin x){
        double dist = 0;
        //dist =  lookup[this.id][x.id];
        return dist;
    }

}
