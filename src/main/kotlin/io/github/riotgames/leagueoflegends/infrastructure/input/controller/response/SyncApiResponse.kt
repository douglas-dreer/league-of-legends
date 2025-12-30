package io.github.riotgames.leagueoflegends.infrastructure.input.controller.response

data class SyncApiResponse(
    val status: SyncStatusCode,
    val code: Int,
    val message: String?,
    val quantity: Long
) {
    companion object {
        fun success(
            message: String = "Versions synchronized successfully",
            quantity: Long = 0
        ): SyncApiResponse {
            return SyncApiResponse(
                status = SyncStatusCode.SUCCESS,
                code = 200,
                message = message,
                quantity = quantity
            )
        }

        fun noContent(
            message: String = "No versions found to synchronize"
        ): SyncApiResponse {
            return SyncApiResponse(
                status = SyncStatusCode.NO_CONTENT,
                code = 204,
                message = message,
                quantity = 0
            )
        }
    }

}
