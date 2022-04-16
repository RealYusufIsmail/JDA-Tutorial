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

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.handler.extension.SlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class KickCommand extends SlashCommand {
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";

    public KickCommand() {
        super("kick", "Gives the ability to kick a user", true);

        getCommandData()
                .addOption(OptionType.USER, USER_OPTION, "The member who you want to kick", true)
                .addOption(OptionType.STRING, REASON_OPTION, "The reason why you want to kick the member", true);
    }
    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent slashCommandEvent) {
        Member targetMember = slashCommandEvent.getOption(USER_OPTION).getAsMember();
        Member author = slashCommandEvent.getMember();
        String reason = slashCommandEvent.getOption(REASON_OPTION).getAsString();
        Guild guild = slashCommandEvent.getGuild();

        if(targetMember == null) {
            slashCommandEvent.reply("The provided member is null")
                    .setEphemeral(true)
                    .queue();
        }

        if(!author.hasPermission(Permission.KICK_MEMBERS)) {
            slashCommandEvent.reply("You do not have the required perms to run the command.")
                    .setEphemeral(true)
                    .queue();
        }

        if(!guild.getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
            slashCommandEvent.reply("I do not have the required perms to run the command.")
                    .setEphemeral(true)
                    .queue();
        }

        if(reason.length() >= 512) {
            slashCommandEvent.reply("The reason can not be longer than 512 characters")
                    .setEphemeral(true)
                    .queue();
        }

        guild.kick(targetMember, reason)
                .flatMap(success -> slashCommandEvent.reply("I have kicked the member " +
                        targetMember.getUser().getAsTag()))
                .queue();
    }
}
