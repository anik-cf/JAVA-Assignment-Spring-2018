
package assignment.demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author th3an
 */
public class TestFile {
    
    public static void main(){
        try {
            RandomAccessFile trxfile = new RandomAccessFile("transactions.txt", "r");

            while (true) {
                String line = trxfile.readLine();
                if (line == null) {
                    break;
                }

                String tokens[] = line.split(";");
                
                System.out.println(tokens[0]+" "+tokens[1]+" "+tokens[2]+" ");
               
            }
    
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
