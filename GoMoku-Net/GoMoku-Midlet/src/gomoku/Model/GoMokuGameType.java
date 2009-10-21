/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.Model;

/**
 * Holds the supported game types:
 * <ol>
 * <li> Computer-Computer
 * <li> Computer-User
 * <li> User-Computer
 * <li> User-User
 * </ol>
 * <p>
 * Each game type has a corresponding command line argument for requesting this type.  
 * <p>
*/
public class GoMokuGameType {
    public static int ComputerVSUserId = 0;
    public static int UserVSComputerId = 1;
    public static int UserVSUserId = 2;
    
    public static GoMokuGameType ComputerVSUser = new GoMokuGameType(ComputerVSUserId);
    public static GoMokuGameType UserVSComputer = new GoMokuGameType(UserVSComputerId);
    public static GoMokuGameType UserVSUser = new GoMokuGameType(UserVSUserId);
    
    private int type;
    private GoMokuGameType(int t) {
        type = t;
    }
    public String name() {
        if (type == ComputerVSUserId)
            return new String("ComputerVSUser");
        if (type == UserVSComputerId) 
            return new String("UserVSComputer");
        return new String ("UserVSUser");
    }
    
}

