lineman-maven-plugin
====================

maven plugin for lineman. Heavily inspired by the [yeoman maven plugin](https://github.com/trecloux/yeoman-maven-plugin/)

Using the plugin
----------------

This plugin assumes that the web part of your project is placed in ``${basedir}/src/main/webapp``
and that there are the needed configuration files for Grunt and npm in this directory
(Gruntfile.js, package.json and the config directory)

Insert the following into your ***pom.xml***

 ```xml
<plugin>
    <groupId>com.github.maxxkrakoa.lineman-maven-plugin</groupId>
    <artifactId>lineman-maven-plugin</artifactId>
    <version>1.6</version>
    <executions>
        <execution>
            <id>lineman-clean</id>
            <!-- run lineman clean in the pre-clean phase to get correct ordering with maven-clean-plugin -->
            <phase>pre-clean</phase>
            <goals>
                <goal>clean</goal>
            </goals>
        </execution>

        <execution>
            <id>lineman-build</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>build</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

This will take care of running lineman clean, npm clean and lineman build at the appropriate times in the maven build cycle.

To start the lineman watch process run ``mvn lineman:run``

To start the lineman test server run ``mvn lineman:spec-ci``

Packaging the war file
----------------------
Now configure the ``maven-war-plugin`` to use the output of lineman when packaging the war file. 

Insert the following into your ***pom.xml***

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>2.1.1</version>
    <configuration>
        <warSourceDirectory>${basedir}/src/main/webapp/dist</warSourceDirectory>
    </configuration>
</plugin>
```

Using with jetty
----------------
If you want to use lineman with the ``jetty-maven-plugin`` then you need to configure the webapp section of jetty in your ***pom.xml*** similar to this

```xml
<webApp>
    <resourceBases>
        <resourceBase>src/main/webapp/generated</resourceBase>
    </resourceBases>
</webApp>
```


Configuring lineman
-------------------

If you use this plugin with (for instance) the jetty plugin it makes sense to disable the server part of lineman since you
will already have a server in jetty.

To do that add the following to ***config/application.js***

```javascript
/* don't run the server task as we use jetty for that  */
removeTasks: {
    dev: ["server"]
},
```


Developing the plugin
---------------------

To install into the local repository:

``mvn install``


To install into the nexus repository:

``mvn deploy``


TODO
----
- Make --no-color configurable
- Make --force configurable for build goal
