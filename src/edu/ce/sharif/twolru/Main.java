package edu.ce.sharif.twolru;

import edu.ce.sharif.twolru.db.datatypes.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by mohammad on 1/30/17.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        long num = 0;
        Simulator simulator = new Simulator(10L, 9000000000L, 100L);
        File traceFile = new File("/Users/mohammad/Desktop/result_1000.txt");
        Scanner scanner = new Scanner(traceFile);
        String splitPattern=Pattern.quote("#");
        while(scanner.hasNext())
        {
            num++;
            String request = scanner.nextLine();
            if(request.startsWith("Between"))
                continue;
            String[] splitted = request.split("\t");

            Page p = new Page(Long.parseLong(splitted[0]));
            simulator.add(p);
            if(num % 100000 == 0) {
                double prec = (double) num / 33000000;
                prec = prec * 100;
                System.out.println(prec);
            }

        }
        simulator.printStatistics();
    }


}
