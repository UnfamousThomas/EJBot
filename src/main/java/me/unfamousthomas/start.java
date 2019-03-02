package me.unfamousthomas;

import me.unfamousthomas.Utils.constants;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Random;

public class start {
    private final Random random = new Random();
private start() {
    CommandManager commandManager = new CommandManager(random);
    Listener listener = new Listener(commandManager);
    Logger logger = LoggerFactory.getLogger(start.class);


        try {
        logger.info("Booting");
        new JDABuilder(AccountType.BOT)
                .setToken(constants.TOKEN)
                .setGame(Game.playing("Games."))
                .addEventListener(listener)
                .build().awaitReady();
        logger.info("Running");
    } catch (LoginException | InterruptedException e) {
        e.printStackTrace();
    }
}

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        new start();
    }

}

