public class CmdListAllItems implements Command{
    @Override
    public void execute(String[] cmdParts) {
        Club i = Club.getInstance();
        i.listItemsClubMembers();
        
    }
}
