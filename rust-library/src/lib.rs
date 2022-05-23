use jni::JNIEnv;
use jni::objects::{JObject, JString};
use jni::sys::jstring;
use libmathcat::{get_version, set_rules_dir, set_mathml, get_braille, errors_to_string};
use libmathcat::errors::Error;

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_getVersion(env: JNIEnv, _obj: JObject) -> jstring {
    let output = env.new_string(get_version()).expect("Could not create java string");
    output.into_inner()
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_setRulesDir(env: JNIEnv, _obj: JObject, dir: JString) {
    let dir = env.get_string(dir).expect("Could not get string value of dir").into();
    let _ = set_rules_dir(dir).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_setMathml(env: JNIEnv, _obj: JObject, mathml_str: JString) -> jstring {
    let mathml_str = env.get_string(mathml_str).expect("Could not get string value of mathml_str").into();
    let canonical_mathml_result = set_mathml(mathml_str);
    get_string_or_throw(env, canonical_mathml_result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_getBraille(env: JNIEnv, _obj: JObject, navigation_id: JString) -> jstring {
    let navigation_id = env.get_string(navigation_id).expect("Could not get Java String for navigation_id").into();
    let braille_result = get_braille(navigation_id);
    get_string_or_throw(env, braille_result)
}

fn get_string_or_throw(env: JNIEnv, result: Result<String, Error>) -> jstring {
    match result {
        Ok(s) => env.new_string(s).expect("Could not create Java String").into_inner(),
        Err(e) => {
            env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}