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
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanCommand extends Command {
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    private static final String MESSAGE_DELEATION_OPTION = "delete_message_history_days";

    //Message to delete days
    private static final String ONE_DAY = "one_day";
    private static final String TWO_DAYS = "two_days";
    private static final String THREE_DAYS = "three_days";
    private static final String FOUR_DAYS = "four_days";
    private static final String FIVE_DAYS = "five_days";
    private static final String SIX_DAYS = "six_days";
    private static final String SEVEN_DAYS = "seven_days";

    public BanCommand() {
        super("ban", "Ban a user from the server.", true);

        getCommandData()
                .addOption(OptionType.USER, USER_OPTION, "The user who you want to ban", true)
                .addOption(OptionType.STRING, REASON_OPTION, "The reason you want to ban the user.", true)
                .addOptions(new OptionData(OptionType.INTEGER, MESSAGE_DELEATION_OPTION, "The amount of days of the message history to delete."
                        ,true)
                        .addChoices(deleationDays));
    }

    private static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> deleationDays = List
            .of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("One Day", ONE_DAY),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Two Days", TWO_DAYS),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Three Days", THREE_DAYS),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Four Days", FOUR_DAYS),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Five Days", FIVE_DAYS),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Six Days", SIX_DAYS),
                new net.dv8tion.jda.api.interactions.commands.Command.Choice("Seven Days", SEVEN_DAYS));

    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent slashCommandEvent) {
        OptionMapping optionMapping = slashCommandEvent.getOption(USER_OPTION);
        String reason = slashCommandEvent.getOption(REASON_OPTION).getAsString();
        User targetUser  = optionMapping.getAsUser();
        Member targetMember = optionMapping.getAsMember();
        Member author = slashCommandEvent.getMember();
        Guild guild = slashCommandEvent.getGuild();

        if(!author.hasPermission(Permission.BAN_MEMBERS)) {
            slashCommandEvent.reply("You do not have the right perms")
                    .setEphemeral(true)
                    .queue();
        }

        if(!guild.getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            slashCommandEvent.reply("The bot does not have the right perms")
                    .setEphemeral(true)
                    .queue();
        }

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
            guild.ban(targetUser, delDays, reason)
            .flatMap(success -> slashCommandEvent.reply("I have banned the user " + targetUser.getAsTag()))
            .queue();
        }
    }
}
