package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.AuthCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class FDrop extends GenericCommand {
    @CMD
    @ConCMD
    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), 9001, "fdrop [user]", "forcefully drops a user");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        AuthedUser authedUser = PermUtils.getAuthUser(Bot, args[0]);
        if (authedUser != null) {
            GeneralRegistry.AuthedUsers.remove(authedUser);
        }
        Account account = AccountUtils.getAccount(args[0]);
        if (account != null) {
            GeneralRegistry.Accounts.remove(account);
            AccountUtils.saveAccounts();
            IRCUtils.SendMessage(user, channel, "Account dropped", isPrivate);
        } else {
            user.send().notice("account does not exist");
        }
    }
}
