package io.paulbaker.school.netbeans;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.FileConverter;
import lombok.Getter;

import java.io.File;

import static java.util.Objects.isNull;

/**
 * @author Paul Nelson Baker
 * @see <a href="https://github.com/paul-nelson-baker/">GitHub</a>
 * @see <a href="https://www.linkedin.com/in/paul-n-baker/">LinkedIn</a>
 * @since 2018-07
 * WGU Student Info: pbake22 - #000412290
 */
@Getter
public class ConverterArguments {

    @Parameter(names = {"--input"}, description = "The path to the root of the java-project we need to convert", converter = FileConverter.class, required = true)
    private File inputProjectDirectory;

    @Parameter(names = {"--output"}, description = "The path where the new Netbeans project will be created", converter = FileConverter.class)
    private File outputProjectDirectory;

    @Parameter(names = {"--project-name"}, description = "The name of the newly generated project", required = true)
    private String projectName;

    public static ConverterArguments parseArguments(String[] args) {
        ConverterArguments parsedConverterArguments = new ConverterArguments();
        JCommander build = JCommander.newBuilder()
                .addObject(parsedConverterArguments)
                .build();
        build.setProgramName("netbeans-converter");
        try {
            build.parse(args);
        } catch (ParameterException ignored) {
            build.usage();
            System.exit(1);
        }
        if (!parsedConverterArguments.getInputProjectDirectory().exists()) {
            System.out.println("Input directory does not exist");
            System.exit(1);
        }
        if (isNull(parsedConverterArguments.outputProjectDirectory)) {
            String homeDirectory = System.getProperty("user.home");
            parsedConverterArguments.outputProjectDirectory = new File(homeDirectory, "NetBeansProjects");
        }
        return parsedConverterArguments;
    }
}
