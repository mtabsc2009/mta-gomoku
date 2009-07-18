package main;

import goMoku.Controller.GoMokuConsoleGame;
import goMoku.Controller.GoMokuGame;
import goMoku.Controller.GoMokuGameType;
import goMoku.View.GoMokuConsoleView;
import goMoku.View.IGoMokuView;

/**
 *
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GoMokuConsoleView view = new GoMokuConsoleView();
//        GoMokuGame game = new GoMokuGame(view);

        boolean paramsOK = true;
        String  gameTypeString = null;
        GoMokuGameType gameType = GoMokuGameType.ComputerVSComputer;

        if (args.length == 0) {
            paramsOK = false;
        } else
        {
            gameTypeString = args[0].toUpperCase();
            if (gameTypeString.compareTo(GoMokuConsoleView.USERVSUSER) == 0){
                gameType = GoMokuGameType.UserVSUser;
            }else if (gameTypeString.compareTo(GoMokuConsoleView.COMPUTERVSCOMPUTER) == 0){
                gameType = GoMokuGameType.ComputerVSComputer;
            }else if (gameTypeString.compareTo(GoMokuConsoleView.COMPUTERVSUSER) == 0){
                gameType = GoMokuGameType.ComputerVSUser;
            }else if (gameTypeString.compareTo(GoMokuConsoleView.USERVSCOMPUTER) == 0){
                gameType = GoMokuGameType.UserVSComputer;
            }
            else {
                paramsOK = false;
            }
        }

        if (paramsOK){
            GoMokuConsoleGame flow = new GoMokuConsoleGame(gameType, view);
            flow.Start();

        } else {
            view.ShowGameTypeError();
        }
    }

}
