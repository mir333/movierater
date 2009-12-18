package movierater;

import java.util.List;

/**
 *
 * @author miro
 */
public class Main {

    private static void help(){
        System.out.println("Help:\njava -jar movieRater [-pif] \"root dir\"\n" +
                "p - prepares the source movie file\n" +
                "i - date source set for internet\n" +
                "f - date source set for file");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //conf vars
        boolean clean = false;
        boolean webSource = false;
        String moviListFile = "db/ratings.list";
        String rootDir = null;

        if (args.length == 1) {
            rootDir = args[0];
            if (!FileAccess.testDir(rootDir)) {
                System.out.println("Wrong root dir: " + rootDir);
                help();
                System.exit(1);
            }
        } else if (args.length == 2) {
            for (int i = 1; i < args[0].length(); i++) {
                switch (args[0].charAt(i)) {
                    case 'p':
                        clean = true;
                        break;
                    case 'i':
                        webSource = true;
                        break;
                    case 'f':
                        webSource = false;
                        break;
                    default:
                        System.out.println("Wrong arguments: " + args[0]);
                        help();
                        System.exit(1);
                        break;
                }
            }
            rootDir = args[1];
            if (!FileAccess.testDir(rootDir)) {
                System.out.println("Wrong root dir: " + rootDir);
                System.exit(1);
            }
        }
        if (clean) {
            FileAccess.delUseless(moviListFile);
        }
        List<String[]> sl = DirUtils.getDirNames(rootDir);
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
                newDirName = strArr[0].replace(strArr[1], newDirName);
                System.out.println(newDirName);
                System.out.println(FileAccess.changeDirName(strArr[0], newDirName)?"changed":"not changed");
                System.out.println("*************************************************************");
            }
        }
    }
}
