package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class CheckUserLevel extends Command {

    public CheckUserLevel() {
        super("level", 0, "Checks User Level, 0 arguments");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        int i = PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel());
        if (i == 9001) {
            event.respond("You are my Master!");
        } else if (i == 15) {
            event.respond("You are a Channel Owner!");

        } else if (i == 10) {
            event.respond("You are a Channel Operator!");
        } else if (i == 5) {
            event.respond("You are a Trusted User!");
        } else {
            event.respond("You are a Regular User!");
        }
    }
}