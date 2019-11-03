package com.core.features.home

import com.core.domain.Gender
import com.core.domain.Location
import com.core.domain.Picture
import com.core.domain.User
import com.core.features.home.model.UserModel

object FakeUserData {

    val users = mutableListOf(
        User(
            "1",
            "Lumi",
            "Joki",
            "lumi@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "2",
            "Annie",
            "Muller",
            "annie@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "3",
            "Nathan",
            "Ray",
            "nathan@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "4",
            "Marcia",
            "Armstrong",
            "marcia@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "5",
            "Alexander",
            "Christiansen",
            "alexander@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "6",
            "a",
            "a",
            "a@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "7",
            "aa",
            "aa",
            "aa@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        ),
        User(
            "8",
            "aaa",
            "aaa",
            "aaa@gmail.com",
            Picture("large", "thumbnail"),
            "phone",
            Gender.MALE,
            Location("street", "number", "city", "thumbnail"),
            null
        )
    )
}