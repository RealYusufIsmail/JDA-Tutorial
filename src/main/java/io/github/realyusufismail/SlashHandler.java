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
import io.github.realyusufismail.commands.github.GithubCommand;
import io.github.realyusufismail.commands.moderation.BanCommand;
import io.github.realyusufismail.commands.moderation.KickCommand;
import io.github.realyusufismail.commands.moderation.PurgeCommand;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.extension.SlashCommandExtender;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.handler.SlashCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashHandler extends SlashCommandHandler {

    protected SlashHandler(@NotNull JDA jda, @NotNull Guild guild) {
        super(jda, guild);

        List<SlashCommandExtender> handler = new ArrayList<>();

        handler.add(new PingCommand());
        handler.add(new HowAreYouCommand());
        handler.add(new BanCommand());
        handler.add(new KickCommand());
        handler.add(new PurgeCommand());
        handler.add(new GithubCommand());

        queueAndRegisterSlashCommands(handler);
    }

    /**
     * @return used to set the bot owner id.
     */
    @Override
    protected long botOwnerId() {
        return 0;
    }
}
