package io.github.realyusufismail;

import io.github.yusufsdiscordbot.config.Config;

public class BotConfig {
    protected static String token() {
        return Config.getString("TOKEN");
    }

    protected static String guildId() {
        return Config.getString("GUILD_ID");
    }
}
