package Utils.Connectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connector {
    public static Socket socket;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public static void SendString(String string) throws IOException {
        oos.writeObject(string);
        oos.flush();
    }
}
