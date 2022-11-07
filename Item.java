import java.util.ArrayList;

public class Item implements Comparable<Item> {

    private String id;
    private String name;
    private Day arrivalDate;
    private String status;
    private int numOfRequests;
    private String dueDate;
    private static ArrayList <Item> itemList = new ArrayList<>();
    private ArrayList<Member> requestMemList;
    private Member onShelfMember;
    private Member borrowedMember;
    private Day borrowedDay;
    private static Item curr_id_item;

    public Item(String id, String name){
        this.id = id;
        this.name = name;
        this.arrivalDate = SystemDate.getInstance().clone();
        this.status = "Available";
        this.borrowedMember = null;
        this.borrowedDay = null;
        this.dueDate = null;
        this.numOfRequests = 0;
        this.requestMemList = new ArrayList<>();
        Club.getInstance().addItem(this);
        itemList.add(this);
    }

    @Override
    public int compareTo(Item another) {
        if (this.id.equals(another.id))
            return 0;
        else if (this.id.compareTo(another.id) > 0)
            return 1;
        else
            return -1;
    }

    public static String getListingHeader() {
        return String.format("%-5s%-17s%11s   %s", "ID", "Name", "  Arrival  ", "Status");
    }

    @Override
    public String toString() {
        if (requestMemList.size() == 0){
            return String.format("%-5s%-17s%11s   %s", id, name, arrivalDate, status);
        }
        else{
            String line = String.format("%-5s%-17s%11s   %s", id, name, arrivalDate, status);
            line += " + " + requestMemList.size() + " request(s):" ;
            for (Member m: requestMemList){
                line += "  " + m.getId();
            } 
            return line;
            
        }
    }

    /// my code

    public static boolean itemIdUsed(String id_search) {
        for (Item i: itemList){
            if (i.id.equals(id_search)){
                curr_id_item = i;
                return true;
            }
        }
        return false;
    }

    public static String getCurrIdUser(){
        return curr_id_item.id + " " + curr_id_item.name;
    }


    public static Item getItem(String id){
        for (Item i: itemList){
            if (i.id.equals(id)){
                return i;
            }
        }
        return null;
    }

    public String getStatus() {
        return this.status;
    }

    public Member getBorrowedMember(){
        return this.borrowedMember;
    }

    public Day getBorrowedDay(){
        return this.borrowedDay;
    }

    public void addReqMember(Member member) {
        this.requestMemList.add(member);
    }

    public void undoReqMemberList(ArrayList<Member> list){
        this.requestMemList = list;
    }

    public boolean memberAlreadyReq(String id){
        for (Member m: requestMemList){
            if (m.getId().equals(id)){return true;} 
        }
        return false;
    }

    public int getItemReqNum(){
        return numOfRequests;
    }

    public void setItemReqNum(int i){
        numOfRequests += i;
    }

    public void removeMemReq(Member m){
        this.requestMemList.remove(m);
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return id;
    }

    public Member getNextReqMember(){
        Member m = requestMemList.get(0); 
        return m;
    }

    public void itemDueDate(String date) {
        this.dueDate = date;
    }

    public String getDueDate(){
        return this.dueDate;
    }

    public Member getOnShelfMember(){
        return onShelfMember;
    }

    public void setBorrowedDay(Day day){
        this.borrowedDay = day;
    }

    public void setBorrowedMember(Member member){
        this.borrowedMember = member;
    }

    public void setOnSelfMember(Member member) {
        this.onShelfMember = member;
    }

    public void setStatus(String stat) {
        this.status = stat;
    }

    public void myStatus(ItemStatus s, Member member, Day day, String nextday){
        s.setStatus(this,member, day,nextday);
    }

    public ArrayList<Member> getReqMemList(){
        return requestMemList;
    }
        
}
