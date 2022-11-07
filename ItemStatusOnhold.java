public class ItemStatusOnhold implements ItemStatus{



    @Override
    public void setStatus(Item item, Member member, Day day, String nextday) {
        item.setStatus("On holdshelf for " + member.getId()+ " " + member.getName() + " until " + nextday);  
        item.setBorrowedDay(null); 
        item.setBorrowedMember(member);
        item.setOnSelfMember(member);
        item.itemDueDate(nextday);
        
    }
    
}
