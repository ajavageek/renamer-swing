package ch.frankel.blog.renamer

data class DirectoryChosenEvent(val path: String)
data class DirectoryPathUpdatedEvent(val path: String)
data class PathModelUpdatedEvent(val path: String)
data class PatternUpdatedEvent(val pattern: String)
data class ReplacementUpdatedEvent(val replacement: String)
class RenamedEvent