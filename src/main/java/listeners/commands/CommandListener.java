package listeners.commands;

import com.slack.api.bolt.App;
import listeners.Listener;
import listeners.commands.callbacks.StemojiCommandCallback;

public class CommandListener implements Listener {
    @Override
    public void register(App app) {
        app.command("/stemoji", new StemojiCommandCallback(app.client()));
    }
}
