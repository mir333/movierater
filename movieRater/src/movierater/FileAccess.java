package movierater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miro
 */
public class FileAccess {

    public static void delUseless(String f) {
        try {
            Reader in = new InputStreamReader(new FileInputStream(f), "UTF-8");
            BufferedReader brr = new BufferedReader(in);
            Writer out = new OutputStreamWriter(new FileOutputStream(f + "o"), "UTF-8");
            BufferedWriter brw = new BufferedWriter(out);
            //StringBuffer text = new StringBuffer();
            String strLine, rating, title;
            //Read File Line By Line
            while ((strLine = brr.readLine()) != null) {
                // Print the content on the console
                strLine = strLine.substring(27);
                rating = strLine.substring(0, 3);
                title = strLine.substring(5);
                brw.write(title + "_" + rating);
                brw.newLine();
            }
            //Close the input stream
            brr.close();
            brw.close();
            in.close();
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static String findMovie(String f, String find) {
        Reader in = null;
        String result = "";
        try {
            File file = new File(f);
            in = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader brr = new BufferedReader(in);

            while (true) {
                result = brr.readLine();
                if (result == null) {
                    break;
                }
                if (result.matches("^" + find + ".*")) {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static boolean changeDirName(String path, String newName) {
        File oldfile = new File(path);
        File newfile = new File(newName);
        return oldfile.renameTo(newfile);
    }

    public static boolean testDir(String dirName){
        File dir = new File(dirName);
        return dir.isDirectory();
    }
}
