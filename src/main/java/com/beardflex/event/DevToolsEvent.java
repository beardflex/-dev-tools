package com.beardflex.event;

import com.beardflex.bean.Effort;

/**
 * Created by David on 10/03/2017.
 */
public class DevToolsEvent {
    private Intent intent;
    private Effort effort;

    public DevToolsEvent(Intent intent, Effort effort) {
        this.intent = intent;
        this.effort = effort;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Effort getEffort() {
        return effort;
    }

    public void setEffort(Effort effort) {
        this.effort = effort;
    }
}
