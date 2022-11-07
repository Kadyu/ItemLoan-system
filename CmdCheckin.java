import java.util.ArrayList;

public class CmdCheckin extends RecordedCommand{


    private String oldStatus;
    private Item item;  
    private Member member;
    private Member memberBorrowed;
    private Day borrowedDay;
    private String nextDay;
    private Member nextMember;
    private ArrayList<Member> old_list = new ArrayList<>();
    

    @Override
    public void execute(String[] cmdParts) throws ExMemberNotFound, ExItemNotFound, ExNotBorrowed, ExInsufficientArgs {

        if (cmdParts.length < 3){
            throw new ExInsufficientArgs();
        }
        
        this.member = Member.getMember(cmdParts[1]); 
        this.item = Item.getItem(cmdParts[2]);
        
        if (member == null){
            throw new ExMemberNotFound();
        }
        else if (item ==  null){
            throw new ExItemNotFound();
        }
        else{

            this.memberBorrowed = item.getBorrowedMember();
            this.borrowedDay = SystemDate.getInstance();

            oldStatus = item.getStatus();

            if (member.getId().equals(memberBorrowed.getId()) == false){
                throw new ExNotBorrowed();
            }
            else{
                member.borrowSet(-1);
                old_list.addAll(item.getReqMemList());
                
                
                if (item.getItemReqNum() != 0 ) {

                    nextDay = SystemDate.getInstance().getNextDay();
                    nextMember = item.getNextReqMember();

                    System.out.println("Item [" + item.getID() + " " + item.getName() + "] is ready for pick up by [" + nextMember.getId() + 
                    " " + nextMember.getName() + "].  On hold due on " + nextDay +".");

                    item.itemDueDate(nextDay);
                    CmdStartNewDay.addNewDueDateItem(item);
                    
                    nextMember.requestSet(-1);
                    item.setItemReqNum(-1);
                    item.myStatus(new ItemStatusOnhold(), item.getNextReqMember(), null, nextDay);
                    item.removeMemReq(nextMember);

                } else {
                    item.myStatus(new ItemStatusAvailable(), null, null, null);  
                                    
                }

                
                System.out.println("Done.");
                addUndoCommand(this);
                clearRedoList();
            }

        }
    }

    @Override
    public void undoMe() {

        if (oldStatus.charAt(0) == 'A'){
            item.myStatus(new ItemStatusAvailable(),null, null, null);
            
        }
        else if(item.getStatus().charAt(0) == 'O'){

            System.out.println("Sorry. "  + nextMember.getId() + " " + nextMember.getName() +
            " please ignore the pick up notice for "+ item.getID() + " " + item.getName() + ".");

            item.itemDueDate(null);
            CmdStartNewDay.removeNewDueDateItem(item);

            nextMember.requestSet(1);
            item.setItemReqNum(1);
            item.undoReqMemberList(old_list);
            
            item.myStatus(new ItemStatusBorrowed(),memberBorrowed, borrowedDay ,null);

        }
        else{
            
            item.undoReqMemberList(old_list);
            item.myStatus(new ItemStatusBorrowed(),memberBorrowed, borrowedDay ,null);
        }
        this.member.borrowSet(1);
        addRedoCommand(this); 
    }

    @Override
    public void redoMe() {

        item.undoReqMemberList(old_list);
        

        if (item.getReqMemList().size() == 0 ){
            item.myStatus(new ItemStatusAvailable(),null, null, null);
        }
        else{

            System.out.println("Item [" + item.getID() + " " + item.getName() + "] is ready for pick up by [" + nextMember.getId() + 
            " " + nextMember.getName() + "]. On hold due on " + nextDay + ".");

            item.itemDueDate(nextDay);
            CmdStartNewDay.addNewDueDateItem(item);
            nextMember.requestSet(-1);
            item.setItemReqNum(-1);
            item.myStatus(new ItemStatusOnhold(), item.getNextReqMember(), null, nextDay);
            item.removeMemReq(nextMember);

        }


        this.member.borrowSet(-1);
        addUndoCommand(this);
    }


}
