package com.islam97.android.apps.users.data.room

import androidx.room.TypeConverter
import com.islam97.android.apps.users.domain.models.Gender

class Converters {
    @TypeConverter
    fun fromGender(value: String): Gender {
        return Gender.valueOf(value)
    }

    @TypeConverter
    fun toGender(gender: Gender): String {
        return gender.name
    }
}