package com.company;

import javax.swing.*;
import java.io.*;

public class TextReader {
    String line;
    BufferedReader br;
    StringBuilder stringDescription;
    FileReader currentReader;
    File currentFile;
    public TextReader() {

    }


    public String description(String fileName) throws IOException {
        stringDescription = new StringBuilder(new String());
        currentFile = new File("descriptions",fileName);
        try
        {
            currentReader = new FileReader(currentFile);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Could not locate " + fileName);
            return null;
        }

        br = new BufferedReader(currentReader);


            while ((line = br.readLine())!= null) {
                line = line +"\n";
                stringDescription.append(line);
            }
            br.close();
            return stringDescription.toString();

    }
}
