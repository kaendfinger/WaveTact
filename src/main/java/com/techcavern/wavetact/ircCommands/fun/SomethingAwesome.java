package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;

/**
 * @author jztech101
 */
@IRCCMD
public class SomethingAwesome extends IRCCommand {

    public SomethingAwesome() {
        super(GeneralUtils.toArray("somethingawesome awesome something"), 0, null, "Something AWESOME!", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
       if(IRCUtils.checkIfCanKick(channel,network,user)){
          channel.send().kick(user, "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻");
       } else {
            IRCUtils.sendAction(user, network, channel, "kicks " + user.getNick() + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻)", prefix);
        }
    }
}
