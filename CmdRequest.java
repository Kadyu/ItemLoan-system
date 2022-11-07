public class CmdRequest extends RecordedCommand{
    private Item item;
    private Member member;


    @Override
    public void execute(String[] cmdParts) throws ExMemberNotFound, ExItemNotFound, ExItemQuotaExceed, ExMemAlreadyReq, ExAlreadyBorrowed, ExItemAvail, ExInsufficientArgs {

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
            if (item.getStatus().equals("Available")){
                throw new ExItemAvail();
            }
            if (item.memberAlreadyReq(cmdParts[1])){
                throw new ExMemAlreadyReq();
            }
            if (this.member.getRequestNum() >= 3) {
                throw new ExItemQuotaExceed();
            } 
            if (item.getBorrowedMember().getName().equals(member.getName()) && item.getOnShelfMember() == null){
                throw new ExAlreadyBorrowed();
            }
            else if (item.getOnShelfMember() != null && member.getName().equals(item.getOnShelfMember().getName()))  {
                throw new ExItemAvail();
            }
            else{
                member.requestSet(1); 
                item.addReqMember(member); 
                item.setItemReqNum(1);
                System.out.println("Done. This request is no. " + this.item.getItemReqNum()+ " in the queue.");
    
                addUndoCommand(this);
                clearRedoList();
            }
        }
    }

    @Override
    public void undoMe() {
        item.removeMemReq(member);
        member.requestSet(-1); 
        item.setItemReqNum(-1);
        addRedoCommand(this); 
    }

    @Override
    public void redoMe() {
        member.requestSet(1); 
        item.addReqMember(member); 
        item.setItemReqNum(1);
        addUndoCommand(this);
        
    }
    

}
