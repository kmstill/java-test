import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpConnectionWorkerThread extends Thread {

	private Socket socket;

	public HttpConnectionWorkerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream in = null;
		try {
			in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder requestBuilder = new StringBuilder();
			String line;
			while (!(line = br.readLine()).isBlank()) {
				requestBuilder.append(line + "\r\n");
			}

			String request = requestBuilder.toString();
			String[] requestsLines = request.split("\r\n");
			String[] requestLine = requestsLines[0].split(" ");
			String method = requestLine[0];
			String path = requestLine[1];

			Path filePath = this.getFilePath(path);
			if (Files.exists(filePath)) {
				// file exist
				String contentType = this.guessContentType(filePath);
				this.sendResponse("200 OK", contentType, Files.readAllBytes(filePath));
			} else {
				// 404
				byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
				this.sendResponse("404 Not Found", "text/html", notFoundContent);
			}

		} catch (IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}

		}
	}

	private String guessContentType(Path filePath) throws IOException {
		return Files.probeContentType(filePath);
	}

	private Path getFilePath(String path) {
		if ("/".equals(path)) {
			path = "/index.html";
		}
		return Paths.get("/tmp/www", path);
	}

	private void sendResponse(String status, String contentType, byte[] content) throws IOException {
		OutputStream out = this.socket.getOutputStream();
		out.write(("HTTP/1.1 \r\n" + status).getBytes());
		out.write(("ContentType: " + contentType + "\r\n").getBytes());
		out.write("\r\n".getBytes());
		out.write(content);
		out.write("\r\n\r\n".getBytes());
		out.flush();
		out.close();
	}

}
