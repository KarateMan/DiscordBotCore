package com.karateman2400.discordbotcore.modules.example.commands;

import com.karateman2400.discordbotcore.internal.commands.Command;
import com.karateman2400.discordbotcore.internal.commands.CommandClass;
import com.karateman2400.discordbotcore.modules.example.ExampleModule;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

@CommandClass(module = ExampleModule.class)
public class NewCommand implements Command {
    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        return false;
    }

    @Override
    public boolean hasPermission(Member member) {
        return false;
    }

    @Override
    public boolean validChannel(GuildChannel channel) {
        return false;
    }

    @Override
    public List<String> aliases() {
        return Arrays.asList("example");
    }

    @Override
    public List<String> getArguments() {
        return null;
    }
}
