package io.github.riotgames.leagueoflegends.infrastructure.input.controller.response

class Pagination<T> {
    val content: List<T>
    val page: Int
    val pageSize: Int
    val totalElements: Int
    val totalPages: Int

    constructor(content: List<T>, page: Int, pageSize: Int, totalElements: Int) {
        this.content = content
        this.page = page
        this.pageSize = pageSize
        this.totalElements = totalElements
        this.totalPages = if (totalElements % pageSize == 0) {
            totalElements / pageSize
        } else {
            totalElements / pageSize + 1
        }
    }

    /**
     * Companion object to provide a factory method for creating Pagination instances.
     */
    fun toPagination(content: List<T>, page: Int = 0, pageSize: Int, totalElements: Int): Pagination<T> {
        return Pagination(content, page, pageSize, totalElements)
    }
}