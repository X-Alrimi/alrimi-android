package com.example.capstone2.core.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DefaultResponse(@JsonProperty("message") val message: String?)