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

package io.github.realyusufismail;

import io.github.realyusufismail.commands.HowAreYouCommand;
import io.github.realyusufismail.commands.PingCommand;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.example.ExampleCommandHandler;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.handler.CoreSlashCommandHandler;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.interactions.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends CoreSlashCommandHandler {
    /**
     * For an example please see {@link ExampleCommandHandler#ExampleCommandHandler(JDA, Guild)}
     *
     * @param jda used to register global command
     * @param guild used to register guild commands
     */
    protected CommandHandler(@NotNull JDA jda, @NotNull Guild guild) {
        super(jda, guild);

        List<Command> handler = new ArrayList<>();

        handler.add(new PingCommand());
        handler.add(new HowAreYouCommand());

        queueAndRegisterCommands(handler);
    }

    /**
     * @return used to set the bot owner id.
     */
    @Override
    protected Long botOwnerId() {
        return null;
    }
}
