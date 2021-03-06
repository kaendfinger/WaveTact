package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Set;

@IRCCMD
public class ListNetworks extends IRCCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks ln netlist"), 0, "listnetworks (connected/disconnected)", "Lists the networks a bot is on", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Set<String> networks = new HashSet<>();
        for (PircBotX net : Registry.networks.inverse().keySet()) {
            if (args.length < 1) {
                networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else if (args[0].equalsIgnoreCase("connected")) {
                if (net.getState().equals(PircBotX.State.CONNECTED))
                    networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else if (args[0].equalsIgnoreCase("disconnected")) {
                if (net.getState().equals(PircBotX.State.DISCONNECTED))
                    networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else {
                networks.add(IRCUtils.getNetworkNameByNetwork(net));
            }
        }
        if (networks.isEmpty())
            IRCUtils.sendError(user, network, channel, "No networks found" + networks, prefix);
        else
            IRCUtils.sendMessage(user, network, channel, Registry.networks.size() + " network(s) found: " + StringUtils.join(networks, ", "), prefix);
    }

}
