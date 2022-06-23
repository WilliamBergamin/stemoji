package listeners.messages;

import com.slack.api.bolt.App;
import listeners.Listener;
import listeners.messages.callbacks.StatsMessageCallback;

import java.util.regex.Pattern;

public class MessageListener implements Listener {
    @Override
    public void register(App app) {
        Pattern statsPattern = Pattern.compile("(?i)(statistics|statistic|stats|stat)");
        app.message(statsPattern, new StatsMessageCallback(app.client()));
    }
}
