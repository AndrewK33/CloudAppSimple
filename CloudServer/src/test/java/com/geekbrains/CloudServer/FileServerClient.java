package com.geekbrains.CloudServer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileServerClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final String EOC = ">>>>>>>>>>>>EOC";

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        if (!clientSocket.isConnected()) {
            throw new IllegalArgumentException("not connected to " + ip + ":" + port);
        }
    }

    public boolean ping() throws IOException {
        out.println("ping");
        String resp = in.readLine();
        return resp.equals("pong");
    }

    public List<String> ls() throws IOException {
        out.println("ls");
        String resp;
        List<String> stringList = new ArrayList<>();
        while (!EOC.equals((resp = in.readLine()))) {
            stringList.add(resp);
        }
        return stringList;
    }


    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public File cat(String fileName) throws IOException {
        String extension = FilenameUtils.getExtension(fileName);
        File tmpFile = File.createTempFile("copy", extension);
        final int chunkSize = 1024;

        int currentChunkSize = chunkSize;
        int offset = 0;
        do {
            out.println("cat " + fileName + " " + offset + " " + chunkSize);
            long fileSize = Long.parseLong(in.readLine());
            currentChunkSize = Integer.parseInt(in.readLine());

            String resp = in.readLine();
            byte[] decode = Base64.getDecoder().decode(resp);
            if (!EOC.equals(in.readLine())) {
                throw new IllegalStateException();
            }
            FileUtils.writeByteArrayToFile(tmpFile, decode, 0, currentChunkSize, true);

            if (currentChunkSize < chunkSize || currentChunkSize == -1) {
                break;
            }

            offset += currentChunkSize;
        } while (true);
        return tmpFile;
    }
}