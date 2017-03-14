package com.beardflex.event;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by David on 10/03/2017.
 */
public class EventManager implements Runnable {

    private final Logger log = LogManager.getLogger();

    private final int Max_Events = 300;

    private static EventManager instance;
    public static EventManager get() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private EventManager() {
        log.info("EventManager initialising...");
    }

    private final LinkedBlockingDeque<DevToolsEvent> eventQueue = new LinkedBlockingDeque<DevToolsEvent>(Max_Events);
    private final List<DevToolsEventListener> listeners = new ArrayList<DevToolsEventListener>();

    public void fireEvent(DevToolsEvent event) {
        try {
            boolean added = eventQueue.offer(event, 500l, TimeUnit.MILLISECONDS);
            log.info("Event added.");
        } catch (InterruptedException e) {
            log.error("Encountered thread interruption while trying to add to event queue. e -> %s.", e.toString());
        } catch (NullPointerException e) {
            log.error("Encountered NPE while trying to add to event queue. e -> %s.", e.toString());
        }
    }

    public void addListener(DevToolsEventListener listener) {
        log.info("Added new event listener.");
        listeners.add(listener);
    }

    public void removeListener(DevToolsEventListener listener) {
        log.info("Removed existing listener.");
        listeners.remove(listener);
    }

    @Override
    public void run() {
        log.info("Beginning event manager thread.");
        while(true) {
            if(eventQueue.peek() != null) {
                try {
                    log.trace("An event is available.");
                    DevToolsEvent event = eventQueue.take();
                    log.trace("Offering event to listeners.");
                    for (DevToolsEventListener listener : listeners) {
                        listener.onDevToolsEvent(event);
                    }
                } catch(InterruptedException e) {
                    log.error(e);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }
}
