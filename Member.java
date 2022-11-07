import java.util.ArrayList;

public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private int quota;
    private int request;
    private static ArrayList <Member> memList = new ArrayList<>();

    public Member(String id, String name) {	
        this.id = id;
        this.name = name;
        this.quota = 0;
        this.request = 0;
        this.joinDate = SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
        memList.add(this);
    }

    @Override
    public String toString() {
        // Learn: "-" means left-aligned
        return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDate, quota, request);
    }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
    }
    //

    public int compareTo(Member another) {
        if (this.id.equals(another.id))
            return 0;
        else if (this.id.compareTo(another.id) > 0)
            return 1;
        else
            return -1;
    }


    /// my methods 
    private static Member curr_id_member;

    public static boolean memberIdUsed(String id_search) {
        for (Member m: memList){
            if (m.id.equals(id_search)){
                curr_id_member = m;
                return true;
            }
        }
        return false;
    }

    public static String getCurrIdUser(){
        return curr_id_member.id + " " + curr_id_member.name;
    }

    public static Member getMember(String id) {
        for (Member m: memList){
            if (m.id.equals(id)){
                return m;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void borrowSet(int i) {
        this.quota  += i;
    }

    public int getQuota() {
        return this.quota;
    }

    public static Member searchMember(String id) {
        for (Member m: memList){
            if (m.getId().equals(id)){
                return m;
            }
        }
        return null;
    }

    public void requestSet(int i) {
        this.request += i;
    }

    public int getRequestNum(){
        return this.request;
    }
}
