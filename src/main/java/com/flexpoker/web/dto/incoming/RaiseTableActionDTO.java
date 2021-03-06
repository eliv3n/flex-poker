package com.flexpoker.web.dto.incoming;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RaiseTableActionDTO {

    private final UUID gameId;

    private final UUID tableId;

    private final int raiseToAmount;

    @JsonCreator
    public RaiseTableActionDTO(
            @JsonProperty(value = "gameId") UUID gameId,
            @JsonProperty(value = "tableId") UUID tableId,
            @JsonProperty(value = "raiseToAmount") int raiseToAmount) {
        this.gameId = gameId;
        this.tableId = tableId;
        this.raiseToAmount = raiseToAmount;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public int getRaiseToAmount() {
        return raiseToAmount;
    }

}
