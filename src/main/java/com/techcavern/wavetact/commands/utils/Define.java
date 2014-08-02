package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.w3c.dom.Document;

import java.util.List;


public class Define extends GenericCommand {
    @CMD
    @GenCMD

    public Define() {
        super(GeneralUtils.toArray("define whatis"), 0, "Define [word]","defines a word");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = GeneralUtils.getDocument("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/" +  StringUtils.join(args).replaceAll(" ", "%20")+ "?key=" + GeneralRegistry.dictionaryapikey);
        String c = doc.toString();
    }

}
