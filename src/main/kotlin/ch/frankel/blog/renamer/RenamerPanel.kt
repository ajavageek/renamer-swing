package ch.frankel.blog.renamer

import java.awt.*
import java.awt.GridBagConstraints.*
import javax.swing.*

class RenamerPanel : JPanel() {

    private val space = 4

    init {
        layout = GridBagLayout()
        add(
            JLabel("Folder:") to constraints(insets = Insets(space, space, space, 0)),
            DirectoryTextField to constraints(gridx = 1, fill = HORIZONTAL, weightx = 1.0, gridwidth = 2),
            FolderPickerButton to constraints(gridx = 3, anchor = LINE_END, insets = Insets(space, 0, space, 0)),
            JLabel("Pattern:") to constraints(gridy = 1, insets = Insets(space, space, space, 0)),
            PatternTextField to constraints(gridx = 1, gridy = 1, fill = HORIZONTAL, weightx = 0.5),
            JLabel("Replacement:") to constraints(gridx = 2, gridy = 1, insets = Insets(space, space, space, 0)),
            ReplacementTextField to constraints(gridx = 3, gridy = 1, fill = HORIZONTAL, weightx = 0.5, insets = Insets(space, 0, space, space)),
            JScrollPane(FileTable) to constraints(gridy = 2, fill = BOTH, weightx = 1.0, weighty = 1.0, gridwidth = 4, insets = Insets(space, space, space, space)),
            RenamerButton to constraints(gridx = 3, gridy = 3, anchor = LINE_END),
        )
    }
}

private fun JPanel.add(vararg components: Pair<JComponent, GridBagConstraints>) {
    components.forEach {
        add(it.first, it.second)
    }
}

private fun constraints(
    gridx: Int = 0, gridy: Int = 0,
    gridwidth: Int = 1, gridheight: Int = 1,
    weightx: Double = 0.0, weighty: Double = 0.0,
    anchor: Int = CENTER, fill: Int = NONE,
    insets: Insets = Insets(0, 0, 0, 0),
    ipadx: Int = 0, ipady: Int = 0
) = GridBagConstraints().apply {
    this.gridx = gridx
    this.gridy = gridy
    this.gridwidth = gridwidth
    this.gridheight = gridheight
    this.weightx = weightx
    this.weighty = weighty
    this.anchor = anchor
    this.fill = fill
    this.insets = insets
    this.ipadx = ipadx
    this.ipady = ipady
}