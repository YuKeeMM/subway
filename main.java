package test;
import java.io.*;
import java.util.*;
public class main {

    public static HashMap<String,station> Map = new HashMap<>();//查找站点信息
    public static List<line> LineSet= new ArrayList<>();//查找路线

    public static void readSubway(String way) {//从TXT中读入线路数据
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(way));
            BufferedReader read1 = new BufferedReader(reader);
            String read = "";
            read = read1.readLine();//线路数量
            int num = 0;
            num = Integer.parseInt(read);
            for (int i = 0; i <= num; i++) {
                line line = new line();//当前存的路线
                read = read1.readLine();
                if(read==null){
                    return;
                }
                String[] stations = read.split(" ");//读入的line对应的所有station
                line.setLineName(stations[0]);//地铁线路名
                for (int j = 1; j < stations.length - 1; j++) {
                    station station1 = new station();//当前line中的station
                    station station2 = new station();
                    if (Map.containsKey(stations[j])) {//如果map中已经有该站点则把这个站点拿出来处理
                        station1 = Map.get(stations[j]);
                        Map.remove(stations[j]);
                    } else {
                        station1.setStationName(stations[j]);
                        station1.setVisited(false);
                    }

                    if (Map.containsKey(stations[j + 1])) {//Map中已经有该站后面的站点拿出来处理
                        station2 = Map.get(stations[j + 1]);
                        Map.remove(stations[j + 1]);
                    } else {
                        station2.setStationName(stations[j + 1]);
                        station2.setVisited(false);
                    }
                    if (!station1.getLine().contains(line.getLineName()))//如果当前站未加入line中，则在line中当前站名
                        station1.AddStationLine(line.getLineName());
                    if (!station2.getLine().contains(line.getLineName()))
                        station2.AddStationLine(line.getLineName());
                    if (!station1.getLinkStations().contains(station2))
                        station1.AddLinkStation(station2);
                    if (!station2.getLinkStations().contains(station1))
                        station2.AddLinkStation(station1);

                    station1.setPreStation(station1.getStationName());
                    station2.setPreStation(station2.getStationName());
                    Map.put(stations[j], station1);//把station1放回Map
                    Map.put(stations[j + 1], station2);//把station2放回Map
                    if (!line.getStations().contains(station1.getStationName())) {
                        line.stationAdd(station1.getStationName());
                    }
                    if (!line.getStations().contains(station2.getStationName())) {
                        line.stationAdd(station2.getStationName());
                    }
                }
                LineSet.add(line);//把线路加入LineSet
            }
            read1.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void searchline(String line){ //根据线路名字查找该线路所有站点
        int flag = 0;
        int index = -1;
        for (line line1 :LineSet){
            index++;
            if(line1.getLineName().equals(line)) {
                flag = 1;
                break;
            }
        }
        if (flag==0)
            System.out.println("该地铁线路不存在");
        else {
            System.out.print(line + ": ");
            for (String str : LineSet.get(index).getStations()) {
                System.out.print(str + " ");
            }
        }
    }

    public static void BFS(String start,String end){//使用BFS广度优先算法计算
        int sign = 0;
        if (!Map.containsKey(start)){//判断start是否存在
            System.out.println("start不存在");
            sign = 1;
        }
        if (!Map.containsKey(end)){
            System.out.println("end不存在");//判断end是否存在
            sign = 1;
        }
        for (String temp :Map.keySet()){
        	Map.get(temp).setVisited(false);//初始化都设为false
        	Map.get(temp).setDistance(0);//初始化距离为0
        }
        station nowStation = new station();
        Queue<String> queue = new LinkedList<>();//存放遍历过的站点

        nowStation = Map.get(start);
        queue.add(start);
        while(!queue.isEmpty()){
            String nowStationName = queue.poll();
            Map.get(nowStationName).setVisited(true);
            if (nowStation.getStationName().equals(end)){
                break;
            }
            for (station station1 :Map.get(nowStationName).getLinkStations()){
                if(!Map.get(station1.getStationName()).isVisited()){//未访问过的临近站点
                	Map.get(station1.getStationName()).setPreStation(nowStationName);//为preStation赋值
                	Map.get(station1.getStationName()).setDistance(Map.get(nowStationName).getDistance()+1);//临近站的距离为本站距离加1
                    queue.offer(station1.getStationName());
                }
            }
        }
    }

    public static void path(String start,String end){
        if (start.equals(end)){
            System.out.print("起点站与终点站相同 本站为："+start);
            return;
        }
        List<String> path = new ArrayList<>();
        Stack<String> printline = new Stack<>();
        int numStation = 1;//存放站数
        String str = end;
        while(!str.equals(start)){
            path.add(str);
            printline.push(str);
            str = Map.get(str).getPreStation();
        }
        path.add(str);//把start放入path
        printline.push(str);
        for (int i=1;i<path.size()-1;i++){
            if (Map.get(path.get(i)).getLine().size()==1){
                continue;
            }
            String temp1="";
            String temp2="";
            for (String str1 : Map.get(path.get(i)).getLine()){//本站与前一站的共同拥有的线路存在temp1中
                int flag=0;
                for (String str2 :Map.get(path.get(i-1)).getLine()){
                    if (str1.equals(str2)){
                        temp1 = temp1+str1;
                        flag=1;
                        break;
                    }
                }
                if (flag==1)
                    break;
            }
            for (String str1 : Map.get(path.get(i)).getLine()){//后一站与本站的共同拥有的线路存在temp2中
                int flag=0;
                for (String str2 :Map.get(path.get(i+1)).getLine()){
                    if (str1.equals(str2)){
                        temp2 = temp2+str1;
                        flag=1;
                        break;
                    }
                }
                if (flag==1)
                    break;
            }
            if (!temp1.equals(temp2))//若temp1和temp2两线路不同则本站为转乘站
            	Map.get(path.get(i)).setIfchange(true);
        }
        //判断path中的换乘站
        while(!printline.empty()){
            String printStation = printline.pop();
            if(numStation==1){
                for (String strnow : Map.get(printStation).getLine()){
                    for (String nextStation : Map.get(path.get(path.size()-numStation-1)).getLine()){
                        if (strnow.equals(nextStation)) {
                            System.out.println("从"+strnow+"出发");

                        }
                    }
                }
            }
            if (Map.get(printStation).isIfchange()){
                String nowline ="";
                for (String strnow : Map.get(printStation).getLine()){
                    for (String nextStation : Map.get(path.get(path.size()-numStation-1)).getLine()){
                        if (strnow.equals(nextStation))
                            nowline= nowline + strnow;
                    }
                }
                System.out.println(printStation);
                System.out.println("转 "+nowline);
            }
            System.out.print(printStation+" ");
            numStation++;
        }
        
        numStation =numStation-1;
        System.out.println();
        System.out.println("一共"+numStation+"站");//输出最后一共乘坐的站数
    }

    public static void main(String[] ards){
        readSubway("E:\\原桌面\\大三上\\软件工程\\地铁.txt");
        System.out.println("1.查询某条线路的所有站点");
        System.out.println("2.查询某站到某站的最短线路");
        System.out.print("请输入序号(1或者2):");
        Scanner Scanner = new Scanner(System.in);
        int input = Scanner.nextInt();
        if (input==1){
            System.out.print("请输入地铁线路名称：");
            Scanner Scanner1 = new Scanner(System.in);
            String linename = Scanner1.nextLine();
            searchline(linename);
        }
        else if (input==2){
            System.out.print("start：");
            Scanner Scanner2 = new Scanner(System.in);
            String start =Scanner2.nextLine();
            System.out.print("end：");
            String end = Scanner2.nextLine();
            BFS(start,end);
            path(start,end);
        }
    }
}
