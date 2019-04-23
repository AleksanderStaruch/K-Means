import java.util.Arrays;

public class Point {
    private double[] tab;
    private String name;

    public Point(double[] tab, String name) {
        this.tab = tab;
        this.name = name;
    }

    public int getTabLength() {
        return tab.length;
    }

    public double[] getTab() {
        return tab;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Point" +Arrays.toString(tab) + ", name=" + name;
    }

    public static double length(Point a1, Point a2){
        double tmp=0;
        for(int i=0;i<a1.tab.length;i++){
            tmp+=(a1.tab[i]-a2.tab[i])*(a1.tab[i]-a2.tab[i]);
        }
        return Math.sqrt(tmp);

    }

}
