package org.tectuinno.P10Soft.app.view.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BinaryTablePixelPanelContainer extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean[][] binaryTable;
	private CellPixelPanel[][] cellsPixelPanels;

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
}
