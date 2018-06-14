package com.pertxt.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class Writer {

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
      writer.write(record + "\n");
    }
    writer.flush();
    writer.close();
    long end = System.currentTimeMillis();
    System.out.println((end - start) / 1000f + " seconds");
  }

  public void writeCompressedFile(String fileName, String value) {
    try {
      InputStream is = new ByteArrayInputStream(value.getBytes());
      GZIPOutputStream gzipOS = new GZIPOutputStream(new FileOutputStream(fileName));

      byte[] buffer = new byte[1024];
      int len;
      while ((len = is.read(buffer)) != -1) {
        gzipOS.write(buffer, 0, len);
      }
      gzipOS.close();
      is.close();
    } catch (IOException ex) {
      System.out.println("Writing failed!");
    }
  }
}
