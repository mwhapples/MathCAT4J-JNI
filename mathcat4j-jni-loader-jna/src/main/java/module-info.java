module mathcat4j.jni.loader.jna {
    requires mathcat4j.jni;
    requires mathcat4j.jni.libs;
    requires com.sun.jna;
    provides onl.mdw.mathcat4j.jni.LibraryLoader with onl.mdw.mathcat4j.jni.loader.jna.JnaLibraryLoader;
}