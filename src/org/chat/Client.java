package org.chat;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final int SECONDS_TO_EXIT = 2;

    private static Logger logger = new Logger("CLIENT");

    public static void start(BufferedReader inputReader) {
        String input, message, reply;
        byte[] receiveBuffer = new byte[App.MESSAGE_SIZE_BYTES];
        byte[] sendBuffer = null;
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            logger.log("started");

            InetAddress serverAddress = InetAddress.getLocalHost();
            while (true) {
                System.out.println("say something:");
                input = inputReader.readLine();
                if (input == null) {
                    System.out.print('\n');
                    return;
                }
                if (input.isBlank()) {
                    continue;
                }
                message = input + '\n';

                sendBuffer = message.getBytes();
                DatagramPacket outputPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        serverAddress,
                        App.PORT);
                clientSocket.send(outputPacket);
                logger.log("sent \"%s\"", input);

                // don't wait for the server reply since we are exiting
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                DatagramPacket replyPacket = new DatagramPacket(receiveBuffer, App.MESSAGE_SIZE_BYTES);
                clientSocket.receive(replyPacket);
                reply = new String(replyPacket.getData());
                logger.log("received \"%s\"", App.stripIncludingNewlines(reply));
                System.out.print(reply);
            }

            for (int i = SECONDS_TO_EXIT; i > 0; --i) {
                logger.log("exiting in %d", i);
                TimeUnit.SECONDS.sleep(1);
            }

            logger.log("done");
        } catch (Exception e) {
            logger.logError(e);
        }
    }
}
