package me.unfamousthomas;

import me.unfamousthomas.Commands.Admin.setprefix;
import me.unfamousthomas.Commands.Tools.pingCommand;
import me.unfamousthomas.Commands.Tools.userinfoCommand;
import me.unfamousthomas.Commands.helpCommand;
import me.unfamousthomas.Commands.music.*;
import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {
    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager(Random random) {
        addCommand(new pingCommand());
        addCommand(new setprefix());
        addCommand(new helpCommand(this));
        addCommand(new userinfoCommand());
        addCommand(new joinCommand());
        addCommand(new leaveCommand());
        addCommand(new playCommand());
        addCommand(new pauseCommand());
        addCommand(new volumeCommand());
        //addCommand(new queueCommand());
    }
    private void addCommand(ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent e)  {
        final String prefix = constants.PREFIXES.get(e.getGuild().getIdLong());

        final String[] split = e.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            e.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, e);

        }
    }
}
