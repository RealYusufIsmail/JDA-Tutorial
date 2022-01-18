/*
 * MIT License
 *
 * Copyright (c) 2021 Yusuf Arfan Ismail
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 */

package io.github.realyusufismail.commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.interactions.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class UnBanCommand extends Command {
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    public UnBanCommand() {
        super("unban", "Used to unban a user", true);

        getCommandData()
                .addOption(OptionType.USER, USER_OPTION, "The user who you want to unban", true)
                .addOption(OptionType.STRING, REASON_OPTION, "The reason why your are unbanning the user",
                        true);
    }
    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired.
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent slashCommandEvent) {
        User targetUser = slashCommandEvent.getOption(USER_OPTION).getAsUser();
        String reason = slashCommandEvent.getOption(REASON_OPTION).getAsString();
        Guild guild = slashCommandEvent.getGuild();
        Member author = slashCommandEvent.getMember();
        Member bot = guild.getSelfMember();

        if(!author.hasPermission(Permission.BAN_MEMBERS)) {
            slashCommandEvent.reply("You don't have the right perms")
                    .setEphemeral(true)
                    .queue();
        }

        if(!bot.hasPermission(Permission.BAN_MEMBERS)) {
            slashCommandEvent.reply("I don't have the right perms")
                    .setEphemeral(true)
                    .queue();
        }

        if(reason.length() >= 512) {
            slashCommandEvent.reply("The reason can not be longer than 512 characters")
                    .setEphemeral(true)
                    .queue();
        }

        guild.unban(targetUser).reason(reason)
                .flatMap(success -> slashCommandEvent.reply("I have unbanned the user " + targetUser.getAsTag()))
                .queue();
    }
}
