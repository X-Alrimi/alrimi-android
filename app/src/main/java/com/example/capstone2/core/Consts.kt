package com.example.capstone2.core

// 인텐트 키 상수 선언
class Consts {
    companion object{
        // intent key
        const val STOCK_ID = "id"
        const val STOCK_NAME = "name"
        const val STOCK_GRAPH = "graph"
        const val NEWS_LINK = "link"

        // SharedPreference
        const val SF_FILE_NAME = "prefs"
        const val SF_TOKEN = "token"

        // base url
        const val BASE_URL = "http://ec2-18-218-1-49.us-east-2.compute.amazonaws.com:8080/"

        // stock graph link
        private const val YG_GRAPH = "https://finance.naver.com/item/main.nhn?code=122870"
        private const val NO_GRAPH = "no stock graph"

        fun getGraphLink(stockId: Long): String {
            return when(stockId) {
                1L -> YG_GRAPH
                else -> NO_GRAPH
            }
        }
    }
}