# MathCAT4J
MathCAT is a library for converting mathematics into Braille and speech. The MathCAT4J library is a set of Java bindings for the MathCAT library.
## Getting MathCAT4J
MathCAT4J can be found on maven central. The builds on Maven central include binaries for the following platforms:
* Windows X86
* Windows X86_64
* Linux X86_64
* Linux Arm
* Linux AArch64
* MacOSX X86_64
* MacOSX AArch64

If you need a platform not mentioned above, then you should be able to build it on the appropriate system.
## Building MathCAT4J
MathCAT4J uses the Gradle build system and is supplied with the gradlew wrapper scripts. You will need to have a Java Development Kit (JDK) installed. You also will need the rust build tools installed, including the cargo package manager.

Once you have all the build dependencies installed, you can build with a command like:

gradlew clean build

You may publish MathCAT4J into your local maven repository with a command like:

gradlew publishToMavenLocal
