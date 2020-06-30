package com.thecat.app.data.network

import com.thecat.app.data.model.Cat
import com.thecat.app.data.network.response.CatResponse

fun catResponseConverter(catResponse: CatResponse): Cat {
    var name: String? = "Cat name"
    var desc: String? = null
    var country: String? = "United Kingdom"
    var age: String = "11 - 14 months"
    catResponse.breeds?.let {
        if (it.isNotEmpty()) {
            it[0].let { breedResponse ->
                name = breedResponse.name
                desc = breedResponse.desc
                country = breedResponse.country
                if (breedResponse.age != null) age = "${breedResponse.age} months"
            }
        }
    }
    return Cat(
        id = catResponse.id!!,
        name = name,
        imageUrl = catResponse.imageUrl,
        isFav = false,
        desc = desc,
        country = country,
        age = age
    )
}

fun catResponseConverter(catResponses: List<CatResponse>): List<Cat> {
    val cats = mutableListOf<Cat>()
    for (catResponse in catResponses) {
        cats.add(catResponseConverter(catResponse))
    }
    return cats
}
