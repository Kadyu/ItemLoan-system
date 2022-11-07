public class ExInsufficientArgs extends Exception{
    public ExInsufficientArgs(){
        super();
    }

    public ExInsufficientArgs(String msg){
        super(msg);
    }
}