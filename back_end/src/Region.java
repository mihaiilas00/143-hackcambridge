import java.util.ArrayList;
import java.util.List;

public class Region {
    public int id;
    public Location leftUp;
    public Location rightDown;

    private Bin middleBin;
    private List<Bin> binList = new ArrayList<Bin>();

    public Bin getMiddleBin(){
        return middleBin;
    }

    public Region(int id, Bin middle, List<Bin> list){
        this.id = id;
        middleBin = middle;
        for(Bin bin : list){
            binList.add(bin);
        }
    }

    public double getRegionFullness(){
        double fullness = 0;
        double weight = 1;
        double perc;
        for(int i = 0; i < binList.size(); i++){
            perc = binList.get(i).getPercFull();
            if(perc > 0.85){
                weight = 2;
            }
            if(perc < 0.35){
                weight = 0.5;
            }
            fullness += weight * perc;
        }
        return fullness;
    }
}
