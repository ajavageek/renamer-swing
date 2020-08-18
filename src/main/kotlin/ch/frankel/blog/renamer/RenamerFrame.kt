package ch.frankel.blog.renamer

import javax.swing.JFrame

class RenamerFrame : JFrame("File Renamer") {

    init {
        contentPane = RenamerPanel()
        pack()
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }
}