package com.issue.tracker.ticket.priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TicketPriorityEnum {

    LOW(1L, "low"),
    NORMAL(2L, "normal"),
    HIGH(3L, "high"),
    IMMEDIATE(4L, "immediate");

    private final Long id;
    private final String value;
    private static final List<TicketPriority> ticketPriorityList = new ArrayList<>();
    private static final Map<Long, TicketPriorityEnum> lookup = new HashMap<>();

    TicketPriorityEnum(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    static {
        for (TicketPriorityEnum d : TicketPriorityEnum.values()) {
            ticketPriorityList.add(new TicketPriority(d.id, d.value));
            lookup.put(d.id, d);
        }
    }

    public static List<TicketPriority> getTicketPriorityList() {
        return ticketPriorityList;
    }

    public static Map<Long, TicketPriorityEnum> getLookup() {
        return lookup;
    }

    public static TicketPriority getTicketPriorityByEnum(TicketPriorityEnum ticketPriorityEnum) {
        return new TicketPriority(ticketPriorityEnum.id, ticketPriorityEnum.value);
    }

    public static TicketPriorityEnum get(Long priorityId) {
        return lookup.get(priorityId);
    }

    public Long getId() {
        return id;
    }
}