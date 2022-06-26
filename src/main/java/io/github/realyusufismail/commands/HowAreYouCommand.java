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

package io.github.realyusufismail.commands;

import java.util.Objects;

import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommand;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommandBuilder;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.extension.SlashCommandExtender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class HowAreYouCommand extends SlashCommandExtender {
    private static final String REPLY_OPTION = "reply";
    private static final String GOOD = "good";
    private static final String BAD = "bad";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String replyOption = event.getOption(REPLY_OPTION).getAsString();

        if(Objects.equals(replyOption, GOOD)) {
            event.reply("Nice to see you are doing well")
                    .queue();
        } else if (Objects.equals(replyOption, BAD)) {
            event.reply("I hope you get better")
                    .queue();
        }
    }

    @Override
    public SlashCommand build() {
        return new SlashCommandBuilder("howareyou", "Replies with how are you")
                .addOptions(new OptionData(OptionType.STRING, REPLY_OPTION, "Good or bad", true)
                        .addChoice("Good", GOOD)
                        .addChoice("Bad", BAD))
                .build();
    }
}
