package ch.frankel.blog.renamer

import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.nio.file.Files
import java.util.regex.PatternSyntaxException
import javax.swing.JButton
import javax.swing.SwingUtilities

object RenamerButton : JButton("Apply") {

    private var root: File? = null
    private var regex = "".toRegex()
    private var replacement = ""

    init {
        addActionListener {
            if (root != null && regex.pattern.isNotEmpty()) {
                SwingUtilities.invokeLater {
                    root.children()
                        ?.forEach {
                            val source = it.toPath()
                            val target = source.resolveSibling(regex.replace(it.name, replacement))
                            Files.move(source, target)
                        }
                    eventBus.post(RenamedEvent())
                }
            }
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onPathModelUpdated(event: PathModelUpdatedEvent) {
        root = File(event.path)
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onPatternUpdated(event: PatternUpdatedEvent) {
        try {
            regex = event.pattern.toRegex()
        } catch (e: PatternSyntaxException) {
            // NOTHING TO DO
        }
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onReplacementUpdated(event: ReplacementUpdatedEvent) {
        replacement = event.replacement
    }
}