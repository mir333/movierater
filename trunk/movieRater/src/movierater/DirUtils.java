package movierater;

import java.io.*;
import java.util.*;

public class DirUtils {

    private static List<String[]> list = new ArrayList<String[]>();

    private static void listPath(File path) {
        File files[];
        files = path.listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                String[] s = {files[i].toString(),files[i].getName()};
                list.add(s);
                listPath(files[i]);
            }
        }
    }

    public static List<String[]> getDirNames(String path){
        listPath(new File(path));
        return list;
    }
}
