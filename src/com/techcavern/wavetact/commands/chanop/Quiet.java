package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.concurrent.TimeUnit;


public class Quiet extends Command {
    public Quiet() {
    super("Quiet", 10, "Quiet [ircd] (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
            if ((args.length == 4) && (!args[1].startsWith("-"))) {
                    quiettime time = new quiettime();
                    time.run(args[2], args[0],
                            IRCUtils.getUserByNick(event.getChannel(), args[1]),
                            event.getChannel(), event.getBot());

            } else if ((args.length < 4) && (!args[1].startsWith("-"))) {
                quiet(IRCUtils.getUserByNick(event.getChannel(), args[1]),
                        args[0], event.getChannel(), event.getBot());
            } else if (args[1].startsWith("-")) {
                unquiet(IRCUtils.getUserByNick(event.getChannel(),
                                args[1].replaceFirst("-", "")), args[0],
                        event.getChannel(), event.getBot());

        }
    }

    public void quiet(User u, String i, Channel c, PircBotX b) {
        if (i.equalsIgnoreCase("c")) {
            IRCUtils.setMode(c, b, "+q ", u);
        } else if (i.equalsIgnoreCase("u")) {
            IRCUtils.setMode(c, b, "+b ~q:", u);
        } else if (i.equalsIgnoreCase("i")) {
            IRCUtils.setMode(c, b, "+b m:", u);
        }
    }

    public void unquiet(User u, String i, Channel c, PircBotX b) {
        if (i.equalsIgnoreCase("c")) {
            IRCUtils.setMode(c, b, "-q ", u);
        } else if (i.equalsIgnoreCase("u")) {
            IRCUtils.setMode(c, b, "-b ~q:", u);
        } else if (i.equalsIgnoreCase("i")) {
            IRCUtils.setMode(c, b, "-b m:", u);
        }
    }

    public class quiettime extends Thread {
        public void run(String i, String s, User u, Channel c, PircBotX b)
                throws InterruptedException {
            quiet(u, s, c, b);

            if (i.endsWith("s")) {
                int e = Integer.parseInt(i.replace("s", ""));
                TimeUnit.SECONDS.sleep(e);
            } else if (i.endsWith("m")) {
                int e = Integer.parseInt(i.replace("m", ""));
                TimeUnit.MINUTES.sleep(e);
            } else if (i.endsWith("h")) {
                int e = Integer.parseInt(i.replace("h", ""));
                TimeUnit.HOURS.sleep(e);
            } else if (i.endsWith("d")) {
                int e = Integer.parseInt(i.replace("d", ""));
                TimeUnit.DAYS.sleep(e);
            }

            unquiet(u, s, c, b);
        }
    }
}
