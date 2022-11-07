public class ExQuotaExceed extends Exception{
    public ExQuotaExceed(){
        super();
    }

    public ExQuotaExceed(String msg){
        super(msg);
    }
}