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

import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommand;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommandBuilder;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.extension.SlashCommandExtender;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanCommand extends SlashCommandExtender {
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    private static final String MESSAGE_DELEATION_OPTION = "delete_message_history_days";


    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent slashCommandEvent) {
        OptionMapping optionMapping = slashCommandEvent.getOption(USER_OPTION);
        String reason = slashCommandEvent.getOption(REASON_OPTION).getAsString();
        User targetUser  = optionMapping.getAsUser();
        Member targetMember = optionMapping.getAsMember();
        Guild guild = slashCommandEvent.getGuild();

        if(reason.length() >= 512) {
            slashCommandEvent.reply("The reason can only be under 512 characters")
                    .setEphemeral(true)
                    .queue();
        }

        int delDays = Math.toIntExact(slashCommandEvent.getOption(MESSAGE_DELEATION_OPTION).getAsLong());
        if(targetMember != null) {
            guild.ban(targetMember, delDays, reason)
            .flatMap(success -> slashCommandEvent.reply("I have banned the member " + targetMember.getUser().getAsTag()))
            .queue();

        } else {
            guild.ban(targetUser, 0, reason)
            .flatMap(success -> slashCommandEvent.reply("I have banned the user " + targetUser.getAsTag()))
            .queue();
        }
    }

    @Override
    public SlashCommand build() {
        return new SlashCommandBuilder("ban", "Ban a user from the server.")
                .addOption(OptionType.USER, USER_OPTION, "The user who you want to ban", true)
                .addOption(OptionType.STRING, REASON_OPTION, "The reason you want to ban the user.", true)
                .addOptions(new OptionData(OptionType.INTEGER, MESSAGE_DELEATION_OPTION, "The amount of days of the message history to delete."
                        ,true)
                        .addChoices(deleationDays))
                .build()
                .setUserPerms(Permission.BAN_MEMBERS)
                .setBotPerms(Permission.BAN_MEMBERS);
    }

    private static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> deleationDays = List
            .of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("One Day", 1),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Two Days", 2),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Three Days", 3),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Four Days", 4),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Five Days", 5),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Six Days", 6),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Seven Days", 7));
}
