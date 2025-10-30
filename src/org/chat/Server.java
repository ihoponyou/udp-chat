package org.chat;

import java.io.*;
import java.net.*;

public class Server {
    private static Logger logger = new Logger("SERVER");
    private static int messagesSent = 0;

    public static synchronized int incrementMessagesSent() {
        return ++messagesSent;
    }

    public static void main(String[] args) {
        byte[] receiveBuffer = new byte[App.MESSAGE_SIZE_BYTES];
        try (DatagramSocket serverSocket = new DatagramSocket(App.PORT)) {
            logger.log("started");

            while (true) {
                receiveBuffer = new byte[App.MESSAGE_SIZE_BYTES];
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(packet);
                Thread packetThread = new Thread(new PacketHandler(serverSocket, packet));
                packetThread.start();
            }
        } catch (IOException e) {
            logger.logError(e);
        }
    }
}
