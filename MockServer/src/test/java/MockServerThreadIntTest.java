import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class MockServerThreadIntTest {

    private static int PORT = 54321;
    private static final String HOST = "127.0.0.1";
    private static final String CRLF = System.lineSeparator();

    private static final String REQUEST = "value10";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(REQUEST);
        bw.flush();
        socket.shutdownOutput();

        //读取服务器返回的消息
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = br.readLine();
        System.out.println("ProviderMain responsed：" + response);

        assertTrue(response.startsWith("response"));

        socket.close();
    }

}
