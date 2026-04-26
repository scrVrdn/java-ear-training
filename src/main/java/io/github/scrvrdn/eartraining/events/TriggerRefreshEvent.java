package io.github.scrvrdn.eartraining.events;

import org.springframework.context.ApplicationEvent;

public class TriggerRefreshEvent extends ApplicationEvent {
    public TriggerRefreshEvent(Object source) {
        super(source);
    }
}
