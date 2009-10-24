package j2mehelper.string;

import java.util.Vector;

/**
 * Helper class for splitting strings.
 * 
 */

public class StringSplitter {
    private String _sep;
    public  StringSplitter(String sep) {
        _sep = sep;
    }
    
    public String[] split(String original) {
        return StringSplitter.split(original,_sep);
    }
    public static String[] split(String original, String separator) {
    Vector nodes = new Vector();
    
    // Parse nodes into vector
    int index = original.indexOf(separator);
    while(index>=0) {
        nodes.addElement( original.substring(0, index) );
        original = original.substring(index+separator.length());
        index = original.indexOf(separator);
    }
    // Get the last node
    nodes.addElement( original );

    // Create splitted string array
    String[] result = new String[ nodes.size() ];
    if( nodes.size()>0 ) {
        for(int loop=0; loop<nodes.size(); loop++) {
            result[loop] = (String)nodes.elementAt(loop);
            System.out.println(result[loop]);
        }
    }

    return result;
    }
    
}