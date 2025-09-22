/*
 * This file is part of P10-Soft.
 *
 * P10-Soft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful
 * as a companion tool for the Tectuinno P10-Link chip, enabling users
 * to design, visualize and transmit frames to P10 LED panels in real time.
 * However, WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * As a special exception, you may use this file as part of a free software
 * library without restriction. Specifically, if other files instantiate
 * templates or use macros or inline functions from this file, or you compile
 * this file and link it with other files to produce an executable, this
 * file does not by itself cause the resulting executable to be covered by
 * the GNU General Public License. This exception does not however
 * invalidate any other reasons why the executable file might be covered by
 * the GNU General Public License.
 *
 * Copyright 2025 Tectuinno Team (https://github.com/tectuinno)
 */

package org.tectuinno.P10Soft.app.view.components;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.Border;

public class CellPixelPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7074079333681788134L;
	/**
	 * 
	 * 
	 */
	private boolean isOn;
	private int column;
	private int row;
	
	/**
	 * 
	 * @param name
	 * @param background
	 * @param border
	 * @param column
	 * @param row
	 */
	public CellPixelPanel(String name, Color background, Border border, int column, int row) {
		super.setName(name);
		super.setBackground(background);
		super.setBorder(border);
		this.column = column;
		this.row = row;
	}
	
	public CellPixelPanel() {
		super();
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public boolean isPixelOn() {
		return this.isOn;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
