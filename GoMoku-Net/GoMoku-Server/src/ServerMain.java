import gomoku.NetServer.GoMokuServer;
import java.io.*;
import java.net.*;
import java.util.*;

public final class ServerMain
{
//    private ServerThread waitingThread;
    
    public static void main(String[] args)
    {
        GoMokuServer gameServer = new GoMokuServer();
        gameServer.startServer();
    }
}