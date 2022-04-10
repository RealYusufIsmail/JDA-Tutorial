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
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class PurgeCommand extends Command {
    private static final String AMOUNT_OF_MESSAGES = "amount";

    public PurgeCommand() {
        super("purge", "Allows you to delete a batch of messages", true);

       getCommandData()
               .addOptions(new OptionData(OptionType.INTEGER, AMOUNT_OF_MESSAGES, "The amount of messages to delete")
                       .setRequiredRange(2, 100));
    }


    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired.
     */
    @Override
    public void onSlashCommand(SlashCommandEvent slashCommandEvent) {
        var amount = slashCommandEvent.getOption(AMOUNT_OF_MESSAGES).getAsLong();
        var channel = slashCommandEvent.getChannel();
        var author = slashCommandEvent.getMember();
        var bot = slashCommandEvent.getGuild().getSelfMember();

        if (!bot.hasPermission(Permission.MESSAGE_MANAGE)) {
            slashCommandEvent.reply("I don't have the permission MESSAGE MANAGE")
                    .setEphemeral(true)
                    .queue();
        }

        if(!author.hasPermission(Permission.MESSAGE_MANAGE)) {
            slashCommandEvent.reply("You don't have the permission MESSAGE MANAGE")
                    .setEphemeral(true)
                    .queue();
        }

        channel.getHistory().retrievePast((int) amount).queue(messages -> {
            if(messages.isEmpty()) {
                slashCommandEvent.reply("There are no messages to delete")
                        .setEphemeral(true)
                        .queue();
            } else {
                channel.purgeMessages(messages);
                slashCommandEvent.reply("Deleted " + messages.size() + " messages").queue();
            }
        });
    }
}
