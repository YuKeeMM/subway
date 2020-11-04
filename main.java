package test;
import java.io.*;
import java.util.*;
public class main {

    public static HashMap<String,station> Map = new HashMap<>();//����վ����Ϣ
    public static List<line> LineSet= new ArrayList<>();//����·��

    public static void readSubway(String way) {//��TXT�ж�����·����
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(way));
            BufferedReader read1 = new BufferedReader(reader);
            String read = "";
            read = read1.readLine();//��·����
            int num = 0;
            num = Integer.parseInt(read);
            for (int i = 0; i <= num; i++) {
                line line = new line();//��ǰ���·��
                read = read1.readLine();
                if(read==null){
                    return;
                }
                String[] stations = read.split(" ");//�����line��Ӧ������station
                line.setLineName(stations[0]);//������·��
                for (int j = 1; j < stations.length - 1; j++) {
                    station station1 = new station();//��ǰline�е�station
                    station station2 = new station();
                    if (Map.containsKey(stations[j])) {//���map���Ѿ��и�վ��������վ���ó�������
                        station1 = Map.get(stations[j]);
                        Map.remove(stations[j]);
                    } else {
                        station1.setStationName(stations[j]);
                        station1.setVisited(false);
                    }

                    if (Map.containsKey(stations[j + 1])) {//Map���Ѿ��и�վ�����վ���ó�������
                        station2 = Map.get(stations[j + 1]);
                        Map.remove(stations[j + 1]);
                    } else {
                        station2.setStationName(stations[j + 1]);
                        station2.setVisited(false);
                    }
                    if (!station1.getLine().contains(line.getLineName()))//�����ǰվδ����line�У�����line�е�ǰվ��
                        station1.AddStationLine(line.getLineName());
                    if (!station2.getLine().contains(line.getLineName()))
                        station2.AddStationLine(line.getLineName());
                    if (!station1.getLinkStations().contains(station2))
                        station1.AddLinkStation(station2);
                    if (!station2.getLinkStations().contains(station1))
                        station2.AddLinkStation(station1);

                    station1.setPreStation(station1.getStationName());
                    station2.setPreStation(station2.getStationName());
                    Map.put(stations[j], station1);//��station1�Ż�Map
                    Map.put(stations[j + 1], station2);//��station2�Ż�Map
                    if (!line.getStations().contains(station1.getStationName())) {
                        line.stationAdd(station1.getStationName());
                    }
                    if (!line.getStations().contains(station2.getStationName())) {
                        line.stationAdd(station2.getStationName());
                    }
                }
                LineSet.add(line);//����·����LineSet
            }
            read1.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void searchline(String line){ //������·���ֲ��Ҹ���·����վ��
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
            System.out.println("�õ�����·������");
        else {
            System.out.print(line + ": ");
            for (String str : LineSet.get(index).getStations()) {
                System.out.print(str + " ");
            }
        }
    }

    public static void BFS(String start,String end){//ʹ��BFS��������㷨����
        int sign = 0;
        if (!Map.containsKey(start)){//�ж�start�Ƿ����
            System.out.println("start������");
            sign = 1;
        }
        if (!Map.containsKey(end)){
            System.out.println("end������");//�ж�end�Ƿ����
            sign = 1;
        }
        for (String temp :Map.keySet()){
        	Map.get(temp).setVisited(false);//��ʼ������Ϊfalse
        	Map.get(temp).setDistance(0);//��ʼ������Ϊ0
        }
        station nowStation = new station();
        Queue<String> queue = new LinkedList<>();//��ű�������վ��

        nowStation = Map.get(start);
        queue.add(start);
        while(!queue.isEmpty()){
            String nowStationName = queue.poll();
            Map.get(nowStationName).setVisited(true);
            if (nowStation.getStationName().equals(end)){
                break;
            }
            for (station station1 :Map.get(nowStationName).getLinkStations()){
                if(!Map.get(station1.getStationName()).isVisited()){//δ���ʹ����ٽ�վ��
                	Map.get(station1.getStationName()).setPreStation(nowStationName);//ΪpreStation��ֵ
                	Map.get(station1.getStationName()).setDistance(Map.get(nowStationName).getDistance()+1);//�ٽ�վ�ľ���Ϊ��վ�����1
                    queue.offer(station1.getStationName());
                }
            }
        }
    }

    public static void path(String start,String end){
        if (start.equals(end)){
            System.out.print("���վ���յ�վ��ͬ ��վΪ��"+start);
            return;
        }
        List<String> path = new ArrayList<>();
        Stack<String> printline = new Stack<>();
        int numStation = 1;//���վ��
        String str = end;
        while(!str.equals(start)){
            path.add(str);
            printline.push(str);
            str = Map.get(str).getPreStation();
        }
        path.add(str);//��start����path
        printline.push(str);
        for (int i=1;i<path.size()-1;i++){
            if (Map.get(path.get(i)).getLine().size()==1){
                continue;
            }
            String temp1="";
            String temp2="";
            for (String str1 : Map.get(path.get(i)).getLine()){//��վ��ǰһվ�Ĺ�ͬӵ�е���·����temp1��
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
            for (String str1 : Map.get(path.get(i)).getLine()){//��һվ�뱾վ�Ĺ�ͬӵ�е���·����temp2��
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
            if (!temp1.equals(temp2))//��temp1��temp2����·��ͬ��վΪת��վ
            	Map.get(path.get(i)).setIfchange(true);
        }
        //�ж�path�еĻ���վ
        while(!printline.empty()){
            String printStation = printline.pop();
            if(numStation==1){
                for (String strnow : Map.get(printStation).getLine()){
                    for (String nextStation : Map.get(path.get(path.size()-numStation-1)).getLine()){
                        if (strnow.equals(nextStation)) {
                            System.out.println("��"+strnow+"����");

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
                System.out.println("ת "+nowline);
            }
            System.out.print(printStation+" ");
            numStation++;
        }
        
        numStation =numStation-1;
        System.out.println();
        System.out.println("һ��"+numStation+"վ");//������һ��������վ��
    }

    public static void main(String[] ards){
        readSubway("E:\\ԭ����\\������\\�������\\����.txt");
        System.out.println("1.��ѯĳ����·������վ��");
        System.out.println("2.��ѯĳվ��ĳվ�������·");
        System.out.print("���������(1����2):");
        Scanner Scanner = new Scanner(System.in);
        int input = Scanner.nextInt();
        if (input==1){
            System.out.print("�����������·���ƣ�");
            Scanner Scanner1 = new Scanner(System.in);
            String linename = Scanner1.nextLine();
            searchline(linename);
        }
        else if (input==2){
            System.out.print("start��");
            Scanner Scanner2 = new Scanner(System.in);
            String start =Scanner2.nextLine();
            System.out.print("end��");
            String end = Scanner2.nextLine();
            BFS(start,end);
            path(start,end);
        }
    }
}
