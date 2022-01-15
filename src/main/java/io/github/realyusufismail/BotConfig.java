package io.github.realyusufismail;

import io.github.yusufsdiscordbot.config.Config;

public class BotConfig {
    public static String token() {
        return Config.getString("TOKEN");
    }
}
