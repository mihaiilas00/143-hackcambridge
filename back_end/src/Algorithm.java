import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Algorithm {
    public static List<Street> streetList = new ArrayList<Street>();
    public static List<Region> regionList = new ArrayList<Region>();

    public static Bin startCouncil;
    public static Bin endCouncil;
    public static Bin middleBin;

    private static Map<Bin, List<Edge>> graph;
    private static PriorityQueue<Edge> heap;
    private static Map<Bin, Boolean> taken;
    private static Map<Bin, Double> dist;
    private static Map<Bin, Bin> last;

    public static void feedData(BufferedWriter writer) throws IOException {
        List<Bin> list = new ArrayList<>();
        List<Bin> list1 = new ArrayList<>();
        List<Bin> list2 = new ArrayList<>();
        List<Bin> list3 = new ArrayList<>();
        List<Bin> list4 = new ArrayList<>();
        List<Bin> list5 = new ArrayList<>();
        list.add(null);
        for(int i = 1; i <= 30; i++){
            Bin b = new Bin(i, Math.random(), Math.random(), Math.random(), Math.random());
            writer.write(String.valueOf(b.getPercFull()));
            writer.write(",");
            list.add(b);
        }
        writer.close();


        streetList.add(new Street(list.get(1), list.get(2), 1600));
        streetList.add(new Street(list.get(1), list.get(4), 900));
        streetList.add(new Street(list.get(1), list.get(5), 650));
        streetList.add(new Street(list.get(2), list.get(3), 1100));
        streetList.add(new Street(list.get(2), list.get(7), 850));
        streetList.add(new Street(list.get(2), list.get(6), 1250));
        streetList.add(new Street(list.get(3), list.get(8), 1700));
        streetList.add(new Street(list.get(7), list.get(8), 2500));
        streetList.add(new Street(list.get(7), list.get(11), 1200));
        streetList.add(new Street(list.get(8), list.get(11), 450));
        streetList.add(new Street(list.get(8), list.get(9), 2600));
        streetList.add(new Street(list.get(9), list.get(10), 700));
        streetList.add(new Street(list.get(9), list.get(12), 2000));
        streetList.add(new Street(list.get(12), list.get(16), 600));
        streetList.add(new Street(list.get(11), list.get(16), 1400));
        streetList.add(new Street(list.get(12), list.get(19), 850));
        streetList.add(new Street(list.get(17), list.get(25), 1400));
        streetList.add(new Street(list.get(17), list.get(18), 700));
        streetList.add(new Street(list.get(17), list.get(28), 400));
        streetList.add(new Street(list.get(17), list.get(22), 600));
        streetList.add(new Street(list.get(22), list.get(28), 500));
        streetList.add(new Street(list.get(21), list.get(28), 700));
        streetList.add(new Street(list.get(20), list.get(21), 1000));
        streetList.add(new Street(list.get(21), list.get(27), 750));
        streetList.add(new Street(list.get(27), list.get(20), 650));
        streetList.add(new Street(list.get(26), list.get(27), 600));
        streetList.add(new Street(list.get(20), list.get(26), 900));
        streetList.add(new Street(list.get(13), list.get(20), 150));
        streetList.add(new Street(list.get(13), list.get(26), 900));
        streetList.add(new Street(list.get(13), list.get(14), 350));
        streetList.add(new Street(list.get(4), list.get(14), 650));
        streetList.add(new Street(list.get(4), list.get(13), 650));
        streetList.add(new Street(list.get(22), list.get(23), 800));
        streetList.add(new Street(list.get(23), list.get(18), 600));
        streetList.add(new Street(list.get(16), list.get(29), 800));
        streetList.add(new Street(list.get(23), list.get(29), 450));
        streetList.add(new Street(list.get(29), list.get(30), 450));
        streetList.add(new Street(list.get(30), list.get(25), 1300));
        streetList.add(new Street(list.get(24), list.get(30), 900));
        streetList.add(new Street(list.get(19), list.get(24), 550));
        streetList.add(new Street(list.get(19), list.get(25), 2900));



        list1.add(list.get(1));
        list1.add(list.get(2));
        list1.add(list.get(4));
        list1.add(list.get(5));
        list1.add(list.get(6));
        list1.add(list.get(7));

        list2.add(list.get(3));
        list2.add(list.get(8));
        list2.add(list.get(9));
        list2.add(list.get(10));
        list2.add(list.get(11));
        list2.add(list.get(12));

        list3.add(list.get(13));
        list3.add(list.get(14));
        list3.add(list.get(20));
        list3.add(list.get(21));
        list3.add(list.get(26));
        list3.add(list.get(27));


        list4.add(list.get(15));
        list4.add(list.get(18));
        list4.add(list.get(17));
        list4.add(list.get(23));
        list4.add(list.get(22));
        list4.add(list.get(28));


        list5.add(list.get(16));
        list5.add(list.get(19));
        list5.add(list.get(24));
        list5.add(list.get(25));
        list5.add(list.get(29));
        list5.add(list.get(30));


        regionList.add(new Region(1, list.get(6), list1));
        regionList.add(new Region(2, list.get(9), list2));
        regionList.add(new Region(3, list.get(20), list3));
        regionList.add(new Region(4, list.get(22), list4));
        regionList.add(new Region(5, list.get(24), list5));

        startCouncil = list.get(26);
        endCouncil = list.get(10);
        System.out.println(startCouncil.id);
        System.out.println(endCouncil.id);

    }

    public static void convertGraph(){
        //produces --> graph from <---streetList

        graph = new HashMap<>();
        heap = new PriorityQueue<>(new EdgeComparator());
        taken = new HashMap<>();
        dist = new HashMap<>();
        last = new HashMap<>();
        for(Street street : streetList){
            Bin start = street.startBin;
            Bin end = street.endBin;
            double weight = street.weight;
            if(!graph.containsKey(start)){
                List<Edge> list = new LinkedList<>();
                list.add(new Edge(end, weight));
                graph.put(start, list);
            }
            else{
                List<Edge> list = graph.get(start);
                list.add(new Edge(end, weight));
                graph.put(start, list);
            }

            if(!graph.containsKey(end)){
                List<Edge> list = new LinkedList<>();
                list.add(new Edge(start, weight));
                graph.put(end, list);
            }
            else{
                List<Edge> list = graph.get(end);
                list.add(new Edge(start, weight));
                graph.put(end, list);
            }
        }

    }



    public static void selectCriticalRegion(BufferedWriter writer) throws IOException {
        double max = 0, fullness;
        int id = 0;
        for(int i = 0; i < regionList.size(); i++){
            fullness = regionList.get(i).getRegionFullness();
            if(fullness > max){
                max = fullness;
                middleBin = regionList.get(i).getMiddleBin();
                id = regionList.get(i).id;
            }
        }

        writer.write(String.valueOf(id));
        writer.write("\n");
        System.out.println((id));

    }

    public static List<Bin> dijkstra(Bin start, Bin end){


        for(Bin bin : graph.keySet()){
            dist.put(bin, Double.MAX_VALUE);
            heap.add(new Edge(bin, Double.MAX_VALUE));
        }

        dist.put(start, 0.0);
        heap.add(new Edge(start, 0.0));

        Bin x, y;
        double c;

            while(!heap.isEmpty())
            {
                x = heap.peek().bin;
                heap.poll();
                    for (Edge edge : graph.get(x)) {
                        y = edge.bin;
                        c = edge.cost;
                        if (dist.get(x) + c < dist.get(y)) {
                            dist.put(y, dist.get(x) + c);
                            last.put(y, x);
                            heap.add(new Edge(y, dist.get(y)));
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
        resultAux.add(next);
        for(int i = resultAux.size() - 1; i >= 0; i--){
            result.add(resultAux.get(i));
        }
        return result;

    }

    public static List<List<Bin>> kShortestPaths(Bin start, Bin end, int trials){
        //Future Development: YEN'S algorithms for k shortest paths
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

    public static void writePath(List<Bin> list, BufferedWriter writer) throws IOException {
        for(Bin bin : list){
            writer.write(String.valueOf(bin.id));
            System.out.println(bin.id);
            writer.write(",");
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedWriter writer_alg = new BufferedWriter(new FileWriter("C:\\Users\\Stefi\\143-hackcambridge\\back_end\\out\\algorithm_output"));
        BufferedWriter writer_inp = new BufferedWriter(new FileWriter("C:\\Users\\Stefi\\143-hackcambridge\\back_end\\out\\input_random"));

        feedData(writer_inp);

        selectCriticalRegion(writer_alg);
        convertGraph();
        System.out.println("First \n");
        List<Bin> inBound = selectOptimalPath(kShortestPaths(startCouncil, middleBin, 5));
        writePath(inBound, writer_alg);

        System.out.println(middleBin.id);

        System.out.println("Second \n");
        List<Bin> outBound = selectOptimalPath(kShortestPaths(middleBin, endCouncil, 5));
        writePath(outBound, writer_alg);

        writer_alg.close();


    }


}
