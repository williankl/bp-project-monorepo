package williankl.bpProject.common.platform.design.components.str

internal data class DesignComponentsStrings(
    val bubbleStrings: CommentBubbleStrings,
) {
    internal data class CommentBubbleStrings(
        val timePassed: (timeInMillis: Long) -> String
    )
}
