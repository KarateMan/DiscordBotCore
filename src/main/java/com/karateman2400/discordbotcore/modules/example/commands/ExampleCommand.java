package com.karateman2400.discordbotcore.modules.example.commands;

import com.karateman2400.discordbotcore.internal.commands.Command;
import com.karateman2400.discordbotcore.internal.commands.CommandClass;
import com.karateman2400.discordbotcore.modules.example.ExampleModule;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CommandClass(module = ExampleModule.class)
public class ExampleCommand implements Command {

    @Override
    public boolean execute(String label, List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("example command").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
        return false;
    }

    @Override
    public boolean hasPermission(Member member) {
        return true;
    }

    @Override
    public boolean validChannel(GuildChannel channel) {
        return true;
    }

    @Override
    public List<String> aliases() {
        return Arrays.asList("example");
    }

}
