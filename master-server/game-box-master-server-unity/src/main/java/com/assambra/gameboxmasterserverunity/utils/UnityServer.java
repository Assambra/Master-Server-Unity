package com.assambra.gameboxmasterserverunity.utils;

import java.io.IOException;

public class UnityServer {

    public static Process start(String path, String username, String password, String room)
    {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    path,
                    "--username", username,
                    "--password", password,
                    "--room", room);

            return processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void stop()
    {
        // Need to implemented
        // send message to server
        // to start a clean/soft shutdown on the unity server
    }

    public static void destroy(Process process)
    {
        if (process != null) {
            process.destroy();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
