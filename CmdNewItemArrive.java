public class CmdNewItemArrive extends RecordedCommand {

    private Item i;

    @Override
    public void execute(String[] cmdParts) throws ExceptionUsedIdItem, ExInsufficientArgs {

        if (cmdParts.length < 3){
            throw new ExInsufficientArgs();
        }

        if (Item.itemIdUsed(cmdParts[1]) == false){
            i = new Item(cmdParts[1],cmdParts[2]);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        }
        else{
            throw new ExceptionUsedIdItem();
        }
    }


    @Override
    public void undoMe() {
        Club c = Club.getInstance();
        c.removeItem(i);      
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club c = Club.getInstance();
        c.addItem(i);
        addUndoCommand(this);

    }

}
