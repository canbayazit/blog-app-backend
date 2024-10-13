package com.example.blog_backend.util.uuid;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;
import java.util.UUID;

public class UuidValueGeneration implements BeforeExecutionGenerator {

    private final EnumSet<EventType> eventTypes;

    public UuidValueGeneration(GeneratedUuidValue annotation) {
        this.eventTypes = annotation.timing().getEquivalent().eventTypes();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return eventTypes;
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        return UUID.randomUUID();  // UUID değerini burada oluşturuyoruz
    }
}
