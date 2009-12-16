package movierater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author miro
 */
public class RegTest {

    public static String getName(String patern, String target) {
        Pattern pattern = Pattern.compile(patern);
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public static String getYear(String patern, String target) {
        Pattern pattern = Pattern.compile(patern);
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return target.substring(matcher.start()+1, matcher.end()-1);
        } else {
            return null;
        }
    }

    public static String getRating(String target) {
        String p="<b>[0-9].[0-9]/10</b>";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return target.substring(matcher.start()+3, matcher.end()-7);
        } else {
            return "";
        }
    }
}
