package org.frontline.certInstaller.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {
    public static void writeFile(String keyString,String filePath) throws IOException {
        BufferedWriter outFile = new BufferedWriter(new FileWriter(filePath));
        outFile.write(keyString);
        outFile.flush();
        outFile.close();
    }
}
