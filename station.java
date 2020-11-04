package test;

import java.util.*;


public class station {

    private String Name;  //站点名
    private List<String> Line = new ArrayList<String>();  //所在线路（如果是换乘点的话可以有多个）
    private List<station> LinkStations= new ArrayList<station>();  //与之相邻的站点
    private boolean visited;//是否访问过该站点
    private String beforeStation;//本站之前访问的站点
    private int distance=0;//本站距离起点站的站数
    private boolean ifchange;//是否换乘 默认为false 用于结果

    public boolean isIfchange() {
        return ifchange;
    }

    public void setIfchange(boolean ifchange) {
        this.ifchange = ifchange;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void AddStationLine(String name){
        Line.add(name);
    }

    public void AddLinkStation(station sta){
        LinkStations.add(sta);
    }

    public String getStationName() {
        return Name;
    }

    public void setStationName(String stationName) {
    	Name = stationName;
    }

    public List<String> getLine() {
        return Line;
    }

    public void setLine(List<String> line) {
        Line = line;
    }

    public List<station> getLinkStations() {
        return LinkStations;
    }

    public void setLinkStations(List<station> linkStations) {
        LinkStations = linkStations;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getPreStation() {
        return beforeStation;
    }

    public void setPreStation(String preStation) {
        this.beforeStation = preStation;
    }
}
