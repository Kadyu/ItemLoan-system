import java.util.ArrayList;
import java.util.Iterator;

public class CmdStartNewDay extends RecordedCommand{

    private String sDay1, sDay2;
    public static ArrayList <Item> dueDatesItems = new ArrayList<>();
    Iterator<Item> itr = dueDatesItems.iterator();
    private Member member;
    
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArgs {

        if (cmdParts.length < 2){
            throw new ExInsufficientArgs();
        }

        SystemDate sd = SystemDate.getInstance();
        sDay1 = sd.toString();
        sDay2 = cmdParts[1];
        sd.set(sDay2);


        while(itr.hasNext()){
            
            Item item = itr.next();

            if (Day.isExpiredDate(sDay2, item.getDueDate())){

                System.out.println("On hold period is over for " + item.getID() + " " + item.getName() + ".");
                
                if (item.getItemReqNum() == 0){    
                    item.myStatus(new ItemStatusAvailable(), null, null, null);
                    itr.remove();                    
                }
                else {
                    member = item.getNextReqMember();
                    member.requestSet(-1);
                    item.setItemReqNum(-1);
                    item.myStatus(new ItemStatusOnhold(), member, null, sd.getNextDay());
                    item.removeMemReq(member);

                    System.out.println("Item [" + item.getID() + " " + item.getName() + "] is ready for pick up by [" + member.getId() + 
                    " " + member.getName() + "].  On hold due on " + sd.getNextDay() + ".");

                    item.itemDueDate(sd.getNextDay());  
                }
            }
        }

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        SystemDate sd = SystemDate.getInstance();
        sd.set(sDay1);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {  
        SystemDate sd = SystemDate.getInstance();
        sd.set(sDay2);
        addUndoCommand(this);   
    }

    public static void addNewDueDateItem(Item item) {
        dueDatesItems.add(item);
    }

    public static void removeNewDueDateItem(Item item) {
        dueDatesItems.remove(item);
    }


    

}


    