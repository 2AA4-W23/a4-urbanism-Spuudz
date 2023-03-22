package configuration;

import org.apache.commons.cli.*;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String SHAPE = "shape";
    public static final String LAKES = "lakes";
    public static final String PROFILE = "altitude";

    private CommandLine cli;
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT,"input.mesh");
    }
    public String profile() {
        return this.cli.getOptionValue(PROFILE,"volcano");
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }

    public String shape() {
        return this.cli.getOptionValue(SHAPE, "Circle");
    }

    public String lakes(){
        return this.cli.getOptionValue(LAKES, "5");
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (SVG)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(SHAPE, true, "the island shape"));
        options.addOption(new Option (LAKES, true, "Number of lakes to be generated"));
        options.addOption(new Option (PROFILE, true, "selected elevation profile"));
        return options;
    }

}
