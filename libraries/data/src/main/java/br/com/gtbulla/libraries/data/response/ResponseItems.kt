package br.com.gtbulla.libraries.data.response

import com.squareup.moshi.Json

data class ResponseItems<T>(
    @field:Json(name = "total_count") val totalCount: Int,
    @field:Json(name = "items") val items: List<T>
)