package com.beardflex.event;

/**
 * Created by David on 10/03/2017.
 */
@FunctionalInterface
public interface DevToolsEventListener {
    public void onDevToolsEvent(DevToolsEvent event);
}
