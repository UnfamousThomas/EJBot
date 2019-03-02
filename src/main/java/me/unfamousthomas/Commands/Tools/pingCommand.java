package me.unfamousthomas.Commands.Tools;

import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class pingCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue(message -> {
            message.editMessageFormat("Ping is %sms", event.getJDA().getPing()).queue();
        });
    }

    @Override
    public String getHelp() {
        return "Tells you the ping of the bot.\n" +
                "Usage: " + constants.PREFIX + "ping";
    }

    @Override
    public String getInvoke() {
        return "ping";
    }
}

