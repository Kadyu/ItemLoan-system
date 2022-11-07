public class ItemStatusBorrowed implements ItemStatus{

    @Override
    public void setStatus(Item item, Member member, Day day, String nextday) {
        item.setBorrowedDay(day);
        item.setBorrowedMember(member);
        item.setStatus("Borrowed by " + member.getId() + " " + member.getName() + " on " + day.toString());
        
    }
    
}
