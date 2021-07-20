import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
	private int port;
	private String root;
	private ServerSocket serverSocket;

	public ServerListenerThread(int port, String root) {
		this.port = port;
		this.root = root;
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (serverSocket.isBound() && !serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
				workerThread.start();
			}
		} catch (IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
