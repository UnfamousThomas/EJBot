package me.unfamousthomas.Commands;

import me.unfamousthomas.CommandManager;
import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class helpCommand implements ICommand {
    private final CommandManager manager;

    public helpCommand(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            generateAndSendEmbed(event);
            return;
        }
        ICommand command = manager.getCommand(String.join("", args));
        if(command == null) {
            event.getChannel().sendMessage("This command does not exist.\n" +
                    "Use '" + constants.PREFIX + getInvoke() + "' for a list of commands.").queue();
            return;
        }

        String msg = "Command help for " + command.getInvoke() + ":\n" + command.getHelp();
        event.getChannel().sendMessage(msg).queue();
    }
    private void generateAndSendEmbed(GuildMessageReceivedEvent e) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("List of commands:");
            StringBuilder descriptionbuilder = builder.getDescriptionBuilder();



            manager.getCommands().forEach((command) -> descriptionbuilder.append(command.getInvoke()).append('\n')
            );
            e.getChannel().sendMessage(builder.build()).queue();
            e.getChannel().sendMessage("<> - Required argument, [] - Not required argument").queue();
    }

    @Override
    public String getHelp() {
        return "Shows a list of all the commands.\n" +
                "Usage: '" + constants.PREFIX + getInvoke() + "[command]";
    }

    @Override
    public String getInvoke() {
        return "help";
    }
}
