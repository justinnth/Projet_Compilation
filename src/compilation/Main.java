package compilation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Moteur automate = new Moteur("C0.descr");
        automate.lire(new BufferedReader(new InputStreamReader(System.in)));
    }
}
