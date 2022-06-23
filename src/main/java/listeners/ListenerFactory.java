package listeners;

import listeners.commands.CommandListener;
import listeners.events.EventListener;
import listeners.messages.MessageListener;
import listeners.shortcuts.ShortcutListener;
import listeners.views.ViewListener;

public class ListenerFactory {

    public static Listener[] getListeners() {
        return new Listener[]{
                new CommandListener(),
                new EventListener(),
                new ShortcutListener(),
                new MessageListener(),
                new ViewListener()
        };
    }
}
