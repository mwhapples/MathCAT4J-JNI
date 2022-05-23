package onl.mdw.mathkat

import fr.stardustenterprises.yanl.NativeLoader

object MathKat {
    external fun getVersion(): String
    external fun setRulesDir(dir: String)
    external fun getPreference(name: String): String
    external fun setPreference(name: String, value: String)
    external fun setMathml(mathmlStr: String): String
    @JvmOverloads
    external fun getBraille(navigationId: String = ""): String
    init {
        val loader = NativeLoader.Builder().root("/META-INF/native").build()
        loader.loadLibrary("mathkat")
    }
}