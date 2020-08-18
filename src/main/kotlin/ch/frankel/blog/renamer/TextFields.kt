package ch.frankel.blog.renamer

import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Document
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class FiringTextField<T>(private val eventBus: EventBus,
                                  private val create: (String) -> T) : JTextField() {

    private val Document.text: String
        get() = getText(0, length)

    init {
        document.addDocumentListener(object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent) = postChange(e)
            override fun insertUpdate(e: DocumentEvent) = postChange(e)
            override fun removeUpdate(e: DocumentEvent) = postChange(e)
        })
    }

    private fun postChange(e: DocumentEvent) =
        eventBus.post(create(e.document.text))
}

object DirectoryTextField : FiringTextField<DirectoryPathUpdatedEvent>(
    EventBus.getDefault(),
    { DirectoryPathUpdatedEvent(it) }
) {

    init {
        text = System.getProperty("user.home")
    }

    @Subscribe
    @Suppress("UNUSED")
    fun onDirectoryChosen(event: DirectoryChosenEvent) {
        text = event.path
    }
}

object ReplacementTextField : FiringTextField<ReplacementUpdatedEvent>(
    EventBus.getDefault(),
    { ReplacementUpdatedEvent(it) }
)

object PatternTextField : FiringTextField<PatternUpdatedEvent>(
    EventBus.getDefault(),
    {PatternUpdatedEvent(it) }
)