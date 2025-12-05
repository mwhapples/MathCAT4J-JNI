module mathcat4j.jni.test {
    requires mathcat4j.jni;
    requires mathcat4j.jni.libs;
    requires com.sun.jna;
    requires org.assertj.core;
    requires org.junit.jupiter.api;
    provides onl.mdw.mathcat4j.jni.LibraryLoader with onl.mdw.mathcat4j.jni.test.TestLibraryLoader;
    uses onl.mdw.mathcat4j.jni.LibraryLoader;
    opens onl.mdw.mathcat4j.jni.test to org.junit.platform.commons;
}