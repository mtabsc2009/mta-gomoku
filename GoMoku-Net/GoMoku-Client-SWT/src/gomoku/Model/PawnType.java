package gomoku.Model;

import java.io.Serializable;

public class PawnType implements Serializable{
	
        static public int WhiteId = 0;
	static public int BlackId = 1;
        static public PawnType White = new PawnType(WhiteId);
	static public PawnType Black =new PawnType(BlackId);
        
        private int type;
        
        public PawnType(int t) {
            type = t;
        }
        
        public int getPawnType() {
            return type;
        }
        
}
