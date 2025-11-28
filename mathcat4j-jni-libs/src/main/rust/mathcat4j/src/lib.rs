/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022-2025 Michael Whapples
 */
use std::ops::Index;
use jni::objects::{JObject, JString, JValue};
use jni::sys::{jboolean, jint, jobject, jobjectArray, jsize, jstring, JNI_TRUE};
use jni::JNIEnv;
use libmathcat::errors::Error;
use libmathcat::*;
use std::panic;

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getVersion(env: JNIEnv, _obj: JObject) -> jstring {
    catch_unwind_to_exception(env, || env.new_string(get_version()).expect("Could not create java string").into_inner())
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_setRulesDir(env: JNIEnv, _obj: JObject, dir: JString) {
    catch_unwind_to_exception(env, || {
        let dir = env.get_string(dir).expect("Could not get string value of dir").into();
        let _ = set_rules_dir(dir).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
        JObject::null().into_inner()
    });
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_setPreference(env: JNIEnv, _obj: JObject, name: JString, value: JString) {
    catch_unwind_to_exception(env, || {
        let name = env.get_string(name).expect("Could not get string for name").into();
        let value = env.get_string(value).expect("Could not get string for value").into();
        let _ = set_preference(name, value).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
        JObject::null().into_inner()
    });
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getPreference(env: JNIEnv, _obj: JObject, name: JString) -> jstring {
    catch_unwind_to_exception(env, || {
        let name = env.get_string(name).expect("Could not get string for name").into();
        let result = get_preference(name);
        get_string_or_throw(env, result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_setMathml(env: JNIEnv, _obj: JObject, mathml_str: JString) -> jstring {
    catch_unwind_to_exception(env, || {
        let mathml_str = env.get_string(mathml_str).expect("Could not get string value of mathml_str").into();
        let canonical_mathml_result = set_mathml(mathml_str);
        get_string_or_throw(env, canonical_mathml_result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getNavigationBraille(env: JNIEnv, _obj: JObject) -> jstring {
    catch_unwind_to_exception(env, || get_string_or_throw(env, get_navigation_braille()))
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getBraille(env: JNIEnv, _obj: JObject, navigation_id: JString) -> jstring {
    catch_unwind_to_exception(env, || {
        let navigation_id = env.get_string(navigation_id).expect("Could not get Java String for navigation_id").into();
        let braille_result = get_braille(navigation_id);
        get_string_or_throw(env, braille_result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getSpokenText(env: JNIEnv, _obj: JObject) -> jstring {
    catch_unwind_to_exception(env, || {
        let spoken_result = get_spoken_text();
        get_string_or_throw(env, spoken_result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getOverviewText(env: JNIEnv, _obj: JObject) -> jstring {
    catch_unwind_to_exception(env, || {
        let overview_result = get_overview_text();
        get_string_or_throw(env, overview_result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_doNavigateKeypress(env: JNIEnv, _obj: JObject, key: jint, shift_key: jboolean, control_key: jboolean, alt_key: jboolean, meta_key: jboolean) -> jstring {
    catch_unwind_to_exception(env, || {
        let result = do_navigate_keypress(key as usize, shift_key == JNI_TRUE, control_key == JNI_TRUE, alt_key == JNI_TRUE, meta_key == JNI_TRUE);
        get_string_or_throw(env, result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_doNavigateCommand(env: JNIEnv, _obj: JObject, command: JString) -> jstring {
    catch_unwind_to_exception(env, || {
        let command = env.get_string(command).expect("Could not get Java String for command").into();
        let result = do_navigate_command(command);
        get_string_or_throw(env, result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getNavigationMathml(env: JNIEnv, _obj: JObject) -> jobject {
    catch_unwind_to_exception(env, || {
        let result = get_navigation_mathml();
        get_navigation_position_or_throw(env, "onl/mdw/mathcat4j/api/NavigationNode", result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getNavigationMathmlId(env: JNIEnv, _obj: JObject) -> jobject {
    catch_unwind_to_exception(env, || {
        let result = get_navigation_mathml_id();
        get_navigation_position_or_throw(env, "onl/mdw/mathcat4j/api/NavigationId", result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_setNavigationNode(env: JNIEnv, _obj: JObject, id: JString, offset: jint) {
    catch_unwind_to_exception(env, || {
        let id_str = env.get_string(id).expect("Could not get Java string for id").into();
        let _ = set_navigation_node(id_str, offset as usize).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
        JObject::null().into_inner()
    });
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getBraillePosition(env: JNIEnv, _obj: JObject) -> jobject {
    catch_unwind_to_exception(env, || {
        let result = get_braille_position();
        get_position_range_or_throw(env, "onl/mdw/mathcat4j/api/PositionRange", result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getNavigationNodeFromBraillePosition(env: JNIEnv, _obj: JObject, position: jint) -> jobject {
    catch_unwind_to_exception(env, || {
        let result = get_navigation_node_from_braille_position(position as usize);
        get_navigation_position_or_throw(env, "onl/mdw/mathcat4j/api/NavigationNode", result)
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getSupportedBrailleCodes(env: JNIEnv, _obj: JObject) -> jobjectArray {
    catch_unwind_to_exception(env, || {
        get_java_string_array(env, get_supported_braille_codes())
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getSupportedLanguages(env: JNIEnv, _obj: JObject) -> jobjectArray {
    catch_unwind_to_exception(env, || {
        get_java_string_array(env, get_supported_languages())
    })
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_jni_MathCatJni_getSupportedSpeechStyles(env: JNIEnv, _obj: JObject, lang: JString) -> jobjectArray {
    catch_unwind_to_exception(env, || {
        get_java_string_array(env, get_supported_speech_styles(env.get_string(lang).expect("Unable to get lang").into()))
    })
}
fn get_java_string_array(env: JNIEnv, vals: Vec<String>) -> jobjectArray {
    let array = env.new_object_array(vals.len() as jsize, "java/lang/String", JObject::null()).expect("Problem creating array");
    for i in 0..vals.len() {
        env.set_object_array_element(array, i as jsize, env.new_string(vals.index(i)).expect("Problem creating java string")).expect("Unable to set array value.");
    }
    array
}

fn get_position_range_or_throw(env: JNIEnv, cls: &str, result: Result<(usize, usize), Error>) -> jobject {
    let signature = "(I;I)V";
    match result {
        Ok((start, end)) => {
            let arguments = &[JValue::from(jint::try_from(start).unwrap()), JValue::from(jint::try_from(end).unwrap())];
            env.new_object(cls, signature, arguments).unwrap_or_else(|_| {
                if !env.exception_check().unwrap() {
                    let _ = env.throw_new("java/lang/RuntimeException", "Unknown error whilst creating object");
                }
                JObject::null()
            }).into_inner()
        },
        Err(e) => {
            let _ = env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}

fn get_navigation_position_or_throw(env: JNIEnv, cls: &str, result: Result<(String, usize), Error>) -> jobject {
    let signature = "(Ljava/lang/String;I)V";
    match result {
        Ok((id, offset)) => {
            let arguments = &[JValue::from(env.new_string(id).expect("Unable to create Java string")), JValue::from(jint::try_from(offset).unwrap())];
            env.new_object(cls, signature, arguments).unwrap_or_else(|_| {
                if !env.exception_check().unwrap() {
                    let _ = env.throw_new("java/lang/RuntimeException", "Unknown error whilst creating object");
                }
                JObject::null()
            }).into_inner()
        },
        Err(e) => {
            let _ = env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}

fn get_string_or_throw(env: JNIEnv, result: Result<String, Error>) -> jstring {
    match result {
        Ok(s) => env.new_string(s).expect("Could not create Java String").into_inner(),
        Err(e) => {
            let _ = env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}

fn catch_unwind_to_exception<F: FnOnce() -> jobject + panic::UnwindSafe>(env: JNIEnv, f: F) -> jobject {
    let result = panic::catch_unwind(f);
    match result {
        Ok(s) => s,
        Err(_) => {
            let _ = env.throw_new("onl/mdw/mathcat4j/api/PanicException", "Panic in MathCAT");
            JObject::null().into_inner()
        }
    }
}