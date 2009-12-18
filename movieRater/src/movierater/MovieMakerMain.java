package movierater;

import java.util.List;

/**
 *
 * @author miro
 */
public class MovieMakerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //conf vars
        boolean clean = false;
        boolean webSource = false;
        String moviListFile = "db/ratings.list";


        if (clean) {
            FileAccess.delUseless(moviListFile);
        }
        List<String[]> sl = DirUtils.getDirNames("c:/movies/");
        String pattern = "\\[(\\d){4}\\]";
        String newDirName = "";

        for (String[] strArr : sl) {
            System.out.println("dir: " + strArr[0] + " name: " + strArr[1]);
            String name = strArr[1].split(pattern)[0];
            name = name.replaceAll("\\.", " ");
            String year = RegTest.getYear(pattern, strArr[1]);
            if (webSource) {
                newDirName = ImdbApiCall.getMovieData(name, year);
            } else {
                String search = name;
                if (year != null) {
                    search += " \\(" + year;
                }
                newDirName = FileAccess.findMovie(moviListFile + "o", search);
            }
            if (newDirName != null) {
                newDirName = newDirName.replaceAll(" \\(", "[");
                newDirName = newDirName.replaceAll("/.", "");
                newDirName = newDirName.replaceAll(" ", ".");
                newDirName = newDirName.replaceAll("\\(", "[");
                newDirName = newDirName.replaceAll("\\)", "]");
                System.out.println(newDirName);
                newDirName = strArr[0].replace(strArr[1], newDirName);
                System.out.println(newDirName);
                System.out.println(FileAccess.changeDirName(strArr[0], newDirName));
            }
        }
    }
}
