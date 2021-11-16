package projetTransport.utils;

import projetTransport.parser.javacc.commons.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DetectFile {
    public static Moyen getParser(File f) throws FileNotFoundException, ParseException {
        String name = f.getName();
        String extension = "txt";
        if (name.contains("."))
            extension = name.substring(name.lastIndexOf(".")).replace(".", "");
        switch (extension){
            case "json" :
                    return Moyen.BUS;
            case  "xml" :
                return trainOrTram(f);
            default:
                return  carOrMetro(f);
        }
    }

    private static Moyen carOrMetro(File f) throws FileNotFoundException, ParseException {
        String regex = "[\t\r ]*[a-zA-Z]+[\t\r ]+[a-zA-Z]+[\t\r ]+[0-9]+[\t\r ]*";
        String regex2 = "[\t\r ]+";
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNextLine()){
            String str  = fileScanner.nextLine();
            if (!str.startsWith("%") &&  !str.matches(regex2) && !str.equals("")){
                if (str.matches(regex))
                    return Moyen.CAR;
                else
                    return Moyen.METRO;
            }
        }
        throw new ParseException("Cannot determine if file is  either \"Car\" or \"Metro\".");
    }

    private static Moyen trainOrTram(File f) throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNextLine()){
            String str = fileScanner.nextLine();
            if (str.contains("<horaires>"))
                return Moyen.TRAIN;
            if (str.contains("<reseau>"))
                return Moyen.TRAM;
        }
        throw new ParseException("Cannot determine if file is  either \"Train\" or \"Tram\".");
    }
}
