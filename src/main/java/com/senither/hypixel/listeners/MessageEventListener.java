/*
 * Copyright (c) 2019.
 *
 * This file is part of Hypixel Guild Synchronizer.
 *
 * Hypixel Guild Synchronizer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hypixel Guild Synchronizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hypixel Guild Synchronizer.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.senither.hypixel.listeners;

import com.senither.hypixel.Constants;
import com.senither.hypixel.GuildSynchronize;
import com.senither.hypixel.commands.CommandHandler;
import com.senither.hypixel.contracts.commands.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class MessageEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageEventListener.class);
    private static final Pattern userRegEX = Pattern.compile("<@(!|)+[0-9]{16,}+>", Pattern.CASE_INSENSITIVE);
    private final static String COMMAND_OUTPUT = "Executing Command \"%command%\""
        + "\n\t\tUser:\t %author%"
        + "\n\t\tServer:\t %server%"
        + "\n\t\tChannel: %channel%"
        + "\n\t\tMessage: %message%";

    private final GuildSynchronize guildSynchronize;

    public MessageEventListener(GuildSynchronize guildSynchronize) {
        this.guildSynchronize = guildSynchronize;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        boolean isMentionable = isMentionableAction(event);

        String part = event.getMessage().getContentRaw().split(" ")[isMentionable ? 1 : 0];

        Command command = CommandHandler.getCommand((isMentionable ? Constants.COMMAND_PREFIX : "") + part);

        if (command != null) {
            log.info(COMMAND_OUTPUT
                .replace("%command%", command.getClass().getSimpleName())
                .replace("%author%", generateUsername(event.getMessage()))
                .replace("%channel%", generateChannel(event.getMessage()))
                .replace("%server%", generateServer(event.getMessage()))
                .replace("%message%", event.getMessage().getContentRaw())
            );

            CommandHandler.invokeCommand(event, command, isMentionable);
        }

        log.info("{} said \"{}\"", event.getAuthor().getAsTag(), event.getMessage().getContentRaw());
    }

    private boolean isMentionableAction(MessageReceivedEvent event) {
        if (!event.getMessage().isMentioned(event.getGuild().getSelfMember())) {
            return false;
        }

        String[] args = event.getMessage().getContentRaw().split(" ");

        return args.length >= 2 && userRegEX.matcher(args[0]).matches();
    }

    private String generateUsername(Message message) {
        return String.format("%s#%s [%s]",
            message.getAuthor().getName(),
            message.getAuthor().getDiscriminator(),
            message.getAuthor().getId()
        );
    }

    private String generateServer(Message message) {
        if (!message.getChannelType().isGuild()) {
            return "PRIVATE";
        }

        return String.format("%s [%s]",
            message.getGuild().getName(),
            message.getGuild().getId()
        );
    }

    private CharSequence generateChannel(Message message) {
        if (!message.getChannelType().isGuild()) {
            return "PRIVATE";
        }

        return String.format("%s [%s]",
            message.getChannel().getName(),
            message.getChannel().getId()
        );
    }
}
