package ch.frankel.blog.renamer

import java.io.File
import javax.swing.JButton
import javax.swing.JFileChooser
import org.greenrobot.eventbus.Subscribe

object FolderPickerButton : JButton("Browse...") {

    private val fileChooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    }

    init {
        addActionListener {
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                eventBus.post(DirectoryChosenEvent(fileChooser.selectedFile.path))
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onPathModelUpdated(event: PathModelUpdatedEvent) {
        fileChooser.currentDirectory = File(event.path)
    }
}