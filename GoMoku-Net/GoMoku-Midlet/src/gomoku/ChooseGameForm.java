/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import javax.microedition.lcdui.*;
import gomoku.NetworkAdapter.GoMokuGameLogic;
import j2mehelper.string.StringSplitter;
        

public class ChooseGameForm extends Form implements CommandListener  {
    ChoiceGroup gameChoiceMenu;
    String[] playerList = null;
    ChooseGameForm(String players) {
        super("Choose game");
        if (players==null) {
            throw new NullPointerException( "ChooseGameForm: players list cannot be null");
        }
        
        
        if(players.equals(GoMokuGameLogic.PROTOCOL_NO_CLIENTS)) {
            append(new StringItem("", "No players connected. Wait for an offer or reconnect"));
            return;
        }
         gameChoiceMenu = new ChoiceGroup("Select game ",ChoiceGroup.EXCLUSIVE);
        
        if(!players.equals(GoMokuGameLogic.PROTOCOL_NO_CLIENTS)) {
            playerList = StringSplitter.split(players, GoMokuGameLogic.PROTOCOL_CLIENT_SEPARATOR);

            int i;
            for (i = 0 ;i < playerList.length ; ++i) {
                gameChoiceMenu.append(playerList[i], null);
                System.out.println("DEBUG: Choice menu: adding:"+playerList[i] + " " + playerList[i].length());
                        
            }
        }
        
        append(gameChoiceMenu);
    } // end of c'tor

    public void commandAction(Command arg0, Displayable arg1) {

    }
   
    public String getOpponent() {
        if (gameChoiceMenu == null) {
            return null;
        }
        
        return playerList[gameChoiceMenu.getSelectedIndex()];
        
    }
}
