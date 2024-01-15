package org.yasudis.model.commandParser;

import org.apache.commons.cli.*;
import org.yasudis.model.sortOption.SorterParameterByDataType;


public class CommandParser {
    private Options options;

    public CommandParser() {
        options = new Options();

        options.addOption("i", "input", true, "input file");
        options.addOption("o", "output", true, "output file");
    }
    public CommandLine parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
            printHelp();
            System.exit(1);
            return null;
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("command-name", options);
    }

    public static SorterParameterByDataType sortParameterTypes(String[] args) {
        SorterParameterByDataType sorterParameterByDataType = new SorterParameterByDataType(args);
        return sorterParameterByDataType;
    }
}

