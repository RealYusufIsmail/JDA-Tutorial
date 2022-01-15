package io.github.realyusufismail;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder
                .createDefault(BotConfig.token())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing("Example"))
                .build();

        jda.awaitReady()
                .addEventListener(new CommandHandler(jda, jda.getGuildById(904090691343380561L)));
    }
}
