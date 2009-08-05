import goMoku.Controller.GoMokuConsoleGame;
import goMoku.Controller.GoMokuGameType;
import goMoku.Controller.InvalidCommandLineException;
import goMoku.Controller.UserAbortException;
import goMoku.View.GoMokuConsoleView;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	GoMokuConsoleView view = new GoMokuConsoleView();
    	
    	view.showWelcome();
    	
        try {	
	
	        GoMokuGameType gameType = parseCommandLineParams(args);
	        	
	        GoMokuConsoleGame flow = new GoMokuConsoleGame(gameType, view);
	        
	        flow.Start();
	        

    	} catch (InvalidCommandLineException e) {
    		view.outputMessage(e.toString());
    		view.showGameUsage();
    	} catch (UserAbortException e) {
    		
    	}
    	
    	view.showGoodbye();
    }
    
    /**
     * 
     * @param args is the command line arguments
     * @return the game type as specified in the command line
     * @throws InvalidCommandLineException in case of invalid command line parameters
     * 			(wrong parameters or no parameters) 
     */
    public static GoMokuGameType parseCommandLineParams(String args[]) throws InvalidCommandLineException {
    	
    	if (args.length == 0) {
    		throw new InvalidCommandLineException("No parameters were entered");
    	}
    	
    	if (args.length > 1) {
    		throw new InvalidCommandLineException("Too many parameters were entered");
    	}
    	
    	String commandline = args[0].toUpperCase();
    	
    	/* 
    	 * iterate over the game types and compare the user command line 
    	 * to game type's command line 
    	 */
    	for (GoMokuGameType gameType : GoMokuGameType.values()) {
    		if (gameType.getCmdline().compareTo(commandline) == 0) {
    			return gameType;
    		}
    	}
    	
        // the specified command line argument didn't match to any of the game types
        throw new InvalidCommandLineException("Wrong parameters");
        
    }
    
}

