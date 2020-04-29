/*
GNU Lesser General Public License

SimpleInfoDialog
Copyright (C) 2000 Howard Kistler

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.hexidec.ekit.component;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.hexidec.util.Translatrix;
import java.awt.Component;
import java.awt.Dialog;

/**
 * Class for providing a dialog that lets the user specify values for tag
 * attributes.
 */
public class SimpleInfoDialog extends JDialog {

	public static final int ERROR = JOptionPane.ERROR_MESSAGE;
	public static final int INFO = JOptionPane.INFORMATION_MESSAGE;
	public static final int WARNING = JOptionPane.WARNING_MESSAGE;
	public static final int QUESTION = JOptionPane.QUESTION_MESSAGE;
	public static final int PLAIN = JOptionPane.PLAIN_MESSAGE;

	private JOptionPane jOptionPane;
	private Object[] buttonLabels;
	private Integer buttonState = new Integer(JOptionPane.CLOSED_OPTION);

	public SimpleInfoDialog(Frame parent, String title, boolean bModal, String message, int type) {
		super(parent, title, bModal);
		init(type, message);
	}

	
	public SimpleInfoDialog(Dialog parent, String title, boolean bModal, String message, int type) {
		super(parent, title, bModal);
		init(type, message);
	}
	
	public SimpleInfoDialog(String title, boolean bModal, String message, int type) {
		super();
		setTitle(title);
		setModal(bModal);
		init(type, message);
	}
	
	public static SimpleInfoDialog newSimpleInfoDialog(Component parent, String title, boolean bModal, String message, int type) {
		if (parent instanceof Frame) {
			return new SimpleInfoDialog((Frame) parent, title, bModal, message, type);
			
		} else if (parent instanceof Dialog) {
			return new SimpleInfoDialog((Dialog) parent, title, bModal, message, type);
						
		} else {
			return new SimpleInfoDialog(title, bModal, message, type);
		}
	}
	
	private void init(int type, String message) {
		if (type == QUESTION) {
			buttonLabels = new Object[]{Translatrix.getTranslationString("DialogAccept"), Translatrix.getTranslationString("DialogCancel")};
			jOptionPane = new JOptionPane(new JLabel(message, SwingConstants.CENTER), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, buttonLabels, buttonLabels[0]);
		} else {
			buttonLabels = new Object[]{Translatrix.getTranslationString("DialogClose")};
			jOptionPane = new JOptionPane(new JLabel(message, SwingConstants.CENTER), JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, buttonLabels, buttonLabels[0]);
		}

		setContentPane(jOptionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		jOptionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if (isVisible() && (e.getSource() == jOptionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY) || prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
					setVisible(false);
				}
			}
		});

		this.pack();
		this.setLocationRelativeTo(getParent());
		this.setVisible(true);
	}
	public String getDecisionValue() {
		return jOptionPane.getValue().toString();
	}
}
