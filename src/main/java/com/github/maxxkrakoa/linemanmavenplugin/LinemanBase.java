package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public abstract class LinemanBase extends AbstractMojo {
    /**
     * Specify the basedir of the project, the Maven ${basedir} by default.
     * <p/>
     * All configuration paths are relative to this location.<pre>
     *  <plugin>
     *      <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *      <artifactId>lineman-maven-plugin</artifactId>
     *      <configuration>
     *      <basedir>/some/other/dir</basedir>
     *      </configuration>
     *      ...</pre>
     */
    @Parameter(defaultValue = "${basedir}", required = true, readonly = false)
    protected File basedir;


    /**
     * Specify the location of the root of the webapp files relative to ${basedir} of the project
     * <p/>
     * By default, the plugin expects the webapp to live at "${basedir}/src/main/webapp/" per the maven-war-plugin conventions.
     * <p/>
     * <pre><plugin>
     *  <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *  <artifactId>lineman-maven-plugin</artifactId>
     *  <configuration>
     *      <webappPath>/my/files/are/here</webappPath>
     *  </configuration>
     *  ...</pre>
     */
    @Parameter(defaultValue = "src/main/webapp", required = true, readonly = false)
    protected String webappPath;

    /**
     * Specify the path to the npm installation directory. This is expected to be a standard npm install, the directory defined here should have the npm executable it.
     * For example you want to define /usr/bin/some/custom/install/npm/bin
     * <p/>
     * By default this is empty. Lineman will try and run npm as if it is defined in $PATH
     * <p/>
     * <pre><plugin>
     *  <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *  <artifactId>lineman-maven-plugin</artifactId>
     *  <configuration>
     *      <npmPath>/usr/bin/some/custom/install/npm</npmPath>
     *  </configuration>
     *  ...</pre>
     */
    @Parameter(defaultValue = "", required = false, readonly = false)
    protected String npmPath;

    /**
     * Specify the path to the node installation directory. This is expected to be a standard npm install, the directory defined here should have the node executable it.
     * For example you want to define /usr/bin/some/custom/install/node
     *  <p/>
     * By default this is empty. Lineman will try and run node as if it is defined in $PATH
     * <p/>
     * <pre><plugin>
     *  <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *  <artifactId>lineman-maven-plugin</artifactId>
     *  <configuration>
     *      <nodePath>/usr/bin/some/custom/install/node</nodePath>
     *  </configuration>
     *  ...</pre>
     */
    @Parameter(defaultValue = "", required = false, readonly = false)
    protected String nodePath;

    /**
     * run node install
     *
     * By default this is true. Lineman will try and run npm install first
     * <p/>
     * <pre><plugin>
     *  <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *  <artifactId>lineman-maven-plugin</artifactId>
     *  <configuration>
     *      <runNpmInstall>true</runNpmInstall>
     *  </configuration>
     *  ...</pre>
     */
    @Parameter(defaultValue = "true", required = false, readonly = false)
    protected boolean runNpmInstall;


    /**
     * @return the combined basedir/webappPath as a File object, using both defaults and configured values.
     */
    protected File buildWebappDir() {
        File webappDir = new File(basedir, webappPath);
        getLog().info("webappDir=" + webappDir.getAbsolutePath());
        return webappDir;
    }

    protected List<String> buildAdditionalPaths() {
        List<String> additionalPaths = new ArrayList<String>();

        if (StringUtils.isNotBlank(npmPath)) {
            additionalPaths.add(new File(basedir, npmPath).getAbsolutePath());
        }

        if (StringUtils.isNotBlank(nodePath)) {
            additionalPaths.add(new File(basedir, nodePath).getAbsolutePath());
        }

        return additionalPaths;
    }

    /**
     * Run `npm install` using the configured or default npmInstallPath
     *
     * @param runner a CommandRunner to use to run the command
     * @throws MojoExecutionException
     */
    protected void npmInstall(CommandRunner runner) throws MojoExecutionException {
        if(runNpmInstall) {
            //TODO: this does not seem to work see https://github.com/maxxkrakoa/lineman-maven-plugin/issues/8
            getLog().info("Running npm install with npm path=" + npmPath + "and node path=" + nodePath);
            runner.run("npm install", buildWebappDir(), buildAdditionalPaths());
        }
    }
}

