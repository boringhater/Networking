package com.apmolokanova.networking

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
//@Serializable
@SerialName("location")
data class Location(
	@SerialName("name")
	val name: String? = null,
	@SerialName("url")
	val url: String? = null
) : Parcelable

@Parcelize
//@Serializable
@SerialName("origin")
data class Origin(
	@SerialName("name")
	val name: String? = null,
	@SerialName("url")
	val url: String? = null
): Parcelable

@Parcelize
//@Serializable
@SerialName("Character")
data class Character(
	@SerialName("image")
	val image: String? = null,
	@SerialName("gender")
	val gender: String? = null,
	@SerialName("species")
	val species: String? = null,
	@SerialName("created")
	val created: String? = null,
	@SerialName("origin")
	val origin: Origin? = null,
	@SerialName("name")
	val name: String? = null,
	@SerialName("location")
	val location: Location? = null,
	@SerialName("episode")
	val episode: List<String?>? = null,
	@SerialName("id")
	val id: Int? = null,
	@SerialName("type")
	val type: String? = null,
	@SerialName("url")
	val url: String? = null,
	@SerialName("status")
	val status: String? = null
) : Parcelable

