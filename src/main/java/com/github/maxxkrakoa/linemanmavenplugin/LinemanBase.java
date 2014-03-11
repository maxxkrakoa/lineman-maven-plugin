package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
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
    @Parameter( defaultValue = "${basedir}", required = true, readonly = false)
    protected File basedir;


    /**
     * Specify the location of the root of the webapp files relative to ${basedir} of the project
     *
     * By default, the plugin expects the webapp to live at "${basedir}/src/main/webapp/" per the maven-war-plugin conventions.
     *
     * <pre><plugin>
     *  <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
     *  <artifactId>lineman-maven-plugin</artifactId>
     *  <configuration>
     *      <webappPath>/my/files/are/here</webappPath>
     *  </configuration>
     *  ...</pre>
     */
    @Parameter( defaultValue = "src/main/webapp", required = true, readonly = false)
    protected String webappPath;


    /**
     * @return the combined basedir/webappPath as a File object, using both defaults and configured values.
     */
    protected File buildWebappDir() {
        File webappDir = new File(basedir, webappPath);
        getLog().info("webappDir=" + webappDir.getAbsolutePath());
        return webappDir;
    }

}

