public interface Command {
    void execute(String[] cmdParts) throws ExceptionUsedIdMember, ExceptionUsedIdItem, ExMemberNotFound, ExItemNotFound, ExItemBorrowed, ExQuotaExceed, ExNotBorrowed, ExItemQuotaExceed, ExMemAlreadyReq, ExAlreadyBorrowed, ExItemAvail, ExReqRecordNotFound, ExInsufficientArgs;
}
