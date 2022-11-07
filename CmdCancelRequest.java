import java.util.ArrayList;

public class CmdCancelRequest extends RecordedCommand{

    private Member member;
    private Item item;
    private ArrayList<Member> old_list = new ArrayList<>();
    private ArrayList<Member> new_list = new ArrayList<>();

    public void execute(String[] cmdParts) throws ExItemNotFound, ExMemberNotFound, ExReqRecordNotFound, ExInsufficientArgs {

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
        else if (item.memberAlreadyReq(cmdParts[1]) == false){
            throw new ExReqRecordNotFound();
        }
        else{
            
            old_list.addAll(item.getReqMemList());

            item.removeMemReq(member);
            item.setItemReqNum(-1);
            member.requestSet(-1);

            new_list.addAll(item.getReqMemList());
            

            System.out.println("Done.");
            addUndoCommand(this);
            clearRedoList();
        } 
    }

    @Override
    public void undoMe() {

        item.undoReqMemberList(old_list);
        item.setItemReqNum(1);
        member.requestSet(1);

        addRedoCommand(this); 
    }

    @Override
    public void redoMe() {

        item.undoReqMemberList(new_list);
        item.removeMemReq(member);
        item.setItemReqNum(-1);
        member.requestSet(-1);
        
        addUndoCommand(this);
        
    }
    
}
