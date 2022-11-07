public class CmdCheckout extends RecordedCommand{

    private Item item;
    private Member member;
    private Day borrowDate;
    private String oldStatus;
    private int quota;
    private String nextDay;
    
    @Override
    public void execute(String[] cmdParts) throws ExMemberNotFound, ExItemNotFound, ExItemBorrowed, ExQuotaExceed, ExInsufficientArgs {
        
        if (cmdParts.length < 3){
            throw new ExInsufficientArgs();
        }

        member = Member.getMember(cmdParts[1]); 
        item = Item.getItem(cmdParts[2]); 
        
        if (member == null){
            throw new ExMemberNotFound();
        }
        else if (item ==  null){
            throw new ExItemNotFound();
        }
        else{
            quota = member.getQuota();
            oldStatus = item.getStatus();


            if (oldStatus.charAt(0) == 'O'){
                String[] line = oldStatus.split(" ");
                nextDay = line[6];
            }

            if (quota >= 6){
                throw new ExQuotaExceed();
            }
            else if (oldStatus.charAt(0) == 'B'){
                throw new ExItemBorrowed();
            }
            else if(oldStatus.charAt(0) == 'O' && item.getOnShelfMember().getName().equals(member.getName()) == false){
                throw new ExItemBorrowed();
            }
            else{
                member.borrowSet(1);
                borrowDate = SystemDate.getInstance().clone();      
                item.myStatus(new ItemStatusBorrowed(),member, borrowDate, null);
            
                System.out.println("Done.");
                item.removeMemReq(member);
                addUndoCommand(this);
                clearRedoList(); 
            }
        }
    }

    @Override
    public void undoMe() {

        if (oldStatus.charAt(0) == 'O'){
            
            item.itemDueDate(nextDay);
            CmdStartNewDay.addNewDueDateItem(item);
            
            item.setItemReqNum(-1);
            item.myStatus(new ItemStatusOnhold(), member, null, nextDay);
            item.removeMemReq(member);
            
        }
        else{
            item.myStatus(new ItemStatusAvailable(),null, null, null);
        } 
        
        member.borrowSet(-1);
        addRedoCommand(this);        
    }

    @Override
    public void redoMe() {
    
        item.myStatus(new ItemStatusBorrowed(),member, borrowDate, null);
        member.borrowSet(1);
        item.removeMemReq(member);
        
        addUndoCommand(this);
    }


}
