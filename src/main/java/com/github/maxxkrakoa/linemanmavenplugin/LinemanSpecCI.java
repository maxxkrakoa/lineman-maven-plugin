package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

@Mojo(name = "spec-ci")
public class LinemanSpecCI extends LinemanBase {

    @Parameter( alias = "skipTests", property = "skipTests")
    protected boolean skipTests;

    @Parameter( alias = "mavenTestSkip", property = "maven.test.skip")
    protected boolean mavenTestSkip;


    public void execute() throws MojoExecutionException {
        if (skipTests || mavenTestSkip) {
            getLog().info("Tests are skipped");
        } else {
            getLog().info("Running lineman spec-ci...");

            File webappDir = buildWebappDir();

            CommandRunner runner = new CommandRunner();
            // make sure the environment is in place by running npm install
            npmInstall(runner);
            // run lineman spec-ci
            // TODO: make it configurable to use --force
            //runner.run("./node_modules/.bin/lineman spec-ci --force --no-color", webappDir);
            runner.run("./node_modules/.bin/lineman spec-ci --no-color", webappDir, buildAdditionalPaths());
        }
    }

}
