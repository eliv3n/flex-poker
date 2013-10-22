package com.flexpoker.core.actionon;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;

import com.flexpoker.config.Command;
import com.flexpoker.core.api.actionon.CreateAndStartActionOnTimerCommand;
import com.flexpoker.core.api.handaction.CallHandActionCommand;
import com.flexpoker.core.api.handaction.FoldHandActionCommand;
import com.flexpoker.core.timertask.ActionOnTimerTask;
import com.flexpoker.model.Game;
import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;

@Command
public class CreateAndStartActionOnTimerImplCommand implements
        CreateAndStartActionOnTimerCommand {

    private final CallHandActionCommand callHandActionCommand;

    private final FoldHandActionCommand foldHandActionCommand;

    @Inject
    @Lazy
    public CreateAndStartActionOnTimerImplCommand(
            CallHandActionCommand callHandActionCommand,
            FoldHandActionCommand foldHandActionCommand) {
        this.callHandActionCommand = callHandActionCommand;
        this.foldHandActionCommand = foldHandActionCommand;
    }

    @Override
    public Timer execute(final Game game, final Table table, final Seat seat) {
        final Timer timer = new Timer();
        final TimerTask timerTask = new ActionOnTimerTask(callHandActionCommand,
                foldHandActionCommand, game, table, seat);
        timer.schedule(timerTask, 20000);
        return timer;
    }

}
