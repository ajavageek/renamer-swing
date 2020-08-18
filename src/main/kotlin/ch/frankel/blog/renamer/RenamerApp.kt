package ch.frankel.blog.renamer

import org.greenrobot.eventbus.EventBus
import java.io.File

fun main() {
    fun EventBus.register(vararg subscribers: Any) {
        subscribers.forEach { register(it) }
    }
    eventBus.register(
        DirectoryTextField,
        FolderPickerButton,
        FileModel,
        PathModel,
        RenamerButton
    )
    eventBus.post(PathModelUpdatedEvent(System.getProperty("user.home")))
    RenamerFrame().isVisible = true
}

internal val eventBus: EventBus = EventBus.getDefault()

internal fun File?.children() = this?.listFiles()
    ?.filter { !it.isHidden && it.isFile }
    ?.toList()