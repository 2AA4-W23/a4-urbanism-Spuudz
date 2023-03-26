package configuration;

import org.apache.commons.cli.*;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String SHAPE = "shape";
    public static final String WHITTAKER = "biome";
    public static final String LAKES = "lakes";
    public static final String AQUIFERS = "aquifers";
    public static final String PROFILE = "altitude";
    public static final String SOILPROFILE = "soil";
    public static final String RIVERS = "rivers";

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

    public String whittaker() {
        return this.cli.getOptionValue(WHITTAKER,"jungle");
    }

    public String lakes(){
        return this.cli.getOptionValue(LAKES, "2");
    }
    public String aquifers(){
        return this.cli.getOptionValue(AQUIFERS, "2");
    }

    public String soil_profile(){
        return this.cli.getOptionValue(SOILPROFILE, "arid");
    }
    public String rivers(){
        return this.cli.getOptionValue(RIVERS, "1");
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
        options.addOption(new Option (AQUIFERS, true, "number of aquifers to be generated"));
        options.addOption(new Option (PROFILE, true, "selected elevation profile"));
        options.addOption(new Option (SOILPROFILE, true, "selected soil profile"));
        options.addOption(new Option (RIVERS, true, "number of rivers"));
        options.addOption(new Option (WHITTAKER, true,"whittaker diagram"));
        return options;
    }

}
