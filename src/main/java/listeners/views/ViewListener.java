package listeners.views;

import com.slack.api.bolt.App;
import listeners.Listener;
import listeners.views.callbacks.StemojiViewCallback;

public class ViewListener implements Listener {
    @Override
    public void register(App app) {
        app.viewSubmission("stemoji-shortcut-callback-id", new StemojiViewCallback(app.client()));
    }
}
