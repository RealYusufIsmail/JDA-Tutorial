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

package io.github.realyusufismail.commands.github;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommand;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.builder.slash.SlashCommandBuilder;
import io.github.yusufsdiscordbot.yusufsdiscordcore.jda5.extension.SlashCommandExtender;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class GithubCommand extends SlashCommandExtender {
    private static final String REPO_NAME = "repo_name";

    /**
     * Were the command is created.
     *
     * @param slashCommandEvent The event that is created.
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent slashCommandEvent) {
        var repoName = slashCommandEvent.getOption(REPO_NAME).getAsString();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet =
                    new HttpGet("https://api.github.com/search/repositories?q=" + repoName);
            httpGet.addHeader("User-Agent", "Mozilla/5.0");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(responseString).getAsJsonObject();
            JsonArray items = jsonObject.getAsJsonArray("items");

            if (items.size() == 0) {
                slashCommandEvent.reply("Repo not found").setEphemeral(true).queue();
            } else if (items.size() > 1) {
                var repoNames = new ArrayList<String>();
                for(var item : items) {
                    var repo = item.getAsJsonObject();
                    repoNames.add(repo.get("full_name").getAsString());
                }
                slashCommandEvent.replyEmbeds(new EmbedBuilder()
                        .setTitle("Multiple results found")
                        .setDescription(String.join("\n", repoNames))
                        .build()).queue();
            } else {
                JsonObject firstItem = items.get(0).getAsJsonObject();
                String repoUrl = firstItem.get("html_url").getAsString();
                var time = firstItem.get("created_at").getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date = sdf.parse(time);
                sdf.applyPattern("dd-MM-yyyy");
                String formattedDate = sdf.format(date);

                EmbedBuilder builder = new EmbedBuilder();
                builder.addField("Repo Url", repoUrl, true);
                builder.addField("Created", formattedDate, true);
                builder.addField("Stars", firstItem.get("stargazers_count").getAsString(), true);
                builder.addField("Watchers", firstItem.get("watchers_count").getAsString(), true);
                builder.addField("License",
                                firstItem.get("license").getAsJsonObject().get("name").getAsString(),
                                true);
                builder.setAuthor(firstItem.get("owner").getAsJsonObject().get("login").getAsString(),
                        firstItem.get("owner").getAsJsonObject().get("html_url").getAsString(),
                        firstItem.get("owner")
                                .getAsJsonObject()
                                .get("avatar_url")
                                .getAsString());
                slashCommandEvent.replyEmbeds(builder.build()).queue();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SlashCommand build() {
        return new SlashCommandBuilder("github", "Used to get info about a repo")
                .addOption(OptionType.STRING, REPO_NAME, "Repo name", true)
                .build();
    }
}
