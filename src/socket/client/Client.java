package socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  private String host;
  private int port;
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  private String message;
  private String response;

  public Client(String host, int port, String message) {
    this.host = host;
    this.port = port;
    this.message = message;
  }

  protected void start() {
    try {
      try {
        socket = new Socket(host, port);
        socket.setSoTimeout(3000);
      } catch (Exception e) {
        System.out.println("Please check is server running on host: " + host + ", port: " + port);
        return;
      }
      out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      in = new ObjectInputStream(socket.getInputStream());
      try {
        // send
        out.writeObject(message);
        out.flush();
        // receive
        response = (String) in.readObject();
      } catch (IOException e) {
        System.out.println(e);
      } catch (ClassNotFoundException e) {
        System.out.println(e);
      }
    } catch (UnknownHostException e) {
    } catch (IOException e) {
      System.out.println(e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
      try {
        socket.close();
      } catch (IOException e) {
      }
    }
  }

  protected String getResponse() {
    return response;
  }

  protected void setMessage(String message) {
    this.message = message;
  }

}
