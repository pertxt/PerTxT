package com.pertxt.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer
{

    //private static final int ITERATIONS = 5;
    //private static final double MEG = (Math.pow(1024, 2));
    //private static final int RECORD_COUNT = 4000000;
    //private static final String RECORD = "Help I am trapped in a fortune cookie factory\n";
    //private static final int RECSIZE = RECORD.getBytes().length;

    public void writeRaw(List<String> records, String outputFileName) {
        try {
            File file = new File(outputFileName);
            try {
                FileWriter writer = new FileWriter(file);
                System.out.print("Writing raw... ");
                write(records, writer);
            } finally {
                // comment this out if you want to inspect the files afterward
                //file.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void write(List<String> records, java.io.Writer writer) throws IOException {
        long start = System.currentTimeMillis();
        for (String record : records) {
            writer.write(record+"\n");
        }
        writer.flush();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000f + " seconds");
    }
}
