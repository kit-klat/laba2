package search;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;
//C:/Users/bykov/Downloads/address.csv
//C:/Users/bykov/Downloads/address.xml
public class App{
    public static void main( String[] args ) throws Exception{
        Scanner scan=new Scanner(System. in, "UTF-8");
        Scanner scanner = new Scanner(System.in, "UTF-8");

while (true) {
    System.out.print("1. csv; 2. xml 3. exit ");
    int vabr = Integer.parseInt(scanner.nextLine());
            
            if (vabr==3) {
                System.out.println("Вы вышли!!!");
                System.exit(0);
            }
        if (vabr==1){
        System.out.print("введите путь файла: ");
        String file = scan.nextLine();
            List<String> addressList = new ArrayList<String>();
            Map<String,Integer> adressHashMap = new HashMap<String,Integer>();
            Map<String,Integer> countHouses = new HashMap<String,Integer>();
            long time = System.currentTimeMillis();
            
            read(file, addressList);
            Count(addressList, adressHashMap);
            dublicate(addressList, countHouses);
            System.out.println(System.currentTimeMillis() - time);
            
        }
        if (vabr==2)
        {
            
            System.out.print("введите путь файла: ");
        String file = scan.nextLine();
        List<Item> addressList = new ArrayList<Item>();
        Map<String, Integer> addressHashMap = new HashMap<>();
            Map<String, Integer>  countHouses = new HashMap<String,Integer>();
            long time = System.currentTimeMillis();
            
            Read_XML(file,addressList,addressHashMap ,countHouses);
            
           
findDuplicates(addressList, addressHashMap);
 CountHouses(addressList,countHouses);
            System.out.println(System.currentTimeMillis() - time);
        }


    }
    }
    
    
    public static void Read_XML(String file, List<Item> itemList, Map<String, Integer> itemDuplicates, Map<String, Integer> countHouses) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
    
            doc.getDocumentElement().normalize();
    
            NodeList nodeList = doc.getElementsByTagName("item");

for (int i = 0; i < nodeList.getLength(); i++) {
    org.w3c.dom.Node node = nodeList.item(i);
    if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        String city = element.getAttribute("city");
        String street = element.getAttribute("street");
        int house = Integer.parseInt(element.getAttribute("house"));
        int floor = Integer.parseInt(element.getAttribute("floor"));

        Item item = new Item(city, street, house, floor);
        itemList.add(item);
    }
}
    
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void findDuplicates(List<Item> itemList, Map<String, Integer> addressHashMap) {
        if (itemList != null && addressHashMap != null) {
            for (final Item item : itemList) {
                Integer count = addressHashMap.get(item.toString());
            addressHashMap.put(item.toString(), (count == null) ? 1 : count + 1);
            }
            printMap_1(addressHashMap);
        }
    }
    private static void CountHouses(List<Item> itemList,  Map<String,Integer> countHouses) {
        if (itemList != null && countHouses != null) {
            for (final Item item : itemList) {
            String key = (item.GetCity()+", этаж: "+item.GetFloor());
            
            Integer count = countHouses.get(key);
            countHouses.put(key, (count == null) ? 1 : count + 1);
        }
    }
        printMap_2(countHouses);
    }

   
    private static void Count(List<String> addressListArr, Map<String, Integer> countHouses) {
        for (final String line : addressListArr) {
            final String[] splStrings = line.split(";");
            String key = (splStrings[0]+splStrings[3]).replace("\"", ", этаж: ");
            Integer count = countHouses.get(key);
            countHouses.put(key, (count == null) ? 1 : count + 1);
        }
        printMap_2(countHouses);
    }
    private static void dublicate(List<String> addressList, Map<String, Integer> adressHashMap)
    {
        for (String string:addressList)
        {
            if(adressHashMap.keySet().contains(string))
            {
                adressHashMap.put(string,adressHashMap.get(string)+1);
            }
            else
            {
                adressHashMap.put(string,1);
            }
        }
        printMap_1(adressHashMap);
    }
    public static void printMap_1(Map<String, Integer> map){
        
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() !=1){
                    System.out.println("Строка: " + entry.getKey() + 
                    ", Повторений: " + entry.getValue());
                }
            }
        }
 public static void printMap_2(Map<String, Integer> map){
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Город: " + entry.getKey() + 
            ", кол-во домов: " + entry.getValue());
        }
    }
        public static void read(String file, List<String> addressListArr) {
            try {
               
                InputStreamReader filereader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
        
                String[] nextRecord;
               
                while ((nextRecord = csvReader.readNext()) != null) {
                    for (String cell : nextRecord) {
                        addressListArr.add(cell);
                    }
                }
        
                csvReader.close();
                filereader.close();
        
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }
        }
    }

