package com.beardflex.event;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by David on 10/03/2017.
 */
public class EventManager implements Runnable {

    private final int Max_Events = 300;

    private static EventManager instance;
    public static EventManager get() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private final LinkedBlockingDeque<DevToolsEvent> eventQueue = new LinkedBlockingDeque<DevToolsEvent>(Max_Events);
    private final List<DevToolsEventListener> listeners = new ArrayList<DevToolsEventListener>();

    public void fireEvent(DevToolsEvent event) {
        try {
            boolean added = eventQueue.offer(event, 500l, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void addListener(DevToolsEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DevToolsEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        while(true) {
            if(eventQueue.peek() != null) {
                try {
                    DevToolsEvent event = eventQueue.take();

                    for (DevToolsEventListener listener : listeners) {
                        listener.onDevToolsEvent(event);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
