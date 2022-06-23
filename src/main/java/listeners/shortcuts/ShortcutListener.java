package listeners.shortcuts;

import com.slack.api.bolt.App;
import listeners.Listener;
import listeners.shortcuts.callbacks.StemojiGlobalShortcutCallback;

public class ShortcutListener implements Listener {
    @Override
    public void register(App app) {
        app.globalShortcut("stemoji-shortcut-callback-id", new StemojiGlobalShortcutCallback(app.client()));
    }
}
