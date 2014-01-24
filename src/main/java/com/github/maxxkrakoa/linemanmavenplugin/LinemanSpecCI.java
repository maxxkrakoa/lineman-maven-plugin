package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

@Mojo(name = "spec-ci")
public class LinemanSpecCI extends AbstractMojo {
    /**
     * This is the basedir of the project
     *
     * @parameter default-value="${basedir}"
     * @required
     * @readonly
     */
    private File basedir;

    public void execute() throws MojoExecutionException {
        getLog().info("Running lineman spec-ci...");

        File webappDir = new File(basedir, "src/main/webapp/");

        CommandRunner runner = new CommandRunner();
        // make sure the environment is in place by running npm install
        runner.run("npm install", webappDir);
        // run lineman spec-ci
        // TODO: make it configurable to use --force
        //runner.run("./node_modules/.bin/lineman spec-ci --force --no-color", webappDir);
        runner.run("./node_modules/.bin/lineman spec-ci --no-color", webappDir);
    }

}
