How to make a release
=====================

The maven release plugin handles most things - including bumping the version number. First make sure everything is committed and pushed.

Then run the following commands:

``mvn release:clean``

``mvn release:prepare``

``mvn release:perform``

The release will now have been tagged, marked as a release on github, built, and pushed to the staging part of the OSS Sonatype Maven Repository.

See the [Sonatype OSS Maven Repository Usage Guide] (https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-8a.ReleaseIt) for details of how to release the build the build from staging.
