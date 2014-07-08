/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.events;

import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class MessageListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String[] messageParts = event.getMessage().split(" ");
        String m = messageParts[0].toLowerCase();
        GenericCommand Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));

                if (Command != null && m.startsWith(GetUtils.getCommandChar(event.getBot()))) {
                    if (PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel()) >= Command.getPermLevel()) {
                        if (Command.getPermLevel() == 9) {
                            if (event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isOp(event.getBot().getUserBot()) ||  event.getChannel().isOwner(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, ArrayUtils.remove(messageParts, 0));
                            } else {
                                event.getChannel().send().message("Error: I must be at least Opped to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 14) {

                            if (event.getChannel().isOwner(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, ArrayUtils.remove(messageParts, 0));
                            } else {
                                event.getChannel().send().message("Error: I must be Ownered to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 6) {
                            if (event.getChannel().isOwner(event.getBot().getUserBot()) || event.getChannel().isOp(event.getBot().getUserBot()) ||event.getChannel().isHalfOp(event.getBot().getUserBot())|| event.getChannel().isSuperOp(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, ArrayUtils.remove(messageParts, 0));
                            } else {
                                event.getChannel().send().message("Error: I must be at least half-opped to perform the operation requested");
                            }
                        }else{
                            Command.onCommand(event.getUser(), event.getBot(), event.getChannel(), false, ArrayUtils.remove(messageParts, 0));
                        }

                    } else {
                        event.getChannel().send().message("Permission Denied");

                    }
                }
            }
        }


