package Launchers;

import ClientHandlers.MainClientHandler;
import Utils.HibernateSessionFactoryUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Launch {
    private static int counter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        HibernateSessionFactoryUtil.BuildSessionFactory();
        System.out.println("Server successfully started!");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("\nClient " + ++counter + " connected...");
            Thread work = new Thread(new MainClientHandler(clientSocket));
            work.start();
        }
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Launch.counter = counter;
    }
}
