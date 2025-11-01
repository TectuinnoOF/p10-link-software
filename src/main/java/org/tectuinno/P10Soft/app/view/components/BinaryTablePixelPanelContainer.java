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
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.tectuinno.P10Soft.app.core.FrameConverter;

/**
 * Componente visual reutilizable que representa una tabla binaria de píxeles
 * editable, utilizada como unidad gráfica dentro de cada pestaña del software
 * P10-Soft.
 * <p>
 * Cada instancia de {@code BinaryTablePixelPanelContainer} actúa como un
 * contenedor autónomo que agrupa una cuadrícula de {@link CellPixelPanel}
 * organizada en 16 filas por 32 columnas. Esta estructura permite al usuario
 * diseñar un frame o patrón gráfico que posteriormente puede convertirse
 * a formato hexadecimal y transmitirse al chip P10-Link.
 * </p>
 *
 * <h2>Características principales</h2>
 * <ul>
 *   <li>Disposición en cuadrícula ({@link java.awt.GridLayout}) de 16×32 celdas.</li>
 *   <li>Sincronización directa entre la vista ({@code cellsPixelPanels}) y el modelo lógico ({@code binaryTable}).</li>
 *   <li>Compatibilidad con operaciones de conversión y limpieza independientes por frame.</li>
 *   <li>Capacidad para integrarse dinámicamente dentro de un {@link javax.swing.JTabbedPane} en la ventana principal.</li>
 * </ul>
 *
 * <h2>Estructura interna</h2>
 * <table border="1" cellpadding="4" cellspacing="0">
 *   <tr><th>Campo</th><th>Tipo</th><th>Descripción</th></tr>
 *   <tr><td>{@code binaryTable}</td><td>{@code boolean[16][32]}</td><td>Modelo lógico que almacena el estado ON/OFF de cada píxel.</td></tr>
 *   <tr><td>{@code cellsPixelPanels}</td><td>{@code CellPixelPanel[16][32]}</td><td>Elementos visuales individuales de la cuadrícula.</td></tr>
 *   <tr><td>{@code hexFrame}</td><td>{@link String}</td><td>Trama hexadecimal resultante generada a partir de la matriz binaria.</td></tr>
 * </table>
 *
 * <h2>Uso típico</h2>
 * <pre>{@code
 * // Crear una nueva tabla de píxeles y agregarla como pestaña en la UI
 * BinaryTablePixelPanelContainer container = new BinaryTablePixelPanelContainer();
 * tabbedPane.addTab("Frame 1", container);
 * }</pre>
 *
 * @see CellPixelPanel
 * @see org.tectuinno.P10Soft.app.view.StartingWindow
 * @since 1.0
 */
public class BinaryTablePixelPanelContainer extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean[][] binaryTable;
	private CellPixelPanel[][] cellsPixelPanels;
	private String hexFrame;
	
	/**
     * Crea un nuevo contenedor de tabla binaria duplicando el diseño existente
     * a partir de otro conjunto de celdas y su matriz lógica asociada.
     * <p>
     * Este constructor permite clonar un frame ya diseñado, generando una copia
     * visual y lógica completamente independiente, preservando los estados de
     * cada píxel (encendido o apagado).
     * </p>
     *
     * @param cellPixelPanels matriz original de celdas {@link CellPixelPanel} a duplicar.
     * @param binaryTable matriz binaria correspondiente al estado lógico de las celdas.
     * @see #buildTableFromDuplicate(CellPixelPanel[][], boolean[][])
     * @since 1.0
     */
	public BinaryTablePixelPanelContainer(CellPixelPanel[][] cellPixelPanels, boolean[][] binaryTable) {
		
		this.setLayout(new GridLayout(16,32));	
		this.binaryTable = new boolean[16][32];
		this.cellsPixelPanels = new CellPixelPanel[16][32];
		buildTableFromDuplicate(cellPixelPanels,binaryTable);
		
	}
	
	/**
	 * Create the panel.
	 */
	public BinaryTablePixelPanelContainer() {
		
		this.setLayout(new GridLayout(16, 32));
		this.binaryTable = new boolean[16][32];
		this.cellsPixelPanels = new CellPixelPanel[16][32];
		this.buildTable();
		
	}
	
	
	
	/**
     * Construye dinámicamente la tabla de celdas binarias representada visualmente
     * en la interfaz de usuario, inicializando la matriz de paneles que conforman
     * la cuadrícula principal.
     * <p>
     * Este método crea una cuadrícula de 16 filas por 32 columnas, donde cada celda
     * es un componente {@link CellPixelPanel} que simula un píxel encendido o apagado.
     * Además, asigna listeners de clic a cada celda para permitir la interacción
     * directa del usuario y mantener sincronizado el modelo lógico
     * ({@code binaryTable}) con la vista ({@code panelGridLayOutTablaBinaria}).
     * </p>
     *
     * <h2>Flujo de ejecución</h2>
     * <ol>
     *   <li>Verifica que {@code panelGridLayOutTablaBinaria} no sea {@code null}.</li>
     *   <li>Itera sobre las 16 filas y 32 columnas de la cuadrícula.</li>
     *   <li>Para cada posición:
     *     <ul>
     *       <li>Se instancia un nuevo {@link CellPixelPanel} identificado como <code>panel_i_j</code>.</li>
     *       <li>Se define un borde negro mediante {@link LineBorder}.</li>
     *       <li>Se inicializa el estado del píxel como apagado (<code>false</code>).</li>
     *       <li>Se agrega un {@link MouseListener} que invoca {@link #cellClickedEvent(CellPixelPanel)} al hacer clic.</li>
     *       <li>El panel es añadido al contenedor principal {@code panelGridLayOutTablaBinaria}.</li>
     *       <li>Se actualizan las matrices lógicas {@code binaryTable} y {@code cellsPixelPanels}.</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * <h2>Estructuras actualizadas</h2>
     * <table border="1" cellpadding="4" cellspacing="0">
     *   <tr>
     *     <th>Variable</th>
     *     <th>Tipo</th>
     *     <th>Descripción</th>
     *   </tr>
     *   <tr>
     *     <td>{@code binaryTable}</td>
     *     <td>{@code boolean[16][32]}</td>
     *     <td>Almacena el estado lógico (encendido/apagado) de cada píxel de la cuadrícula.</td>
     *   </tr>
     *   <tr>
     *     <td>{@code cellsPixelPanels}</td>
     *     <td>{@code CellPixelPanel[16][32]}</td>
     *     <td>Contiene las referencias visuales de cada celda de la tabla.</td>
     *   </tr>
     * </table>
     *
     * <h2>Gestión de errores</h2>
     * <ul>
     *   <li>Si {@code panelGridLayOutTablaBinaria} es {@code null}, se lanza una excepción y se muestra un cuadro de diálogo con el mensaje de error.</li>
     *   <li>El error también se imprime en la salida estándar mediante {@code e.printStackTrace()}.</li>
     * </ul>
     *
     * <h2>Ejemplo de uso</h2>
     * <pre>{@code
     * // Inicializar la cuadrícula binaria en la ventana principal
     * buildTable();
     * }</pre>
     *
     * @throws RuntimeException si el contenedor de la tabla binaria no está inicializado.
     * @see CellPixelPanel
     * @see #cellClickedEvent(CellPixelPanel)
     * @since 1.0
     */
	private void buildTable() {

		try {						

			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 32; j++) {
					CellPixelPanel cellPanel = new CellPixelPanel("panel_" + i + "_" + j, new LineBorder(new Color(0, 0, 0)),j,i);
					cellPanel.setOn(false);
					cellPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) { 
							super.mouseClicked(e);
							cellClickedEvent(cellPanel);
						}
					});
					this.add(cellPanel);
					
					
					this.binaryTable[i][j] = cellPanel.isPixelOn() ? true : false;
					this.cellsPixelPanels[i][j] = cellPanel;
					// jpanelList.add(cellPanel);
				}
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "Ha ocurrido un error:" + e.getMessage(), "Error de UI",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}

	}
	
	/**
     * Construye una nueva cuadrícula de píxeles duplicando el contenido
     * de otra tabla existente, incluyendo su estado visual y lógico.
     * <p>
     * Crea nuevas instancias de {@link CellPixelPanel} basadas en el contenedor
     * original, copia el estado de cada píxel y asigna los listeners de clic
     * correspondientes. La estructura resultante es independiente del origen.
     * </p>
     *
     * @param copyContainer matriz original de celdas a replicar.
     * @param copyBinaryTable matriz lógica de referencia que contiene los estados binarios.
     * @throws RuntimeException si ocurre un error durante la construcción del duplicado.
     * @see CellPixelPanel
     * @since 1.0
     */
	private void buildTableFromDuplicate(CellPixelPanel[][] copyContainer, boolean[][] copyBinaryTable) {
		
		try {
			
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 32; j++) {
					CellPixelPanel cellPanel = new CellPixelPanel("panel_" + i + "_" + j, new LineBorder(new Color(0, 0, 0)),j,i);
					cellPanel.setOn(copyContainer[i][j].isPixelOn());
					cellPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) { 
							super.mouseClicked(e);
							cellClickedEvent(cellPanel);
						}
					});
					this.add(cellPanel);
					
					this.binaryTable[i][j] = copyBinaryTable[i][j];
					this.cellsPixelPanels[i][j] = cellPanel;
				}
			}
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error:" + e.getMessage(), "Error de UI",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	/**
     * Alterna el estado lógico y visual de una celda al hacer clic,
     * actualizando simultáneamente su valor correspondiente en la matriz binaria.
     *
     * @param panel celda {@link CellPixelPanel} seleccionada por el usuario.
     * @see CellPixelPanel#setOn(boolean)
     * @since 1.0
     */
	private void cellClickedEvent(CellPixelPanel panel) {
		
		panel.setOn(!panel.isPixelOn());
		
		if(panel.isPixelOn()) {		
			this.binaryTable[panel.getRow()][panel.getColumn()] = true;
			return;
		}
		
		if(!panel.isPixelOn()) {			
			this.binaryTable[panel.getRow()][panel.getColumn()] = false;
			return;
		}
	}
	
	/**
     * Convierte la tabla binaria actual en su representación hexadecimal y
     * genera la trama final de 64 bytes lista para transmisión al chip P10-Link.
     * <p>
     * El proceso incluye la impresión en consola de la matriz binaria y su
     * conversión a formato hexadecimal mediante {@link FrameConverter}.
     * El resultado se almacena en {@code hexFrame}.
     * </p>
     *
     * @throws RuntimeException si ocurre un error inesperado durante la conversión.
     * @throws Exception si {@link FrameConverter} detecta una inconsistencia
     *                   en los datos de entrada o en el formato de la matriz.
     * @see FrameConverter#convertBinaryMatrixToHex(boolean[][])
     * @see FrameConverter#getRamArrayFrame(String[][])
     * @since 1.0
     */
	public void convertCurrentFrame() throws RuntimeException, Exception {
		
		/*for(int i = 0; i < this.binaryTable.length; i++) {
			for(int j = 0; j < this.binaryTable[i].length ; j++) {
				System.out.print(this.binaryTable[i][j]? 1 + " " : 0 + " ");
			}
			System.out.println();
		}*/
		
		String[][] hexMatrix = FrameConverter.convertBinaryMatrixToHex(binaryTable);
		for(int i = 0; i< hexMatrix.length; i++) {
			for(int j = 0; j < hexMatrix[i].length; j++) {
				System.out.print(" " + hexMatrix[i][j]);
			}
			System.out.println();
		}
		
		this.hexFrame = FrameConverter.getRamArrayFrame(hexMatrix);
		
	}
		
	
	/**
     * Limpia completamente la tabla de píxeles actual, apagando todas las celdas
     * visuales y restableciendo la matriz binaria a {@code false}.
     * <p>
     * Recorre toda la cuadrícula de {@link CellPixelPanel} asignando cada píxel
     * a estado apagado, manteniendo sincronizado el modelo lógico
     * ({@code binaryTable}) con la vista.
     * </p>
     *
     * @see CellPixelPanel#setOn(boolean)
     * @since 1.0
     */
	public void clearCurrentPixelTable() {
		for(int i = 0; i < this.cellsPixelPanels.length; i++) {
			for(int j = 0; j < this.cellsPixelPanels[i].length; j++) {
				this.cellsPixelPanels[i][j].setOn(false);
				this.binaryTable[i][j] = false;
			}
		}
	}
	
	public boolean[][] getBinaryTable(){
				
		return this.binaryTable;
	}
	
	public String getHexFrame() {
		return this.hexFrame;
	}
	
	public CellPixelPanel[][] getCellPixelPanels(){
		return this.cellsPixelPanels;
	}
}
