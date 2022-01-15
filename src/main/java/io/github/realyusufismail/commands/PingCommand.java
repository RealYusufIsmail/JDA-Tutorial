package io.github.realyusufismail.commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.interactions.Command;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends Command {
    /**
     * Were the command is registered.
     */
    public PingCommand() {
        super("ping", "Will reply with pong", true, false);
    }

    /**
     * Were the command is created.
     *
     * @param slashCommandEvent the event that is fired.
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent slashCommandEvent) {
        slashCommandEvent.reply("Pong")
                .queue();
    }
}
