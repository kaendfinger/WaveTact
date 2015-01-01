package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.BANS;

public class BanTimeoutListener implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException c) {
            // ignored
        }
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                for (Record banRecord : DatabaseUtils.getBans()) {
                    try {
                        if (System.currentTimeMillis() >= ((Long) banRecord.getValue(BANS.TIME) + (Long) banRecord.getValue(BANS.INIT))) {
                            PircBotX networkObject = IRCUtils.getBotByNetworkName(banRecord.getValue(BANS.NETWORK));
                            IRCUtils.setMode(IRCUtils.getChannelbyName(networkObject, banRecord.getValue(BANS.CHANNEL)), networkObject, "-" + Registry.QuietBans.get(banRecord.getValue(BANS.PROPERTY)), banRecord.getValue(BANS.HOSTMASK));
                            DatabaseUtils.removeBan(banRecord.getValue(BANS.NETWORK), banRecord.getValue(BANS.CHANNEL), banRecord.getValue(BANS.HOSTMASK), banRecord.getValue(BANS.ISMUTE));
                        } else {
                            break;
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        // ignored
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

