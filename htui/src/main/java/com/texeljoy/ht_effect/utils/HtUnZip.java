package com.texeljoy.ht_effect.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HtUnZip {

  public static void unzip(File zip, File targetDir) throws IOException {
    InputStream in = new FileInputStream(zip);
    unzip(in, targetDir);
  }

  public static void unzip(InputStream in, File targetDir) throws IOException {
    final ZipInputStream zipIn = new ZipInputStream(in);
    final byte[] b = new byte[8192];
    ZipEntry zipEntry;
    while ((zipEntry = zipIn.getNextEntry()) != null) {
      final File file = new File(targetDir, zipEntry.getName());
      String canonicalPath = file.getCanonicalPath();

      if (!canonicalPath.startsWith(targetDir.getCanonicalPath())) {
        // SecurityException
        throw new RuntimeException("illegal zip path");
      }

      if (!zipEntry.isDirectory()) {
        final File parent = file.getParentFile();
        if (!parent.exists()) {
          parent.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        int r;
        while ((r = zipIn.read(b)) != -1) {
          fos.write(b, 0, r);
        }
        fos.close();
      } else {
        file.mkdirs();
      }
      zipIn.closeEntry();
    }
  }
}

