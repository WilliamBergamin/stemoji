package listeners.events;

import com.slack.api.bolt.App;
import com.slack.api.model.event.AppHomeOpenedEvent;
import listeners.Listener;
import listeners.events.callbacks.AppHomeOpenedCallback;

public class EventListener implements Listener {
    @Override
    public void register(App app) {
        app.event(AppHomeOpenedEvent.class, new AppHomeOpenedCallback(app.client()));
    }
}
