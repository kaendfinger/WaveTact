package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetactdb.tables.Channelproperty;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@IRCCMD
public class MCServerInfo extends IRCCommand {

    public MCServerInfo() {
        super(GeneralUtils.toArray("mcserverinfo mcsi mcserver mcping mclist mcwho mcplayers"), 5, "mcserverinfo [address] (port)", "Gets info on minecraft server", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int port = 25565;
        if(args.length < 1){
            org.jooq.Record rec = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network),channel.getName(), "mcserver");
            if (rec != null){
                args = StringUtils.split(rec.getValue(Channelproperty.CHANNELPROPERTY.VALUE),":");
            }else{
                IRCUtils.sendError(user, network, channel, "Default Minecraft Server Undefined", prefix);
                return;
            }
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }
        InetSocketAddress address = new InetSocketAddress(GeneralUtils.getIP(args[0], network, false), port);
        Socket socket = new Socket();
        socket.connect(address, 10000);
        OutputStream preout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(preout);
        InputStream prein = socket.getInputStream();
        InputStreamReader inreader = new InputStreamReader(prein);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(bos);
        handshake.writeByte(0x00);
        GeneralUtils.writeOutputStream(handshake, 4);
        GeneralUtils.writeOutputStream(handshake, address.getHostString().length());
        handshake.writeBytes(address.getHostString());
        handshake.writeShort(address.getPort());
        GeneralUtils.writeOutputStream(handshake, 1);
        GeneralUtils.writeOutputStream(out, bos.size());
        out.write(bos.toByteArray());
        out.writeByte(0x01);
        out.writeByte(0x00);
        DataInputStream in = new DataInputStream(prein);
        int size =  GeneralUtils.readInputStream(in);
        int id = GeneralUtils.readInputStream(in);
        if (id != 0x00) {
            IRCUtils.sendError(user, network, channel, "Error parsing packet response", prefix);
            return;
        }
        int length = GeneralUtils.readInputStream(in);
        if (length == 0 || length == -1) {
            IRCUtils.sendError(user, network, channel, "Error parsing packet response", prefix);
            return;
        }
        byte[] bits = new byte[length];
        in.readFully(bits);
        String json = new String(bits);
        long now = System.currentTimeMillis();
        out.writeByte(0x09); //size of packet
        out.writeByte(0x01); //0x01 for ping
        out.writeLong(now); //time!?
        size =  GeneralUtils.readInputStream(in);
        id = GeneralUtils.readInputStream(in);
        if (id != 0x01) {
            IRCUtils.sendError(user, network, channel, "Error parsing packet response", prefix);
            return;
        }
        long pingtime = in.readLong(); //read response
        JsonObject response = new JsonParser().parse(json).getAsJsonObject();
        out.close();
        preout.close();
        inreader.close();
        prein.close();
        socket.close();

        String gameVersion = "VERSION: " + response.get("version").getAsJsonObject().get("name").getAsString();
        String motd;
        try {
            motd = "MOTD: " + response.get("description").getAsJsonObject().get("text").getAsString();
        }catch(IllegalStateException e){
            motd = "MOTD: " + response.get("description").getAsString();
        }
        String playercount = "Players: " + response.get("players").getAsJsonObject().get("online").getAsString() + "/" + response.get("players").getAsJsonObject().get("max").getAsString();
        List<String> players = new ArrayList<>();
        if(response.get("players").getAsJsonObject().get("online").getAsInt() > 0 && response.get("players").getAsJsonObject().get("sample") != null){
            for(JsonElement e:response.get("players").getAsJsonObject().get("sample").getAsJsonArray()){
                players.add(e.getAsJsonObject().get("name").getAsString());
            }
            playercount += " (" + StringUtils.join(players, ", ") +")";
        }
        IRCUtils.sendMessage(user, network, channel, "[" + address.getHostString() + ":" + port + "] " + gameVersion + " - " + motd + " - " + playercount, prefix);
    }

}

