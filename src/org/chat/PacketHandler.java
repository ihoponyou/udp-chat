package org.chat;

import java.io.*;
import java.net.*;

public class PacketHandler implements Runnable {
    private static int clientsCreated = 0;

    private Logger logger;
    private DatagramSocket serverSocket;
    private DatagramPacket receivedPacket;

    public PacketHandler(DatagramSocket socket, DatagramPacket packet) throws IOException {
        logger = new Logger("PACKET %d".formatted(clientsCreated));
        receivedPacket = packet;
        serverSocket = socket;

        ++clientsCreated;
    }

    public static String packetToString(DatagramPacket packet) {
        return "%s:%s".formatted(packet.getAddress(), packet.getPort());
    }

    public void run() {
        String packetAsString = PacketHandler.packetToString(receivedPacket);
        String message;
        byte[] sendBuffer;
        try {
            logger.log("started");

            message = new String(receivedPacket.getData());
            logger.log("%s said \"%s\"", packetAsString, App.stripIncludingNewlines(message));

            if (message.toLowerCase().equals("exit")) {
                return;
            }

            int messagesSent = Server.incrementMessagesSent();
            String reply = Integer.toString(messagesSent) + '\n';
            sendBuffer = reply.getBytes();
            DatagramPacket replyPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                    receivedPacket.getAddress(), receivedPacket.getPort());
            serverSocket.send(replyPacket);

            logger.log("replied to %s with \"%s\"", packetAsString, App.stripIncludingNewlines(reply));

            logger.log("done");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
