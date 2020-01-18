public class Bin {
    public int id;

    private Location location;
    private float percFull;
    private float percGlass;
    private float percPlastic;
    private float percPaper;

    public float getPercFull(){
        return percFull;
    }
    public float getPercGlass(){
        return percGlass;
    }
    public float getPercPlastic(){
        return percPlastic;
    }
    public float getPercPaper(){
        return percPaper;
    }

    public void updatePercentages(float newFull, float newGlass, float newPlastic, float newPaper){
        percFull = newFull;
        percGlass = newGlass;
        percPlastic = newPlastic;
        percPaper = newPaper;
    }

    public float getDistance(Bin x){
        float dist = 0;
        //dist =  lookup[this.id][x.id];
        return dist;
    }

}
