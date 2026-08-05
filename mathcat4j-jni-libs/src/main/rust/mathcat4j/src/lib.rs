/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022-2025 Michael Whapples
 */
use jni::objects::{JObject, JObjectArray, JString};
use jni::signature::MethodSignature;
use jni::strings::{JNIStr, JNIString};
use jni::sys::{jboolean, jint, JNI_TRUE};
use jni::NativeMethod;
use jni::{jni_sig, Env, JValue};
use jni::{jni_str, native_method};
use libmathcat::*;
use std::ops::Index;

const JNI_METHODS: &[NativeMethod] = &[
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getVersion",
        extern fn jni_get_version() -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "setRulesDir",
        extern fn jni_set_rules_dir(JString),
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "setPreference",
        extern fn jni_set_preference(JString, JString),
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getPreference",
        extern fn jni_get_preference(JString) -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "setMathml",
        extern fn jni_set_mathml(JString) -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getNavigationBraille",
        extern fn jni_get_navigation_braille() -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getBraille",
        extern fn jni_get_braille(JString) -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getSpokenText",
        extern fn jni_get_spoken_text() -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getOverviewText",
        extern fn jni_get_overview_text() -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "doNavigateKeypress",
        extern fn jni_do_navigate_keypress(jint, jboolean, jboolean, jboolean, jboolean) -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "doNavigateCommand",
        extern fn jni_do_navigate_command(JString) -> JString,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getNavigationMathml",
        extern fn jni_get_navigation_mathml() -> JObject,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getNavigationMathmlId",
        extern fn jni_get_navigation_mathml_id() -> JObject,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "setNavigationNode",
        extern fn jni_set_navigation_node(JString, jint),
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getBraillePosition",
        extern fn jni_get_braille_position() -> JObject,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getNavigationNodeFromBraillePosition",
        extern fn jni_get_navigation_node_from_braille_position(jint) -> JObject,
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getSupportedBrailleCodes",
        extern fn jni_get_supported_braille_codes() -> JString[]
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getSupportedLanguages",
        extern fn jni_get_supported_languages() -> JString[],
    },
    native_method! {
        java_type = "onl.mdw.mathcat4j.jni.MathCatJni",
        name = "getSupportedSpeechStyles",
        extern fn jni_get_supported_speech_styles(JString) -> JString[],
    },
];

fn jni_get_version<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JString<'local>, jni::errors::Error> {
    env.new_string(get_version())
}

fn jni_set_rules_dir<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    dir: JString,
) -> Result<(), jni::errors::Error> {
    let dir = dir.to_string();
    set_rules_dir(dir).or_else(|e| {
        env.throw_new(
            jni_str!("java/lang/RuntimeException"),
            JNIString::new(errors_to_string(&e)),
        )
    })
}

fn jni_set_preference<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    name: JString,
    value: JString,
) -> Result<(), jni::errors::Error> {
    let name = name.to_string();
    let value = value.to_string();
    set_preference(name, value).or_else(|e| {
        env.throw_new(
            jni_str!("java/lang/RuntimeException"),
            JNIString::new(errors_to_string(&e)),
        )
    })
}

fn jni_get_preference<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    name: JString,
) -> Result<JString<'local>, jni::errors::Error> {
    let name = name.to_string();
    match get_preference(name) {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Unable to throw exception")),
    }
}

fn jni_set_mathml<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    mathml_str: JString,
) -> Result<JString<'local>, jni::errors::Error> {
    let mathml_str = mathml_str.to_string();
    match set_mathml(mathml_str) {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_get_navigation_braille<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JString<'local>, jni::errors::Error> {
    match get_navigation_braille() {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_get_braille<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    navigation_id: JString,
) -> Result<JString<'local>, jni::errors::Error> {
    let navigation_id = navigation_id.to_string();
    match get_braille(navigation_id) {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_get_spoken_text<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JString<'local>, jni::errors::Error> {
    match get_spoken_text() {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_get_overview_text<'local>(
    env: &mut Env<'local>,
    _this: JObject,
) -> Result<JString<'local>, jni::errors::Error> {
    match get_overview_text() {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_do_navigate_keypress<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    key: jint,
    shift_key: jboolean,
    control_key: jboolean,
    alt_key: jboolean,
    meta_key: jboolean,
) -> Result<JString<'local>, jni::errors::Error> {
    match do_navigate_keypress(
        key as usize,
        shift_key == JNI_TRUE,
        control_key == JNI_TRUE,
        alt_key == JNI_TRUE,
        meta_key == JNI_TRUE,
    ) {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_do_navigate_command<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    command: JString,
) -> Result<JString<'local>, jni::errors::Error> {
    let command = command.to_string();
    match do_navigate_command(command) {
        Ok(v) => env.new_string(v),
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .expect_err("Cannot throw exception")),
    }
}

fn jni_get_navigation_mathml<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JObject<'local>, jni::errors::Error> {
    let result = get_navigation_mathml();
    new_navigation_position(
        env,
        jni_str!("onl/mdw/mathcat4j/api/NavigationNode"),
        result,
    )
}

fn jni_get_navigation_mathml_id<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JObject<'local>, jni::errors::Error> {
    let result = get_navigation_mathml_id();
    new_navigation_position(env, jni_str!("onl/mdw/mathcat4j/api/NavigationId"), result)
}

fn jni_set_navigation_node<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    id: JString,
    offset: jint,
) -> Result<(), jni::errors::Error> {
    let id = id.to_string();
    set_navigation_node(id, offset as usize).or_else(|e| {
        env.throw_new(
            jni_str!("java/lang/RuntimeException"),
            JNIString::new(errors_to_string(&e)),
        )
    })
}

fn jni_get_braille_position<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JObject<'local>, jni::errors::Error> {
    let result = get_braille_position();
    new_position_range(env, jni_str!("onl/mdw/mathcat4j/api/PositionRange"), result)
}

fn jni_get_navigation_node_from_braille_position<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    position: jint,
) -> Result<JObject<'local>, jni::errors::Error> {
    let result = get_navigation_node_from_braille_position(position as usize);
    new_navigation_position(
        env,
        jni_str!("onl/mdw/mathcat4j/api/NavigationNode"),
        result,
    )
}

fn jni_get_supported_braille_codes<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JObjectArray<'local, JString<'local>>, jni::errors::Error> {
    new_string_array(env, get_supported_braille_codes())
}

fn jni_get_supported_languages<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
) -> Result<JObjectArray<'local, JString<'local>>, jni::errors::Error> {
    new_string_array(env, get_supported_languages())
}

fn jni_get_supported_speech_styles<'local>(
    env: &mut Env<'local>,
    _this: JObject<'local>,
    lang: JString<'local>,
) -> Result<JObjectArray<'local, JString<'local>>, jni::errors::Error> {
    new_string_array(env, get_supported_speech_styles(lang.to_string()))
}

fn new_string_array<'local>(
    env: &mut Env<'local>,
    vals: Vec<String>,
) -> Result<JObjectArray<'local, JString<'local>>, jni::errors::Error> {
    let array = JObjectArray::<JString>::new(env, vals.len(), JString::null())?;
    for i in 0..vals.len() {
        let val = env.new_string(vals.index(i))?;
        array.set_element(env, i, val)?;
    }
    Ok(array)
}
const NAVIGATION_POSITION_CTOR_SIG: MethodSignature = jni_sig!((id: JString, jint) -> void);

fn new_navigation_position<'local>(
    env: &mut Env<'local>,
    cls: &JNIStr,
    result: Result<(String, usize), libmathcat::errors::Error>,
) -> Result<JObject<'local>, jni::errors::Error> {
    match result {
        Ok((id, offset)) => {
            let arguments = &[
                JValue::Object(&JObject::from(env.new_string(id)?)),
                JValue::Int(jint::try_from(offset).unwrap()),
            ];
            env.new_object(cls, NAVIGATION_POSITION_CTOR_SIG, arguments)
        }
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .unwrap_err()),
    }
}

const POSITION_RANGE_SIGNATURE: MethodSignature = jni_sig!((start: jint, end: jint) -> JObject);

fn new_position_range<'local>(
    env: &mut Env<'local>,
    cls: &JNIStr,
    result: Result<(usize, usize), libmathcat::errors::Error>,
) -> Result<JObject<'local>, jni::errors::Error> {
    match result {
        Ok((start, end)) => {
            let arguments = &[
                JValue::Int(jint::try_from(start).unwrap()),
                JValue::Int(jint::try_from(end).unwrap()),
            ];
            env.new_object(cls, POSITION_RANGE_SIGNATURE, arguments)
        }
        Err(e) => Err(env
            .throw_new(
                jni_str!("java/lang/RuntimeException"),
                JNIString::new(errors_to_string(&e)),
            )
            .unwrap_err()),
    }
}
