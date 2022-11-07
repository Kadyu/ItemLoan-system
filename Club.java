import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Club{

	private ArrayList<Member> allMembers;	
	private ArrayList<Item> allItems;

	private static Club instance = new Club(); 

	private Club() { 
		allMembers = new ArrayList<>(); 
		allItems = new ArrayList<>();
	}

	public static Club getInstance() { return instance; }

	public void addMember(Member m) {// See how it is called in Member constructor (Step 3)
		allMembers.add(m);
		Collections.sort(allMembers); // allMembers
	}
	
	public void listClubMembers() {
		System.out.println(Member.getListingHeader()); // Member

		for (Member m: allMembers)
			System.out.println(m); // m or m.toString()
	}

	public void removeMember(Member m) {
		allMembers.remove(m); //.remove is a method of ArrayList
		}	

	public void addItem(Item i) {
			allItems.add(i);
			Collections.sort(allItems); 
		}

	public void listItemsClubMembers() {
			System.out.println(Item.getListingHeader()); 
			for (Item i: allItems){
				System.out.println(i); 
			}
		}
	
	public void removeItem(Item i) {
			allItems.remove(i); 
		}
}