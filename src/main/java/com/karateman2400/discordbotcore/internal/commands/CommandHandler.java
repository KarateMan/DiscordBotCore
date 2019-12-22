package com.karateman2400.discordbotcore.internal.commands;

import com.karateman2400.discordbotcore.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandHandler extends ListenerAdapter {

    private HashMap<List<String>, Command> commandMap;
    private List<String> commandList;

    public CommandHandler() {
        commandMap = Bot.getCommandRegistry().getCommandsAliases();
        commandList = new ArrayList<>();
        for (List<String> commands : commandMap.keySet()) commandList.addAll(commands);
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(Bot.getConfigManager().getCoreConfig().isThreaded()) {
            new Thread(() -> {
                handleCommand(event);
            }).start();
        } else {
            handleCommand(event);
        }
    }

    private void handleCommand(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if(!event.getMessage().getContentRaw().startsWith(Bot.getConfigManager().getCoreConfig().getPrefix())) return;

        String label = event.getMessage().getContentRaw().split(" ")[0].replace(Bot.getConfigManager().getCoreConfig().getPrefix(), "");
        List<String> args = Arrays.asList(event.getMessage().getContentRaw().split(" "));

        if(!commandList.contains(label)) return;

        for(List<String> commandAliases : commandMap.keySet()) {
            if(!commandAliases.contains(label)) continue;
            Command command = commandMap.get(commandAliases);
            if(validCommand(command, args, event)) {
                if(!command.execute(label, args, event)) event.getMessage().delete().queue();
            }
            break;
        }
    }

    private boolean validCommand(Command command, List<String> args, GuildMessageReceivedEvent event) {
        if(!hasPermission(command, event.getMember())) {
            //TODO: Write embed for error
            return false;
        }

        if(!validChannel(command, event.getChannel())) {
            //TODO: Write embed for error
            return false;
        }
        return true;
    }

    private boolean hasPermission(Command command, Member member) {
        if(command.hasPermission(member)) return true;
        return false;
    }

    private boolean validChannel(Command command, GuildChannel channel) {
        if(command.validChannel(channel)) return true;
        return false;
    }

}
