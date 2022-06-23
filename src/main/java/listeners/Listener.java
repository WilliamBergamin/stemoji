package listeners;

import com.slack.api.bolt.App;

public interface Listener {
    void register(App app);
}
