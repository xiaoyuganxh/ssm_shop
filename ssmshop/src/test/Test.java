import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
    public static void main(String[] args) {
        String str = "Sat Oct 10 00:00:00 CST 2009";
        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        Date date;
        try {
            date = sdf.parse(str);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            Date parse = sdf.parse(format);
            System.out.println(parse);
            System.out.println(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}


