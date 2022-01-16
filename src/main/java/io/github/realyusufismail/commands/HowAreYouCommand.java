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

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.interactions.Command;
import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class HowAreYouCommand extends Command {
    //logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HowAreYouCommand.class);
    private static final String REPLY_OPTION = "reply";
    private static final String GOOD = "good";
    private static final String BAD = "bad";

    /**
     * Were the command is registered.
     */
    public HowAreYouCommand() {
        super("how_are_you", "will reply with good or I hope you feel better", true);

        getCommandData()
                .addOptions(new OptionData(OptionType.STRING, REPLY_OPTION, "Choices if you are well or not", true)
                        .addChoice("Good", GOOD).addChoice("Bad", BAD));
    }

    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent slashCommandEvent) {
        String replyOption = slashCommandEvent.getOption(REPLY_OPTION).getAsString();

        if(Objects.equals(replyOption, GOOD)) {
            slashCommandEvent.reply("Nice to see you are doing well")
                    .queue();
        } else if (Objects.equals(replyOption, BAD)) {
            slashCommandEvent.reply("I hope you get better")
                    .queue();
        }
    }
}
