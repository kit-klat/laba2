package search;

public class Item {
    private String city;
    private String street;
    private int house;
    private int floor;
    public Item(String city, String street, int house, int floor) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.floor = floor;
        
    }  
    public String GetCity(){
        return city;
    }
    public int GetFloor(){
        return floor;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(city);
        sb.append(" ");
        sb.append(street);
        sb.append(" ");
        sb.append(house);
        sb.append(" ");
        sb.append(floor);
        return sb.toString();
    }
}
