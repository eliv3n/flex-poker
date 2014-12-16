package com.flexpoker.table.command.events;

import java.util.UUID;

import com.flexpoker.framework.event.BaseEvent;
import com.flexpoker.table.command.framework.TableEvent;
import com.flexpoker.table.command.framework.TableEventType;

public class LastToActChangedEvent extends BaseEvent<TableEventType> implements
        TableEvent {

    private static final TableEventType TYPE = TableEventType.LastToActChanged;

    private final UUID gameId;

    private final UUID handId;

    private final UUID playerId;

    public LastToActChangedEvent(UUID aggregateId, int version, UUID gameId, UUID handId,
            UUID playerId) {
        super(aggregateId, version, TYPE);
        this.gameId = gameId;
        this.handId = handId;
        this.playerId = playerId;
    }

    @Override
    public UUID getGameId() {
        return gameId;
    }

    public UUID getHandId() {
        return handId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

}
