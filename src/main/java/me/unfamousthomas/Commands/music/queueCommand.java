package me.unfamousthomas.Commands.music;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import me.unfamousthomas.music.PlayerManager;
import me.unfamousthomas.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;
import java.util.Queue;

public class queueCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = event.getGuild().getAudioManager();
        Guild g = event.getGuild();
        String argsJoined = String.join(" ", args);
        TrackScheduler scheduler = manager.getGuildMusicManager(g).scheduler;

        Queue<AudioTrack> queue = scheduler.queue;
    if (queue.isEmpty()) {
        event.getChannel().sendMessage("No songs found in the queue,").queue();
    } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Current Queue: Entries: ").append(queue.size()).append("\n");
                channel.sendMessage(sb.toString()).queue();
            }
    }

    @Override
    public String getHelp() {
        return "Shows you the current music queue\n" +
        "Usage: `" + constants.PREFIX + getInvoke();
    }

    @Override
    public String getInvoke() {
        return "queue";
    }
}
