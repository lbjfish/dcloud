package com.sida.dcloud.event.po;

import java.io.Serializable;

public interface EventMessage {
    public static final String BACKEND_REGISTRATION = "BackendRegistration";
    public static class EventJob implements Serializable {
        private final Event event;

        public EventJob(Event event) {
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }
    }

    public static class EventResult implements Serializable {
        private final String text;

        public EventResult(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "EventResult(" + text + ")";
        }
    }

    public static class JobFailed implements Serializable {
        private final String reason;
        private final EventJob job;

        public JobFailed(String reason, EventJob job) {
            this.reason = reason;
            this.job = job;
        }

        public String getReason() {
            return reason;
        }

        public EventJob getJob() {
            return job;
        }

        @Override
        public String toString() {
            return "JobFailed(" + reason + ")";
        }
    }
}