package me.unfamousthomas.Commands.Admin;

import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class setprefix implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Member m = event.getMember();
        TextChannel c = event.getChannel();

        if(!m.hasPermission(Permission.MANAGE_SERVER)) {
            c.sendMessage("You need the MANAGE SERVER permission to use this command.").queue();
            return;
        }
        if(args.isEmpty()) {
            c.sendMessage("Usage: " + constants.PREFIX +  getInvoke() + " [new prefix]").queue();
            return;
        }
        String newprefix = args.get(0);
        constants.PREFIXES.put(event.getGuild().getIdLong(), newprefix);
        c.sendMessage("The prefix has been set to '" + newprefix + "'").queue();
    }

    @Override
    public String getHelp() {
        return "Lets you set a new prefix for the bot. (PERMISSIONS: MANAGE SERVER)\n" +
                "Usage: " + constants.PREFIX +  getInvoke() + " setprefix <new prefix>";
    }

    @Override
    public String getInvoke() {
        return "setprefix";
    }
}
