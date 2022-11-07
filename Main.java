import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
 

public class Main {

	public static void main(String [] args) throws FileNotFoundException, ExUnknownCommand {	
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		Scanner inFile = null;

			inFile = new Scanner(new File(filepathname));
		
			//The first command in the file must be to set the system date 
			//(eg. "startNewDay 03-Jan-2021"); and it cannot be undone
			String cmdLine1 = inFile.nextLine();
			String[] cmdLine1Parts = cmdLine1.split(" ");
			System.out.println("\n> "+cmdLine1);
			SystemDate.createTheInstance(cmdLine1Parts[1]);
			
			while (inFile.hasNext()) {
				String cmdLine = inFile.nextLine().trim();
				
				//Blank lines exist in data file as separators.  Skip them.
				if (cmdLine.equals("")) continue;  

				System.out.println("\n> "+cmdLine);
				
				//split the words in actionLine => create an array of word stringstes
				String[] cmdParts = cmdLine.split(" "); 
			
			try{
				if (cmdParts[0].equals("register"))
					(new CmdRegister()).execute(cmdParts);
				else if (cmdParts[0].equals("listMembers"))
					(new CmdListMembers()).execute(cmdParts);
				else if (cmdParts[0].equals("startNewDay"))
					(new CmdStartNewDay()).execute(cmdParts);
				else if (cmdParts[0].equals("arrive"))
					(new CmdNewItemArrive()).execute(cmdParts);	
				else if (cmdParts[0].equals("listItems"))
					(new CmdListAllItems()).execute(cmdParts);
				else if (cmdParts[0].equals("checkout"))
					(new CmdCheckout()).execute(cmdParts);	
				else if (cmdParts[0].equals("checkin"))
					(new CmdCheckin()).execute(cmdParts);	
				else if (cmdParts[0].equals("request"))
					(new CmdRequest()).execute(cmdParts);	
				else if (cmdParts[0].equals("cancelRequest"))
					(new CmdCancelRequest()).execute(cmdParts);									
				else if (cmdParts[0].equals("undo"))
					RecordedCommand.undoOneCommand();
				else if (cmdParts[0].equals("redo"))
					RecordedCommand.redoOneCommand();
				else 
					throw new ExUnknownCommand();

			} catch (ExceptionUsedIdMember e) {
				System.out.print("Member ID already in use: ");
				System.out.print(Member.getCurrIdUser() + '\n');
			} catch(ExceptionUsedIdItem e){
				System.out.print("Item ID already in use: ");
				System.out.print(Item.getCurrIdUser() + "\n");
			} catch(ExMemberNotFound e){
				System.out.print("Member not found.\n");
			} catch(ExItemNotFound e){
				System.out.print("Item not found.\n");
			} catch(ExItemBorrowed e){
				System.out.print("Item not available.\n");			
			} catch(ExQuotaExceed e){
				System.out.print("Loan quota exceeded.\n");
			} catch(ExNotBorrowed e){
				System.out.print("The item is not borrowed by this member.\n");
			} catch(ExItemQuotaExceed e){
				System.out.print("Item request quota exceeded.\n");
			} catch(ExMemAlreadyReq e){
				System.out.print("The same member has already requested the item.\n");
			} catch(ExAlreadyBorrowed e){
				System.out.print("The item is already borrowed by the same member.\n");
			} catch(ExItemAvail e){
				System.out.print("The item is currently available.\n");
			}catch(ExReqRecordNotFound e){
				System.out.print("Request record is not found.\n");
			} catch(ExUnknownCommand e){
				System.out.print("Unknown command - ignored.\n");
			} catch(ExInsufficientArgs e){
				System.out.print("Insufficient command arguments.\n");
			}

		}
		inFile.close();
		in.close();
	}
}
		
