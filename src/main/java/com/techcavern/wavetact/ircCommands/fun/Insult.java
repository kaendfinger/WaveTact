package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Random;

@IRCCMD
public class Insult extends IRCCommand {

    public Insult() {
        super(GeneralUtils.toArray("insult ins burn"), 1, "insult [something]", "Insults something", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://www.pangloss.com/seidel/Shaker/").userAgent(Registry.USER_AGENT).get();
        String c = doc.select("p").get(0).text().replaceAll("\\[","").replaceAll("]","");
        int chance = new Random().nextInt(10);
        if (args.length < 1 || args[0].equalsIgnoreCase(network.getNick()))
            IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(user.getNick()) + ": " + c, prefix);
        else if (userPermLevel >= 20 || chance != 1)
            IRCUtils.sendMessage(user, network,  channel, args[0] + ": " + c, prefix);
        else
            IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(user.getNick()) + ": " + c, prefix);
    }
}
