import xyz.imxqd.push.Client;
import xyz.imxqd.push.Message;

import java.io.IOException;

/**
 * Created by imxqd on 2017/3/26.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException {
        Client client = new Client(new Client.OnNewMessageListener() {
            @Override
            public void onNewMessage(Message message) {
                System.out.println(message);
            }
        });
        client.connect("127.0.0.1", 73);
        client.start();
    }
}
