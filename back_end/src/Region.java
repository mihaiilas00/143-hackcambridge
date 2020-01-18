import java.util.ArrayList;
import java.util.List;

public class Region {
    public int id;
    private List<Bin> binList = new ArrayList<Bin>();
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
