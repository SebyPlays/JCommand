package com.github.sebyplays.jcommand.api;

import com.github.sebyplays.consoleprinterapi.api.ConsolePrinter;
import com.github.sebyplays.jcommand.utils.Command;
import com.github.sebyplays.jcommand.utils.Console;
import com.github.sebyplays.jcommand.utils.PreConditions;
import com.github.sebyplays.jcommand.utils.exceptions.DoubleEntryException;
import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import com.github.sebyplays.logmanager.utils.io.ConsolePrintStream;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class JCommand {

    @Getter private HashMap<String, Command> commands = new HashMap<>();
    @Getter @Setter private String inputPrefix = "Input";
    @Getter private final Scanner scanner = new Scanner(System.in);

    public JCommand(){

    }

    public void launchListeners(){
        new Thread("commandListener"){
            @Override
            public void run() {
                while (true){
                    System.out.print(getInputPrefix() + " > ");
                    callCommand(new Console(), getInput());
                }
            }
        }.start();
    }

    public boolean registerCommand(String commandName, String description, CommandExecutor commandExecutor){
        if(!commands.containsKey(commandName.toLowerCase())){
            this.commands.put(commandName, new Command(commandName, description, commandExecutor));
            return true;
        }
        throw new DoubleEntryException("command already registered");
    }

    @SneakyThrows
    public void unregisterCommand(String commandName){
        if(commands.containsKey(commandName.toLowerCase())){
            this.commands.remove(commandName);
            return;
        }
        LogManager.getLogManager("JCommand").log(LogType.NORMAL, "No such command found!", true, false, true, true);
    }

    public void callCommand(CommandSender commandSender, String commandName){
        if(PreConditions.notNull(commandName)){
            ConsolePrinter.print("No valid input found", true, false, true);
            return;
        }
        String[] temp = commandName.split(" ");
        String strTemp = commandName.replace(temp[0] + " ", "");

        if(commands.containsKey(temp[0].toLowerCase())){
            commands.get(temp[0].toLowerCase()).callExecutor(commandSender, strTemp.split(" "));
            return;
        }
        ConsolePrinter.print("Sorry, \"" + temp[0] + "\" seems not to be registered.", false, false, true);
        return;
    }

    public String getDescription(String commandName){
        return commands.get(commandName).getDescription();
    }

    public String getInput(){
        return scanner.nextLine();
    }
}
