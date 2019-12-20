package com.karateman2400.discordbotcore.internal.commands;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * All commands should implement Command.
 */
public interface Command {

    /**
     * Executes the command.
     * @param event Event command was received on
     * @param args Command arguments
     * @return Success or Failure
     */
    boolean execute(MessageReceivedEvent event, String[] args);

    /**
     * Checks for command permission.
     * @return Member has permission or not
     */
    boolean hasPermission(Member member);

    /**
     * Checks if the channel is valid.
     * @param channel Channel command was executed in
     * @return Valid channel or not
     */
    boolean validChannel(GuildChannel channel);

    /**
     * Retrives all command aliases
     * @return Command aliases
     */
    List<String> aliases();

    /**
     * Retrieves all command arguments
     * @return Command arguments
     */
    List<String> getArguments();

}
