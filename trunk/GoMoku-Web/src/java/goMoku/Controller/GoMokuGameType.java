/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package goMoku.Controller;

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
 * This enum makes use of the Java-Enums, as are described in 
 * http://java.sun.com/j2se/1.5.0/docs/guide/language/enums.html
 */
public enum GoMokuGameType {
    ComputerVSUser("CU"),
    UserVSComputer("UC"),
    UserVSUser("UU");
    
    private String m_commandLineArgument;
    
    private GoMokuGameType(String commandLineArgument) {
    	m_commandLineArgument = commandLineArgument;
    }
    
    /**
     * 
     * @return the command line arguments that corresponds to a particular game type
     */
    public String getCmdline() {
    	return m_commandLineArgument;
    }
    
}

