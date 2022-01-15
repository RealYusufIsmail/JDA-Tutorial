package io.github.realyusufismail;

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
     * @param jda The JDA class
     * @param guild The Guild class
     */
    protected CommandHandler(@NotNull JDA jda, @NotNull Guild guild) {
        super(jda, guild);

        List<Command> handler = new ArrayList<>();

        handler.add(new PingCommand());
        queueAndRegisterCommands(handler);
    }

    /**
     * @return used to set the bot owner id.
     */
    protected long botOwnerId() {
        return 0;
    }
}
