module mathcat4j.jni.loader.jarjni {
    requires io.questdb.jar.jni;
    requires mathcat4j.jni;
    requires mathcat4j.jni.libs;
    provides onl.mdw.mathcat4j.jni.LibraryLoader with onl.mdw.mathcat4j.jni.loader.jarjni.JarJniLibraryLoader;
}