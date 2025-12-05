module mathcat4j.jni {
    requires transitive mathcat4j.api;
    requires org.jspecify;
    exports onl.mdw.mathcat4j.jni;
    provides onl.mdw.mathcat4j.api.MathCatFactory with onl.mdw.mathcat4j.jni.MathCatFactory;
    uses onl.mdw.mathcat4j.jni.LibraryLoader;
}