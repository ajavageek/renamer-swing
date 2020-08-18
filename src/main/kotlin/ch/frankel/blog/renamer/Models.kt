package ch.frankel.blog.renamer

import java.io.File
import java.util.regex.PatternSyntaxException
import javax.swing.table.AbstractTableModel
import org.greenrobot.eventbus.Subscribe

object PathModel {

    private var path: String = System.getProperty("user.home")

    @Subscribe
    @Suppress("UNUSED")
    fun onDirectoryPathUpdated(event: DirectoryPathUpdatedEvent) {
        if (path != event.path) {
            eventBus.post(PathModelUpdatedEvent(event.path))
            path = event.path
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onDirectoryChosen(event: DirectoryChosenEvent) {
        if (path != event.path) {
            eventBus.post(PathModelUpdatedEvent(event.path))
            path = event.path
        }
    }
}

object FileModel : AbstractTableModel() {

    private var root: File? = null
    private var regex = "".toRegex()
    private var replacement = ""
    private var files = listOf<File>()

    override fun getRowCount() = files.size
    override fun getColumnCount() = 2

    override fun getColumnName(column: Int) = when (column) {
        0 -> "Name"
        else -> "Candidate name"
    }

    override fun getValueAt(row: Int, column: Int): String = when (column) {
        0 -> files[row].name
        else -> {
            val name = files[row].name
            if (root != null && regex.pattern.isNotEmpty())
                regex.replace(name, replacement)
            else name
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onPathModelUpdated(event: PathModelUpdatedEvent) {
        root = File(event.path)
        refresh()
        fireTableDataChanged()
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onPatternUpdated(event: PatternUpdatedEvent) {
        try {
            regex = event.pattern.toRegex()
            fireTableDataChanged()
        } catch (e: PatternSyntaxException) {
            // NOTHING TO DO
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onReplacementUpdated(event: ReplacementUpdatedEvent) {
        replacement = event.replacement
        fireTableDataChanged()
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onRenamed(@Suppress("UNUSED_PARAMETER") event: RenamedEvent) {
        refresh()
        fireTableDataChanged()
    }

    private fun refresh() {
        files = root
            .children()
            ?.sortedBy { it.name }
            ?: emptyList()
    }
}