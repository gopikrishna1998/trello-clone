package com.machine.coding.trello.service;

import java.util.List;
import java.util.UUID;

public class UuidService {

    private static Integer index = 0;

    public static UUID getDynamicUuid() {
        return UUID.randomUUID();
    }

    public static UUID getStaticUuid() {
        List<UUID> uuidList = List.of(
                UUID.fromString("81e6f2e4-ba1b-4fff-bf13-f5357c6026de"),
                UUID.fromString("12423070-8dcb-43cc-b795-a47d602f03b5"),
                UUID.fromString("dcf076a2-88db-4bae-806d-09555133cd91"),
                UUID.fromString("314d81a5-d7a1-4c72-8c43-c613fce5e27b"),
                UUID.fromString("fed60b44-d3db-4e6f-bbfc-0c233c161127"),
                UUID.fromString("9c7ed7e8-52c3-4428-b18d-10d512a36328")
        );
        UUID hardcodedUuid = uuidList.get(index);
        index ++;
        return hardcodedUuid;
    }
}
