package gomokuconsole;

import goMoku.Controller.GoMokuGame;
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
        IGoMokuView view = new GoMokuConsoleView();
        GoMokuGame game = new GoMokuGame(view);
    }

}
