public class ItemStatusAvailable implements ItemStatus{

    @Override
    public void setStatus(Item item, Member member, Day day, String nextday) {
        item.setStatus("Available");
        item.setBorrowedDay(null);
        item.setBorrowedMember(null);
        item.setBorrowedDay(null);        
    }
    
}
