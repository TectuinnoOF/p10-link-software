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

/**
 * Representa una celda interactiva dentro de la cuadrícula binaria principal de la interfaz.
 * <p>
 * La clase {@code CellPixelPanel} extiende {@link JPanel} y actúa como un componente
 * visual individual (un "píxel") capaz de cambiar su estado entre encendido y apagado.
 * Cada instancia mantiene un identificador único, coordenadas dentro de la cuadrícula,
 * un borde personalizado, y un color de fondo dinámico que refleja su estado lógico.
 * </p>
 *
 * <h2>Propósito</h2>
 * <ul>
 *   <li>Simular un píxel binario dentro de una matriz de 16×32 posiciones.</li>
 *   <li>Permitir la interacción directa con el usuario mediante clics.</li>
 *   <li>Actualizar la representación visual y lógica del sistema de dibujo binario.</li>
 * </ul>
 *
 * <h2>Comportamiento visual</h2>
 * <ul>
 *   <li>Cuando el píxel está encendido, su fondo se pinta de un color activo (por defecto, negro o configurado).</li>
 *   <li>Cuando está apagado, el fondo se muestra con el color base o transparente.</li>
 *   <li>El borde exterior se define mediante {@link LineBorder} para una separación visual clara.</li>
 * </ul>
 *
 * <h2>Interacción y eventos</h2>
 * <p>
 * Cada celda puede escuchar eventos de ratón mediante {@link MouseListener}.
 * En la ventana principal ({@link StartingWindow}), el método
 * {@link StartingWindow#cellClickedEvent(CellPixelPanel)} se invoca al detectar un clic,
 * alternando su estado binario y actualizando la tabla correspondiente.
 * </p>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * CellPixelPanel pixel = new CellPixelPanel("panel_3_5", new LineBorder(Color.BLACK), 5, 3);
 * pixel.setOn(true);   // Encender el píxel
 * parentPanel.add(pixel);
 * }</pre>
 *
 * <h2>Consideraciones</h2>
 * <ul>
 *   <li>Debe integrarse dentro de un contenedor con {@code GridLayout(16, 32)} para alinear correctamente la cuadrícula.</li>
 *   <li>No debe manejar directamente la lógica de red o conversión binaria; su responsabilidad es puramente visual y de interacción local.</li>
 *   <li>El color de encendido/apagado puede personalizarse mediante métodos setter si se extiende esta clase.</li>
 * </ul>
 *
 * @author Pablo Gomez Perez
 * @version 1.0
 * @since 2025-10
 * @see JPanel
 * @see StartingWindow
 * @see java.awt.event.MouseListener
 */
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
	public CellPixelPanel(String name, Border border, int column, int row) {
		super.setName(name);		
		super.setBorder(border);
		this.column = column;
		this.row = row;
	}
	
	public CellPixelPanel() {
		super();
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
		this.setBackgroundColorEvent();
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
	
	private void setBackgroundColorEvent() {
		
		if(this.isOn) {
			this.setBackground(new Color(102,204,0));
			return;
		}
		
		if(!this.isOn) {
			this.setBackground(new Color(255,255,153));
		}
		
	}
	
}
