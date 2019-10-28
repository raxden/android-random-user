package com.core.common.android.extensions

import com.google.gson.*
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)