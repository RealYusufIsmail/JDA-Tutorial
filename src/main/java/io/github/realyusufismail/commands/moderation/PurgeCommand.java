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
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class PurgeCommand extends SlashCommandExtender {
    private static final String AMOUNT = "amount";


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent slashCommandEvent) {
        var amount = slashCommandEvent.getOption(AMOUNT).getAsLong();
        var channel = slashCommandEvent.getChannel();

        channel.getHistory().retrievePast((int) amount).queue(messages -> {
            if(messages.isEmpty()) {
                slashCommandEvent.reply("There are no messages to delete!")
                        .setEphemeral(true)
                        .queue();
            } else {
                channel.purgeMessages(messages);
                slashCommandEvent.reply("Successfully deleted " + messages.size() + " messages!").queue();
            }
        });
    }

    @Override
    public SlashCommand build() {
        return new SlashCommandBuilder("purge", "Used to delete messages")
                .addOptions(new OptionData(OptionType.STRING, AMOUNT, "Amount of messages to delete")
                        .setRequiredRange(2, 100))
                .build()
                .setUserPerms(Permission.MESSAGE_MANAGE)
                .setBotPerms(Permission.MESSAGE_MANAGE);
    }
}
