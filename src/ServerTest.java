import xyz.imxqd.push.Message;
import xyz.imxqd.push.Server;

/**
 * Created by imxqd on 2017/3/26.
 */
public class ServerTest {
    public static void main(String[] args) throws InterruptedException {
        Server.init();
        Server server = Server.getInstance();
        server.push(new Message("1"));
        server.push(new Message("2"));
        server.push(new Message("3"));
        server.push(new Message("4"));
        Thread.sleep(5000);
        server.push(new Message("5"));
        Thread.sleep(3000);
        server.push(new Message("6"));
        Thread.sleep(5000);
        server.push(new Message("7"));
        Thread.sleep(5000);
        server.push(new Message("8"));
        Thread.sleep(5000);
        server.push(new Message("9"));
    }
}
