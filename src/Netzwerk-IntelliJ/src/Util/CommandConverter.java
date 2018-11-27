package Util;

import com.sun.media.sound.InvalidDataException;

public class CommandConverter {
    private static final char startChar = '<';
    private static final char lastChar = '>';


    private CommandConverter() {
    }


    public static Command construct(String text) throws InvalidDataException, CommandNotFoundException {
        if (!isValidCommand(text)) {
            throw new InvalidDataException("Message is in invalid format");
        }
        String commandText = text.substring(1, text.length() - 2);


        for (Command c : Command.values()) {
            if (c.name().equals(commandText)) {
                return c;
            }
        }
        throw new CommandNotFoundException();
    }


    public static String destruct(Command command) {
        String commandText = "";
        switch (command) {
            case LOGOUT:
                commandText = "LOGOUT";
                break;
            default:
                throw new UnsupportedOperationException("This command is not supported!");
        }

        return startChar + commandText + lastChar;
    }


    public static boolean isValidCommand(String message) {
        return message.charAt(0) == startChar // Fist char is startChar
                && message.indexOf(lastChar, 1) == message.length() - 1; // next instance of lastChar is the last char
    }
}
