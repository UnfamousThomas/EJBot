package me.unfamousthomas.Commands.music;

import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import me.unfamousthomas.music.PlayerManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class pauseCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = event.getGuild().getAudioManager();
        Guild g = event.getGuild();

        if(!audioManager.isConnected()) {
            channel.sendMessage("I am not connected to a channel. Do '!join' first.").queue();
        } if(manager.getGuildMusicManager(g).player.isPaused()) {
            manager.getGuildMusicManager(g).player.setPaused(false);
            channel.sendMessage("Music unpaused, continueing to play: " + manager.getGuildMusicManager(g).player.getPlayingTrack().getInfo().title).queue();
        } else {
            manager.getGuildMusicManager(g).player.setPaused(true);
            channel.sendMessage("Music paused, stopped playing " +  manager.getGuildMusicManager(g).player.getPlayingTrack().getInfo().title).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Pauses the song\n" +
                "Usage: `" + constants.PREFIX + getInvoke();
    }

    @Override
    public String getInvoke() {
        return "pause";
    }
}
