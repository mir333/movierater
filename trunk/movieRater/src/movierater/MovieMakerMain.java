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
        boolean clean = true;
        boolean webSource = false;
        String moviListFile = "db/ratings.list";


        if (clean) {
            FileAccess.delUseless(moviListFile);
        }
        List<String[]> sl = DirUtils.getDirNames("c:/movies/");
        String pattern = "\\[(\\d){4}\\]";

        for (String[] strArr : sl) {
            System.out.println("dir: " + strArr[0] + " name: " + strArr[1]);
            String name = strArr[1].split(pattern)[0];
            name = name.replaceAll("\\.", " ");
            String year = RegTest.getYear(pattern, strArr[1]);
            if (webSource) {
                System.out.println(ImdbApiCall.getMovieData(name, year));
            } else {
                System.out.println(FileAccess.findMovie(moviListFile+"o", name+" ("+year+")") );
            }
        }
    }
}
