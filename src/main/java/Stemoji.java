import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import listeners.Listener;
import listeners.ListenerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stemoji {

    private static final Logger logger = LoggerFactory.getLogger(Stemoji.class);

    public static void main(String[] args) throws Exception {
        App app = new App();

        for (Listener listener : ListenerFactory.getListeners()) {
            listener.register(app);
        }

        // SocketModeApp expects an env variable: SLACK_APP_TOKEN
        new SocketModeApp(app).start();
    }
}
