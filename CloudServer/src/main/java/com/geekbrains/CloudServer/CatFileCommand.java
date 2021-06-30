package com.geekbrains.CloudServer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class CatFileCommand implements Command {


    @Override
    public String execute(String line) {
        String[] args = line.split(" ");
        String path;
        if (4 != args.length) {
            return "no arg. expect cat <path:string> <offset:int> <size:int>";
        }
        path = args[1];
        long offset = Long.parseLong(args[2]);
        int size = Integer.parseInt(args[3]);

        File file = new File(path);

        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(offset);
            byte[] buf = new byte[size];
            int chunkSize = raf.read(buf);

            if (chunkSize != buf.length)
                buf = Arrays.copyOf(buf, chunkSize);

            byte[] encodedBuf = Base64.getEncoder().encode(buf);
            StringBuilder responseBuilder = new StringBuilder()
                    .append(file.length())
                    .append("\r\n")
                    .append(new String(encodedBuf, StandardCharsets.UTF_8))
                    .append(EOC);
            return responseBuilder.toString();
        } catch (IOException e) {
            return "problem " + e.getMessage();
        }
    }
}
