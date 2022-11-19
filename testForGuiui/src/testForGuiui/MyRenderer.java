package testForGuiui;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class MyRenderer extends DefaultTableCellRenderer 
{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
		String target_comment_idx = (String) table.getValueAt(row, 0);
		Color g = new Color(240,240,240);

		try(Connection con = JDBC.connection()) {
			String s_ = "select * from comment where comment_id = \'" + target_comment_idx + "\' and parent_id is not null";
			Statement stmt_ = con.createStatement();
			ResultSet rs_ = stmt_.executeQuery(s_);

			if(rs_.next()) { // 특정한 값을 가진 셀을 찾아서 그 셀만 배경색상을 변경한다
				c.setBackground(g);
			} else{
				c.setBackground(Color.white);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}