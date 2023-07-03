package com.issue.tracker.ticket.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TicketTypeEnum {

    BUG(1L, "bug"),
    FEATURE(2L, "feature"),
    TEST(3L, "test");

    private final Long id;
    private final String value;
    private static final List<TicketType> ticketTypeList = new ArrayList<>();
    private static final Map<Long, TicketTypeEnum> lookup = new HashMap<>();

    TicketTypeEnum(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    static {
        for (TicketTypeEnum d : TicketTypeEnum.values()) {
            ticketTypeList.add(new TicketType(d.id, d.value));
            lookup.put(d.id, d);
        }
    }

    public static List<TicketType> getTicketTypeList() {
        return ticketTypeList;
    }

    public static Map<Long, TicketTypeEnum> getLookup() {
        return lookup;
    }

    public static TicketType getTicketTypeByEnum(TicketTypeEnum ticketTypeEnum) {
        return new TicketType(ticketTypeEnum.id, ticketTypeEnum.value);
    }

    public static TicketTypeEnum get(Long status) {
        return lookup.get(status);
    }

    public Long getId() {
        return id;
    }
}