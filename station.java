package test;

import java.util.*;


public class station {

    private String Name;  //վ����
    private List<String> Line = new ArrayList<String>();  //������·������ǻ��˵�Ļ������ж����
    private List<station> LinkStations= new ArrayList<station>();  //��֮���ڵ�վ��
    private boolean visited;//�Ƿ���ʹ���վ��
    private String beforeStation;//��վ֮ǰ���ʵ�վ��
    private int distance=0;//��վ�������վ��վ��
    private boolean ifchange;//�Ƿ񻻳� Ĭ��Ϊfalse ���ڽ��

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
