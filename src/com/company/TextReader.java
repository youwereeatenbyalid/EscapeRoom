package com.company;

import javax.swing.*;
import java.io.*;
/*
Text reader.
Created by Josh to read descriptions in from plaintext files.
 */
public class TextReader {
    public TextReader() {

    }


    public String description(String fileName) throws IOException {

        //fileReader used to create the buffered reader.
        FileReader currentReader;

        //String builder used to create the formatted string
        StringBuilder stringDescription = new StringBuilder();

        //String used to hold the current line.
        String line;

        //Grabs a file with the filename if it exists.
        File currentFile = new File("descriptions",fileName);

        //Try to create a reader if it does
        try
        {
             currentReader = new FileReader(currentFile);
        }
        //display an error if it doesn't and quit.
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Could not locate " + fileName);
            return null;
        }


        //creates a buffered reader from the reader.
        BufferedReader br = new BufferedReader(currentReader);
        //reads the lines into the string builder and formats them.
            while ((line = br.readLine())!= null) {
                line = line +"\n";
                stringDescription.append(line);
            }
            br.close();
            //returns the stringbuilder as a string.
            return stringDescription.toString();

    }
}
