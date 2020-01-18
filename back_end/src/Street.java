import java.util.ArrayList;
import java.util.List;

public class Street {
    private String name;
    public Bin startBin;
    public Bin endBin;
    public double weight;

    private List<Bin> binList = new ArrayList<Bin>();

    public void setBinList(List<Bin> bins){

    }

    public double getWeight(){
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
