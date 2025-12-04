# MathCAT4J-JNI
MathCAT is a library for converting mathematics into Braille and speech. MathCAT4J-JNI uses JNI to expose the MathCAT library through the MathCAT4J API.
## Getting MathCAT4J-JNI
MathCAT4J-JNI can be found on maven central. The mathcat4j-jni-libs artifact on Maven central include binaries for the following platforms:
* Windows X86
* Windows X86_64
* Linux X86_64
* Linux Arm
* Linux AArch64
* MacOSX X86_64
* MacOSX AArch64

If you need a platform not mentioned above, then you should be able to build it on the appropriate system.
## Building MathCAT4J-JNI
MathCAT4J-JNI uses the Maven build system and is supplied with the mvnw wrapper scripts. You will need to have a Java Development Kit (JDK) installed. You also will need the rust build tools installed, including the cargo package manager.

Once you have all the build dependencies installed, you can build with a command like:

```
mvnw clean package
```

You may publish MathCAT4J into your local maven repository with a command like:

```
mvnw install
```
