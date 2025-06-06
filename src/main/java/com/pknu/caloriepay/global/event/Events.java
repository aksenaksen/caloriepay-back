package com.pknu.caloriepay.global.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher eventPublisher;

    static void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        Events.eventPublisher = eventPublisher;
    }

    public static void publish(Object event) {
        if (eventPublisher != null){
            eventPublisher.publishEvent(event);
        }
    }

}
