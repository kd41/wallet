package socket.server;

import static constants.Variables.TERMINATOR;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import socket.MessageHelper;
import socket.MessageType;

public class Server {
  private ServerSocket serverSocket;
  protected boolean isRunning = true;

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    int count = 0;
    while (isRunning) {
      Socket clientSocket = serverSocket.accept();
      ServerThread thread = new ServerThread(clientSocket, count++);
      thread.run();
    }
  }

  protected void stop() {
    this.isRunning = false;
  }

  private class ServerThread extends Thread {
    private Socket clientSocket;
    private int count = -1;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;

    private ServerThread(Socket clientSocket, int count) {
      this.clientSocket = clientSocket;
      this.count = count;
    }

    @Override
    public void run() {
      try {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());
        do {
          try {
            message = (String) in.readObject();
            long recNo = Long.parseLong(message.split(TERMINATOR)[1]);
            sendMessage("one of test message");
          } catch (ClassNotFoundException e) {
            System.out.println("Data received in unknown format: " + e);
          }
        } while (true);
      } catch (EOFException e) {
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        try {
          in.close();
        } catch (IOException e) {
          System.out.println(e);
        }
        try {
          out.close();
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    }

    private void sendMessage(String msg) {
      try {
        out.writeObject(msg);
        out.flush();
      } catch (IOException e) {
        System.out.println(e);
      }
    }

  }
}
