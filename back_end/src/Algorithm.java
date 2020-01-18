import javafx.util.Pair;

import java.util.*;

public class Algorithm {
    public static List<Street> streetList = new ArrayList<Street>();
    public static List<Region> regionList = new ArrayList<Region>();

    public static Bin startCouncil;
    public static Bin endCouncil;
    public static Bin middleBin;

    private static Map<Bin, Pair<Bin, Double>> graph;
    private static PriorityQueue<Pair<Double, Bin>> heap;
    private static Map<Bin, Boolean> taken;
    private static Map<Bin, Double> dist;
    private static Map<Bin, Bin> last;

    public static void convertGraph(){
        //produces --> graph from <---streetList
        for(Street street : streetList){
            Bin start = street.startBin;
            Bin end = street.endBin;
            double weight = street.weight;
            graph.put(start, new Pair(end, weight));
            graph.put(end, new Pair(start, weight));
        }

    }



    public static void selectCriticalRegion(){
        double max = 0, fullness;
        for(int i = 0; i < regionList.size(); i++){
            fullness = regionList.get(i).getRegionFullness();
            if(fullness > max){
                max = fullness;
                middleBin = regionList.get(i).getMiddleBin();
            }
        }
    }

    public static List<Bin> dijkstra(Bin start, Bin end){
        dist.put(start, 0.0);
        heap.add(new Pair(0, start));

        for(Bin bin : graph.keySet()){
            dist.put(start, Double.MAX_VALUE);
            heap.add(new Pair(-Double.MAX_VALUE, start));
        }



        Bin x, y;
        double c;
        while(!heap.isEmpty())
        {
            while(!heap.isEmpty() && taken.get(heap.peek().getValue()))
                heap.poll();
            if(!heap.isEmpty())
            {
                x = heap.peek().getValue();
                taken.put(x, true);
                for (Bin bin : graph.keySet()) {
                    y = graph.get(bin).getKey();
                    c = graph.get(bin).getValue();
                    if (dist.get(x) + c < dist.get(y)) {
                        dist.put(y, dist.get(x) + c);
                        last.put(y, x);
                        heap.add(new Pair(-dist.get(y), y));
                    }
                }
            }
        }

        List<Bin> resultAux = new ArrayList<Bin>();
        List<Bin> result = new ArrayList<Bin>();
        Bin next = end;
        while(next != start){
            resultAux.add(next);
            next = last.get(next);
        }
        for(int i = resultAux.size() - 1; i >= 0; i--){
            result.add(resultAux.get(i));
        }
        return result;

    }

    public static List<List<Bin>> kShortestPaths(Bin start, Bin end, int trials){
        //Future Development: YEN'S algorithms
        List<List<Bin>> result = new ArrayList<>();
        result.add(dijkstra(start, end));
        return result;
    }


    public static List<Bin> selectOptimalPath(List<List<Bin>> options){
        List<Bin> result = new ArrayList<>();
        double max = 0;
        for(int i = 0; i < options.size(); i++){
            List<Bin> list = options.get(i);
            double sum = 0;
            for(Bin bin : list){
                sum += bin.getPercFull();
            }
            if(sum > 0){
                max = sum;
                for(int j = 0; j < list.size(); j++) {
                    result.add(list.get(j));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        selectCriticalRegion();
        convertGraph();
        List<Bin> inBound = selectOptimalPath(kShortestPaths(startCouncil, middleBin, 5));
        List<Bin> outBound = selectOptimalPath(kShortestPaths(middleBin, endCouncil, 5));

    }


}
