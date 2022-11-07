public class CmdRegister extends RecordedCommand {

    private Member m;
    

    @Override
    public void execute(String[] cmdParts) throws ExceptionUsedIdMember, ExInsufficientArgs{
        
        if (cmdParts.length < 3){
            throw new ExInsufficientArgs();
        }

        if (Member.memberIdUsed(cmdParts[1]) == false){
            m = new Member(cmdParts[1], cmdParts[2]);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        }
        else{
            throw new ExceptionUsedIdMember();
        } 
        
    }

    @Override
    public void undoMe() {
        Club c = Club.getInstance();
        c.removeMember(m);      
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club c = Club.getInstance();
        c.addMember(m);
        addUndoCommand(this);

    }

}
