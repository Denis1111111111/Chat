package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class TableRooms extends JPanel
{
	public  JTable tb;
	Listen listen = null;
	Client client = null;
	
	public TableRooms(Client client)
	{
		setLayout(null);
		this.client = client;
		listen = new Listen();
		tb = new JTable(listen);
		tb.setTableHeader(null);
		tb.setShowGrid(false);
		tb.setShowVerticalLines(false);
		tb.setShowHorizontalLines(false);
		tb.setDefaultRenderer(Object.class, new Rendr());
		tb.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getClickCount() == 1) 
				{
					int row = tb.rowAtPoint(e.getPoint()); 
					if (row > -1) 
					{
					
							client.frameClient.room.setText(client.rooms.get(row));
							client.colorMap.put(row, Color.WHITE);
							client.frameClient.table.listen.fireTableDataChanged();
							client.room = client.rooms.get(row);
							client.frameClient.jtaTextAreaMessage.setText("");		
						}
						client.func.getMessages(client.rooms.get(row));
						client.func.newRoom();
				}
			}
		});
	    
	    
	    
		
		JScrollPane sp = new JScrollPane(tb);
		listen.addTableModelListener(tb);
		sp.setBounds(0, 0, 280, 450);      
		add(sp);
	}
	
	public class Listen  extends DefaultTableModel
	{	
		@Override
		public int getColumnCount() 
		{
			return 1;
		}
		
		@Override
		public int getRowCount() 
		{
			return client.rooms.size();
		}

		@Override
		public Object getValueAt(int row, int col) 
		{
			return client.rooms.get(row);
		}
	}
	
	class Rendr extends DefaultTableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
				int column)
		{
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);	
			
			c.setBackground(client.colorMap.get(row));
			return c;
		}
	}
}