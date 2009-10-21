/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetServer;

import gomoku.Model.GameBoard;
import gomoku.Model.PawnType;
import gomoku.Model.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * GomokuObjectSerializer class is a helper class for
 * serializing and deserializing objects over the net.
 * It currently supports 4 data types:
 * BOOLEAN:           0, true/false
 * STRING:              1, length, contents
 * GAMEBOARD:      2, pawnsCount, boardSize, list of (x,y,type)
 *                                  the list contains pawnCount items
 * POINT:                3, xCord,yCord
 *
 * This class exports two methods: writeObject and readObject
 * (like the ObjectInputStream and the ObjectOutputStream classes).
 * Both methods might throw exceptions:
 * ClassCastException - in case the methods recv`d an unknown (currently unhandled) class
 * IOException - in case of a network error. this exception is caused by the Socket class
 *
 * This class could have been written in a more "cleaner" way.
 * But it was written this way to make the porting to MIDP easier.
 */

public class GomokuObjectSerializer {
    private Socket sck;
    private InputStream in;
    private OutputStream out;

    private final byte PROTOCOL_DATA_TYPE_BOOLEAN = 0;
    private final byte PROTOCOL_DATA_TYPE_STRING = 1;
    private final byte PROTOCOL_DATA_TYPE_GAMEBOARD = 2;
    private final byte PROTOCOL_DATA_TYPE_POINT = 3;

    public GomokuObjectSerializer(Socket s) throws IOException{
        sck = s;
        in = sck.getInputStream();
        out = sck.getOutputStream();
    }

    public void writeObject(String str) throws IOException{
        byte strBytes[] = str.getBytes();
        byte[] content = new byte[2+strBytes.length];
        content[0] = PROTOCOL_DATA_TYPE_STRING;
        content[1] = (byte)strBytes.length;
        int i;
        for ( i = 0 ; i < strBytes.length ; ++i) {
            content[2+i] = strBytes[i];
        }
        debugPrint("::writeObject(String): "+str);
        writeContent(content);
    }

    public void  writeObject(Boolean val) throws IOException{
        byte[] content = new byte[2];
        content[0] =PROTOCOL_DATA_TYPE_BOOLEAN;
        content[1] = (byte) (val ? 1 : 0);
        debugPrint("::writeObject(Boolean): "+val.toString());
        writeContent(content);
    }

    public void  writeObject(GameBoard gameBoard) throws IOException {
        byte[] content = new byte[1 + 1 + 1 + gameBoard.getPawnsCount()*3];
        content[0] = PROTOCOL_DATA_TYPE_GAMEBOARD;
        content[1] = (byte)gameBoard.getPawnsCount();
        content[2] = (byte)gameBoard.getSize();
        int x,y,i=0;
        for (x=1 ; x<gameBoard.getSize() ; ++x) {
            for (y=1 ; y < gameBoard.getSize() ; ++y) {
                PawnType pType = gameBoard.getPawnType(y, x);
                if (pType!=null) {
                    content[3+i] = (byte)x;
                    content[3+i+1] = (byte)y;
                    content[3+i+2] = (byte)pType.getPawnType();
                    i+=3;
                }
            }
        }
        debugPrint("::writeObject(GameBoard): "+gameBoard.getPawnsCount()+ " pawns");
        writeContent(content);
    }

    public void writeObject(Point point) throws IOException {
        byte[] content = new byte[3];
        content[0] =PROTOCOL_DATA_TYPE_POINT;
        content[1] = (byte)point.x;
        content[2] = (byte)point.y;
        debugPrint("::writeObject(point): "+point.x + " " + point.y);
        writeContent(content);

    }

    private void writeContent(byte[] objectData) throws IOException{
        out.write(objectData);
    }
    public boolean writeObject(Object obj) throws ClassCastException{
        debugPrint("::writeObject(Object): Unknwons Class "+obj.getClass().toString());
        throw new ClassCastException();
    }

    public Object readObject() throws ClassCastException, IOException {
        Object obj;
        int objectType = in.read();
        int len,i;
        switch (objectType) {
            case PROTOCOL_DATA_TYPE_BOOLEAN:
                obj = new Boolean(in.read()==1?true:false);
                break;
            case PROTOCOL_DATA_TYPE_STRING:
                len = in.read();
                byte content[] = new byte[len];
                for(i = 0 ; i < len ; ++i) {
                    content[i] = (byte)in.read();
                }
                obj = new String(content);
                break;
            case PROTOCOL_DATA_TYPE_GAMEBOARD:
                len = in.read();
                GameBoard g = new GameBoard(in.read());
                for(i = 0 ; i < len ; ++i) {
                    int x = (int)in.read();
                    int y = (int)in.read();
                    int type = (int)in.read();
                    if (type == PawnType.WhiteId)
                        g.PlaceWhitePawn(new Point(x,y));
                    else
                        g.PlaceBlackPawn(new Point(x,y));
                }
                obj = g;
                break;
            case PROTOCOL_DATA_TYPE_POINT:
                int x = in.read();
                int y = in.read();
                obj = new Point(x, y);
                break;
            case -1:
                throw new IOException(this.getClass().toString()+"::readObject: EOF");
            default:
                throw new ClassCastException(this.getClass().toString()+"::readObject: Unknwons class "+objectType);
        }

        debugPrint("::readObject: received "+
                obj.getClass().toString()+" :"+obj.toString());
        return obj;
    }

    /**
     * debugPrint internal function from debugging purposes.
     * may be enabled/disabled on compile time.
     */
    private void debugPrint(String str) {
//        System.out.println(this.getClass().toString()+str);
    }

}
