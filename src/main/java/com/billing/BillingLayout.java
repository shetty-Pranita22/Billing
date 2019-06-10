package com.billing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.billing.exception.NotInStockException;
import com.billing.model.Item;
import com.billing.service.BillingTerminal;

import net.miginfocom.swing.MigLayout;

@SpringBootApplication
public class BillingLayout implements ActionListener {

	@Autowired
	BillingTerminal billTerminal;
	
	
	JFrame f = new JFrame("Home");
	JFrame errorFrame;
	JPanel p = new JPanel();
	JPanel addPanel = new JPanel();
	JPanel editPanel = new JPanel();
	JPanel tablePanel = new JPanel();
	JButton btnDelete = new JButton("Delete");

	String column[] = { "ID", "NAME", "QTY", "PRICE" };
	DefaultTableModel model = new DefaultTableModel(column, 0);
	public boolean flag = false;
	JTable jt = new JTable(model) {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return flag;
		};
	};

	JLabel cart = new JLabel("Cart");
	JButton btnAdd = new JButton("ADD");
	JButton btnEdit = new JButton("EDIT");
	JButton btnDel = new JButton("DELETE");
	JButton btnCheckout = new JButton("CHECKOUT");
	JScrollPane sp = new JScrollPane(jt);
	JLabel l = new JLabel("Cart total");
	JTextField cartTotal = new JTextField(12);

	BigDecimal calculateTotal() {
		BigDecimal total = new BigDecimal(0.0);
		for (int i = 0; i < jt.getRowCount(); i++) {

			BigDecimal Amount = (BigDecimal) jt.getValueAt(i, 3);
			total = Amount.add(total);
		}
		return total;
	}

	public void billingHome() {

		cart.setFont(new Font("default", Font.BOLD, 16));
		l.setForeground(Color.blue);
		p.setLayout(new MigLayout());
		tablePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

		p.add(cart, "wrap");
		p.add(btnAdd, "split 3,align left");
		p.add(btnEdit);
		p.add(btnDel, "wrap");
		tablePanel.add(sp, "split 2");
		tablePanel.add(addPanel);
		tablePanel.add(editPanel);
		p.add(tablePanel, "wrap");
		p.add(l, "split 3,align left");
		p.add(cartTotal);
		p.add(btnCheckout);
		f.add(p);
		f.pack();
		f.setVisible(true);

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editRow();
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addRow();
			}
		});

		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteRow();
			}
		});

		btnCheckout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkoutFunction();
			}
		});
	}

	public void checkoutFunction() {
		List<Item> cartData = new ArrayList<>();
		for (int count = 0; count < model.getRowCount(); count++) {
			cartData.add(new Item(model.getValueAt(count, 1).toString(),
					Integer.parseInt(model.getValueAt(count, 2).toString()), new BigDecimal(0.0), new BigDecimal(0.0)));
		}
		billTerminal.editQuantity(cartData);
		model.setRowCount(0);
		calculateCartTotal();
	}

	public void addRow() {
		JLabel itemName = new JLabel("Enter the product code:");
		final JComboBox<String> enterItemName = new JComboBox<String>();
		JLabel itemQty = new JLabel("Enter product quantity:");
		final JTextField enterItemQty = new JTextField(12);
		JButton b = new JButton("Submit");
		JButton exitBtn = new JButton("Exit");

		enterItemName.addItem("--Choose one--");
		ArrayList<Item> pc = (ArrayList<Item>) billTerminal.getItemCodes();
		for (Item s : pc) {
			enterItemName.addItem(s.getItemName());
		}

		exitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addPanel.removeAll();
				addPanel.setVisible(false);
			}
		});
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int pQty = Integer.parseInt(enterItemQty.getText());
				String pName = enterItemName.getItemAt(enterItemName.getSelectedIndex());
				try {
					if (billTerminal.checkAvailability(pName, pQty)) {
						BigDecimal price = billTerminal.calculatePrice(pQty, pName);
						model.addRow(new Object[] { jt.getRowCount() + 1, pName, pQty, price });
						cartTotal.setText("$" + calculateTotal());
					}
				} catch (NotInStockException e1) {
					// TODO Auto-generated catch block
					errorFrame = new JFrame();
					JOptionPane.showMessageDialog(errorFrame, "Item " + pName + " in stock: "+e1.getMessage(), "Alert",
							JOptionPane.WARNING_MESSAGE);
				}

				enterItemQty.setText("");
				enterItemName.setSelectedIndex(0);
			}
		});
		addPanel.setLayout(new MigLayout());
		addPanel.add(itemName);
		addPanel.add(enterItemName, "wrap,growx,pushx");
		addPanel.add(itemQty);
		addPanel.add(enterItemQty, "wrap,growx,pushx");
		addPanel.add(new JLabel("Click Submit to add item"));
		addPanel.add(b);
		addPanel.add(exitBtn);
		addPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		addPanel.setSize(300, 300);
		addPanel.setVisible(true);

	}

	public void editRow() {
		JLabel itemName = new JLabel("Edit the product:");
		final JTextField editItemName = new JTextField();
		JLabel itemQty = new JLabel("Edit product quantity:");
		final JTextField editItemQty = new JTextField(12);
		JButton b = new JButton("Submit");

		editItemName.setEditable(false);
		editItemName.setText((String) jt.getValueAt(jt.getSelectedRow(), 1));
		editItemQty.setText("" + jt.getValueAt(jt.getSelectedRow(), 2));

		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int pQty = Integer.parseInt(editItemQty.getText());
				String pName = editItemName.getText();
				BigDecimal price = billTerminal.calculatePrice(pQty, pName);
				jt.setValueAt((Object) pQty, jt.getSelectedRow(), 2);
				jt.setValueAt(price, jt.getSelectedRow(), 3);
				cartTotal.setText("$" + calculateTotal());
				editPanel.setVisible(false);
				editPanel.removeAll();
			}
		});

		editPanel.setLayout(new MigLayout());
		editPanel.add(itemName);
		editPanel.add(editItemName, "wrap,pushx,growx");
		editPanel.add(itemQty);
		editPanel.add(editItemQty, "wrap,pushx,growx");
		editPanel.add(new JLabel("Click Submit to edit item"));
		editPanel.add(b, "wrap");
		editPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		editPanel.setSize(300, 300);
		editPanel.setVisible(true);

	}

	public void deleteRow() {
		if (jt.getSelectedRow() != -1) {
			model.removeRow(jt.getSelectedRow());
		}
		for (int i = 0; i < jt.getRowCount(); i++) {
			jt.setValueAt(i + 1, i, 0);
		}
		calculateCartTotal();
	}

	public void calculateCartTotal() {	
		cartTotal.setText("$" + calculateTotal());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(BillingLayout.class).headless(false)
				.run(args);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BillingLayout billLayout = ctx.getBean(BillingLayout.class);
				billLayout.billingHome();
			}
		});

	}

}
