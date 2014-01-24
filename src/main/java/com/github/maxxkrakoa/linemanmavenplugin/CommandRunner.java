package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

public class CommandRunner {

    public void run(String command, File workingDir) throws MojoExecutionException {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                command = "cmd /c " + command.replace("/", File.separator);
            }
            CommandLine cmdLine = CommandLine.parse(command);
            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(workingDir);
            executor.execute(cmdLine);
        } catch (IOException e) {
            throw new MojoExecutionException("Error executing command: '" + command + "'",
                    e);
        }
    }
}
