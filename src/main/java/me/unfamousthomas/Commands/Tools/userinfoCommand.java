package me.unfamousthomas.Commands.Tools;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.unfamousthomas.Utils.ICommand;
import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.requests.Route;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class userinfoCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments, check `" + constants.PREFIX + "help " + getInvoke() + "`").queue();
            return;
        }

        String joined = String.join("", args);
        List<User> foundUsers = FinderUtil.findUsers(joined, event.getJDA());

        if (foundUsers.isEmpty()) {

            List<Member> foundMembers = FinderUtil.findMembers(joined, event.getGuild());

            if (foundMembers.isEmpty()) {
                event.getChannel().sendMessage("No users found for `" + joined + "`").queue();
                return;
            }

            foundUsers = foundMembers.stream().map(Member::getUser).collect(Collectors.toList());

        }

        User user = foundUsers.get(0);
        Member member = event.getGuild().getMember(user);

        EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(member.getColor());
                builder.setThumbnail(user.getEffectiveAvatarUrl().replaceFirst("gif", "png"));
                builder.addField("Username#Discriminator", String.format("%#s", user), false);
                builder.addField("Display Name", member.getEffectiveName(), false);
                builder.addField("User Id + Mention", String.format("%s (%s)", user.getId(), member.getAsMention()), false);
                builder.addField("Account Created", user.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
                builder.addField("Guild Joined", member.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
                builder.addField("Online Status", member.getOnlineStatus().name().toLowerCase().replaceAll("_", " "), false);
                builder.addField("Bot Account", user.isBot() ? "Yes" : "No", false);

        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Displays information about a user.\n" +
                "Usage: `" + constants.PREFIX + getInvoke() + " <user name/@user/user id>";
    }

    @Override
    public String getInvoke() {
        return "user";
    }
}