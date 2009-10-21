/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package j2mehelper.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.Connector;

/**
 *
 * @author Michael
 */
public class Socket {
    private SocketConnection sck;
    public Socket(String ipAddress, int port) throws IOException{
        // TODO; convert port param to string and use it for opening the connection
        String serverAddress = "socket://" + ipAddress + ":" + port;
        sck = (SocketConnection) Connector.open(serverAddress);
    }
    
    public InputStream getInputStream() throws IOException{
        return sck.openInputStream();
    }
    
    public OutputStream getOutputStream () throws IOException{
        return sck.openOutputStream();
    }
    
    public void close() throws IOException{
        sck.close();
    }
            
}
