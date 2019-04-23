import java.io.File;
import java.util.*;

public class IrisData {

    private double readFile(String path,List<Point> l){
        double max=0;
        try{
            File file = new File(path);
            Scanner od = new Scanner(file);
            String linia;
            String[] tmp;
            while(od.hasNext()){
                linia=od.nextLine();
                tmp=linia.split(",");
                double[] tab=new double[tmp.length-1];
                for(int i=0;i<tmp.length-1;i++){
                    tab[i]=Double.parseDouble(tmp[i]);
                    if(tab[i]>max){
                        max=tab[i];
                    }
                }
                l.add(new Point(tab,tmp[tmp.length-1]));
            }
            od.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return max;
    }

    private List<Point> getCentroids(int k,int n,double max){
        List<Point> centroids=new ArrayList<>();

        for(int i=0;i<k;i++){
            double[] tab=new double[n];
            for(int j=0;j<n;j++){
                Random random = new Random();
                double tmp=random.nextDouble()*max*10;
                tmp=(int)(tmp);
                tab[j]=(tmp)/10;
            }
            centroids.add(new Point(tab,"c"+(i+1)));
        }
        return centroids;
    }

    private double dl(Map<Point,List<Point>> map){
        double dl=0;
        for(Point d:map.keySet()){
            Point centroid=d;
            List<Point> centroids=map.get(centroid);
            for(int i=0;i<centroids.size();i++){
                double tmp= Point.length(d,centroids.get(i));
                dl+=tmp*tmp;
            }


        }
        return dl;
    }

    public IrisData(String path, int k) {
        List<Point> data=new ArrayList<>();
        double max=readFile(path,data);

        List<Point> centroids=getCentroids(k,data.get(0).getTabLength(),max);

        //przypisanie kazdemy centroidowi punktow
        Map<Point,List<Point>> map= getMap(data,centroids);

        //poprawa centroidow
        List<Point> newCentroids= newCentroids(map);

        while(!checkCentroids(centroids,newCentroids)){
            map.forEach((key,v)-> System.out.println(key+" "+v.size()));
            System.out.println(dl(map));
            centroids=newCentroids;

            //przypisanie kazdemy centroidowi punktow
            map= getMap(data,centroids);

            //poprawa centroidow
            newCentroids= newCentroids(map);
        }
        map.forEach((key,v)->{
            System.out.println(key+" "+v.size());
            v.forEach(System.out::println);
            System.out.println(" ");
        });


    }

    private Map<Point,List<Point>> getMap(List<Point> data, List<Point> centroids){
        Map<Point,List<Point>> map=new HashMap<>();
        for(Point c:centroids){
            map.put(c,new ArrayList<>());
        }

        for(Point d:data){
            Point centroid=centroids.get(0);
            double minLength = Point.length(d,centroid);

            for(int i=1;i<centroids.size();i++){
                double tmp= Point.length(d,centroids.get(i));
                if(tmp<minLength){
                    centroid=centroids.get(i);
                    minLength=tmp;
                }
            }

            map.get(centroid).add(d);

        }

        return map;
    }

    private List<Point> newCentroids(Map<Point,List<Point>> map){
        List<Point> newCentroids=new ArrayList<>();
        for(Point p:map.keySet()){
            List<Point> points=map.get(p);
            double[] tab=new double[p.getTabLength()];
            for(int i=0;i<tab.length;i++){
                tab[i]=0;
            }
            for(Point point:points){
                for(int i=0;i<tab.length;i++){
                    tab[i]+=point.getTab()[i];
                }
            }
            for(int i=0;i<tab.length;i++){
                double tmp=tab[i]/points.size()*10;
                tmp=(int)(tmp);
                tab[i]=(tmp)/10;
            }
            newCentroids.add(new Point(tab,p.getName()));

        }
        return newCentroids;
    }

    private boolean checkCentroids(List<Point> oldCentr,List<Point> newCentr){
        System.out.println(" ");
        for(int i=0;i<oldCentr.size();i++){
            if(!Arrays.equals(oldCentr.get(i).getTab(),newCentr.get(i).getTab())){
                return false;
            }
        }
        return true;
    }

}
