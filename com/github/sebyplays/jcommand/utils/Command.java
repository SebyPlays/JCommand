package com.github.sebyplays.jcommand.utils;

import com.github.sebyplays.jcommand.api.CommandExecutor;
import com.github.sebyplays.jcommand.api.CommandSender;
import lombok.Getter;

public class Command {

    @Getter private String command;
    @Getter private String description;
    @Getter public CommandExecutor commandExecutor;

    public Command(String commandName, String description, CommandExecutor commandExecutor){
        this.command = commandName;
        this.description = description;
        this.commandExecutor = commandExecutor;
    }

    public boolean callExecutor(CommandSender commandSender, String[] args){
        return this.commandExecutor.onCommand(commandSender, this, args);
    }

}
