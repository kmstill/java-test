import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Task3 {

	/**
	 * main method to drive application
	 * 
	 * @param args - args[0] is the port number args[1] is the document root path
	 */
	public static void main(String[] args) {
		int port = Integer.valueOf(args[0]);
		String root = args[1];

		server(port, root);
	}

	/**
	 * 
	 * @param port - port for serversocket
	 * @param root - root path for serversocket
	 */
	private static void server(int port, String root) {
		ServerListenerThread thread = new ServerListenerThread(port, root);
		thread.start();
	}

}
