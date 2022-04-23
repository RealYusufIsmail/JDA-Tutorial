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

import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnActionListener extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        var user = event.getUser();
        var guild = event.getJDA().getGuildById(938122131949097052L);
        var channel = guild.getTextChannelById(938122131949097055L);
        channel.sendMessage("**" + user.getAsTag() + "** has left the server.").queue();
    }
}
