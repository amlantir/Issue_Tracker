package com.issue.tracker.ticket.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TicketStatusEnum {

    NEW(1L, "new"),
    IN_PROGRESS(2L, "in progress"),
    TO_BE_TESTED(3L, "to be tested"),
    CLOSED(4L, "closed"),
    ON_HOLD(5L, "on hold");

    private final Long id;
    private final String value;
    private static final List<TicketStatus> ticketStatusList = new ArrayList<>();
    private static final Map<Long, TicketStatusEnum> lookup = new HashMap<>();

    TicketStatusEnum(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    static {
        for (TicketStatusEnum d : TicketStatusEnum.values()) {
            ticketStatusList.add(new TicketStatus(d.id, d.value));
            lookup.put(d.id, d);
        }
    }

    public static List<TicketStatus> getTicketStatusList() {
        return ticketStatusList;
    }

    public static Map<Long, TicketStatusEnum> getLookup() {
        return lookup;
    }

    public static TicketStatus getTicketStatusByEnum(TicketStatusEnum ticketStatusEnum) {
        return new TicketStatus(ticketStatusEnum.id, ticketStatusEnum.value);
    }

    public static TicketStatusEnum get(Long status) {
        return lookup.get(status);
    }

    public Long getId() {
        return id;
    }
}