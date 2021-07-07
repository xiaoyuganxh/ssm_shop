import java.net.URL;

public class Test1 {
    public static void main(String[] args) {
        //D:\IDEA\workspace\springdome\ssmshop\src\main\webapp\pic
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"pic/";
        System.out.println(path);
    }
}
