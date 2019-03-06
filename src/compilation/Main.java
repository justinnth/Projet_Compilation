package compilation;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        System.out.println(System.getProperty("user.dir"));
        AEF automate = new AEF("CO.descr");
    }
}
