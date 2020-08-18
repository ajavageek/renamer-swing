package ch.frankel.blog.renamer

import java.awt.Color.LIGHT_GRAY
import java.awt.Color.YELLOW
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

object FileTable : JTable(FileModel) {

    private val highlightRenderer = TableCellRenderer { table: JTable, _: Any,
                                                        _: Boolean   , _: Boolean,
                                                        row: Int     , _: Int ->
        JLabel().apply {
            val actual = table.getValueAt(row, 0) as String
            text = table.getValueAt(row, 1) as String
            if (actual != text) {
                isOpaque = true
                background = YELLOW
            }
        }
    }

    init {
        setShowGrid(true)
        setGridColor(LIGHT_GRAY)
    }

    override fun getCellRenderer(row: Int, column: Int): TableCellRenderer = when (column) {
        0 -> super.getCellRenderer(row, column)
        else -> highlightRenderer
    }
}