package com.github.maxxkrakoa.linemanmavenplugin;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

public class CommandRunner {
    public void run(String command, File workingDir, List<String> additionalPaths) throws MojoExecutionException {
            try {
                if (SystemUtils.IS_OS_WINDOWS) {
                    command = "cmd /c " + command.replace("/", File.separator);
                }

                CommandLine cmdLine = CommandLine.parse(command);

                DefaultExecutor executor = new DefaultExecutor();
                executor.setWorkingDirectory(workingDir);

                Map<String, String> environment = EnvironmentUtils.getProcEnvironment();

                if(additionalPaths != null){
                    String path = environment.get("PATH");

                    if(path == null){
                        path = new String();
                    }

                    String pathSeparator = ":";

                    if (SystemUtils.IS_OS_WINDOWS) {
                        pathSeparator = ";";
                    }

                    for(String additionalPath : additionalPaths){
                        if(additionalPath != null) {
                            path = path + pathSeparator + additionalPath;
                        }
                    }

                    System.out.println("PATH=" + path);
                    environment.put("PATH", path);
                }

                //TODO: should this return code be inspected and an exception thrown if it is not successful
                int returnCode = executor.execute(cmdLine, environment);
            } catch (IOException e) {
                throw new MojoExecutionException("Error executing command: '" + command + "'",
                        e);
            }
        }
}
