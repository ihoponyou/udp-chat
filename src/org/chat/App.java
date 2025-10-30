package org.chat;

import java.io.*;

enum AppType {
    Client,
    Server,
    Unknown,
}

public class App {
    public static final int MESSAGE_SIZE_BYTES = 1024;
    public static final int PORT = 6789;
    public static final boolean SHOW_LOGS = true;

    private static AppType appTypeFromString(String str) {
        switch (str.toLowerCase().strip()) {
            case "c":
            case "client":
                return AppType.Client;
            case "s":
            case "server":
                return AppType.Server;
        }
        return AppType.Unknown;
    }

    // force the user to choose between client/server
    private static AppType chooseAppType(BufferedReader inputReader) {
        AppType appType = AppType.Unknown;
        try {
            String input;
            while (appType == AppType.Unknown) {
                System.out.print("(c)lient or (s)erver? ");
                input = inputReader.readLine();
                if (input == null) {
                    System.out.print('\n');
                    break;
                }
                if (input.isBlank()) {
                    continue;
                }
                appType = appTypeFromString(input);
            }
        } catch (Exception e) {
            System.err.println(Logger.ERROR_MESSAGE_FORMAT.formatted("APP/MAIN", e.getMessage()));
        }
        return appType;
    }

    public static String stripIncludingNewlines(String str) {
        return str.strip().replace('\n', '\u0000');
    }

    public static void main(String[] args) {
        AppType appType = AppType.Unknown;
        if (args.length >= 1) {
            appType = appTypeFromString(args[0]);
        }
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        if (appType == AppType.Unknown) {
            appType = chooseAppType(inputReader);
        }

        if (appType == AppType.Client) {
            Client.start(inputReader);
        } else if (appType == AppType.Server) {
            Server.start();
        }
    }
}
