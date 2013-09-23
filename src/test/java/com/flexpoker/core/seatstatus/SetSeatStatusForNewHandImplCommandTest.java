package com.flexpoker.core.seatstatus;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.flexpoker.bso.api.ActionOnTimerBso;
import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;
import com.flexpoker.util.ActionOnSeatPredicate;
import com.flexpoker.util.BigBlindSeatPredicate;
import com.flexpoker.util.ButtonSeatPredicate;
import com.flexpoker.util.DataUtilsForTests;
import com.flexpoker.util.SmallBlindSeatPredicate;

public class SetSeatStatusForNewHandImplCommandTest {

    private SetSeatStatusForNewHandImplCommand command;
    
    @Mock ActionOnTimerBso actionOnTimerBso;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        command = new SetSeatStatusForNewHandImplCommand(actionOnTimerBso);
    }
    
    @Test
    public void testExecute() {
        setStatusForNewHandHelper(2, Arrays.asList(new Integer[]{}), 1, 1, 0, 1);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{0}), 2, 2, 1, 2);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{1}), 2, 2, 0, 2);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{2}), 1, 1, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0}), 1, 2, 3, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{1}), 1, 2, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{2}), 1, 2, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{3}), 1, 2, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0, 1}), 2, 2, 3, 2);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{1, 2}), 0, 0, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{2, 3}), 1, 1, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0, 3}), 2, 2, 1, 2);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{}), 1, 2, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{2}), 1, 2, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{2, 3}), 1, 2, 4, 0);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{0, 1, 2}), 4, 4, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{0, 1, 4}), 2, 2, 3, 2);
    }
    
    private void setStatusForNewHandHelper(int numberOfPlayers,
            List<Integer> seatsThatJustLeft, int buttonIndex,
            int smallBlindIndex, int bigBlindIndex, int actionOnIndex) {
        Table table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, numberOfPlayers, 9);
        table.getSeats().get(0).setButton(true);
        table.getSeats().get(1).setSmallBlind(true);
        table.getSeats().get(2).setBigBlind(true);

        for (Integer seat : seatsThatJustLeft) {
            table.getSeats().get(seat.intValue()).setUserGameStatus(null);
            table.getSeats().get(seat.intValue()).setPlayerJustLeft(true);
        }

        command.execute(table);

        for (int i = 0; i < numberOfPlayers; i++) {
            if (seatsThatJustLeft.contains(i)) {
                assertFalse(table.getSeats().get(i).isStillInHand());
            } else {
                assertTrue(table.getSeats().get(i).isStillInHand());
            }
        }

        Seat buttonSeat = (Seat) CollectionUtils.find(table.getSeats(),
                new ButtonSeatPredicate());
        Seat smallBlindSeat = (Seat) CollectionUtils.find(table.getSeats(),
                new SmallBlindSeatPredicate());
        Seat bigBlindSeat = (Seat) CollectionUtils.find(table.getSeats(),
                new BigBlindSeatPredicate());
        Seat actionOnSeat = (Seat) CollectionUtils.find(table.getSeats(),
                new ActionOnSeatPredicate());

        assertTrue(buttonSeat.equals(table.getSeats().get(buttonIndex)));
        assertTrue(smallBlindSeat.equals(table.getSeats().get(smallBlindIndex)));
        assertTrue(bigBlindSeat.equals(table.getSeats().get(bigBlindIndex)));
        assertTrue(actionOnSeat.equals(table.getSeats().get(actionOnIndex)));
    }


}
