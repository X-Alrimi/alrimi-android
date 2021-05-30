package com.example.capstone2.core.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Stock (
        @JsonProperty("id") var id: Long,
        @JsonProperty("name") var name: String
        )