package me.unfamousthomas.Commands.music;

import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import me.unfamousthomas.music.PlayerManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;


import java.util.List;

public class volumeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        Guild g = event.getGuild();
        String argsJoined = String.join(" ", args);

        if(argsJoined.length() == 0) {
        channel.sendMessage("Current volume is: " + manager.getGuildMusicManager(g).player.getVolume()).queue();
        } else {
            String num = args.get(0);
            if(isNumeric(num)) {
                int number = Integer.parseInt(num);
            if(!(number < 0) && !(number > 101)) {
                int oldvolume = manager.getGuildMusicManager(g).player.getVolume();
            channel.sendMessage("Volume has been changed from: " + oldvolume + " to: " + num + ".").queue();
            manager.getGuildMusicManager(g).player.setVolume(number);
        } else {
                channel.sendMessage("Number can only be 0 - 100.").queue();
            }} else{
                channel.sendMessage(num + " does not seem to be a number.").queue();

            }
            }
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public String getHelp() {
        return  "Sets the volume of the bot\n" +
                "Usage: `" + constants.PREFIX + getInvoke() + " <0 - 100>`";
    }

    @Override
    public String getInvoke() {
        return "volume";
    }
}
