package main;

import goMoku.Controller.GoMokuConsoleGame;
import goMoku.Controller.GoMokuGameType;
import goMoku.Controller.InvalidCommandLineException;
import goMoku.View.GoMokuConsoleView;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	GoMokuConsoleView view = new GoMokuConsoleView();
    	
        try {	
	
	        GoMokuGameType gameType = parseCommandLineParams(args);
	        	
	
	        GoMokuConsoleGame flow = new GoMokuConsoleGame(gameType, view);
	        flow.Start();

    	} catch (InvalidCommandLineException e) {
    		view.outputMessage(e.toString());
    		view.showGameUsage();
    	}
    }
    
    public static GoMokuGameType parseCommandLineParams(String args[]) throws InvalidCommandLineException {
    	

    	if (args.length == 0) {
    		throw new InvalidCommandLineException("No parameters were entered");
    	}
    	
    	GoMokuGameType gameType;
    	String gameTypeString = args[0].toUpperCase();
    	
    	
        if (gameTypeString.compareTo(GoMokuConsoleView.USERVSUSER) == 0){
            gameType = GoMokuGameType.UserVSUser;
        } else if (gameTypeString.compareTo(GoMokuConsoleView.COMPUTERVSCOMPUTER) == 0){
            gameType = GoMokuGameType.ComputerVSComputer;
        } else if (gameTypeString.compareTo(GoMokuConsoleView.COMPUTERVSUSER) == 0){
            gameType = GoMokuGameType.ComputerVSUser;
        } else if (gameTypeString.compareTo(GoMokuConsoleView.USERVSCOMPUTER) == 0){
            gameType = GoMokuGameType.UserVSComputer;
        } else {
        	throw new InvalidCommandLineException("Wrong parameters");
        }
        
        return gameType;
    }
    
}

