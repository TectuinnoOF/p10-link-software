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

package org.tectuinno.P10Soft.app.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.tectuinno.P10Soft.app.core.FrameConverter;
import org.tectuinno.P10Soft.app.io.SerialTransmitter;
import org.tectuinno.P10Soft.app.view.components.CellPixelPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Ventana principal del sistema que actúa como punto de entrada visual para el usuario.
 * <p>
 * La clase {@code StartingWindow} extiende {@link JFrame} y representa la
 * ventana principal de la aplicación. Su propósito es inicializar y mostrar
 * la interfaz base desde la cual el usuario puede acceder al resto de las
 * funcionalidades del sistema.
 * </p>
 *
 * <h2>Características principales</h2>
 * <ul>
 *   <li>Inicializa los componentes gráficos (botones, menús, paneles, etc.).</li>
 *   <li>Establece las propiedades visuales de la ventana (tamaño, título, íconos, comportamiento de cierre, etc.).</li>
 *   <li>Actúa como contenedor principal de otras vistas o paneles secundarios.</li>
 *   <li>Puede incluir listeners para manejar eventos de interacción del usuario.</li>
 * </ul>
 *
 *
 * <h2>Ciclo de vida</h2>
 * <table border="1">
 *   <tr>
 *     <th>Etapa</th>
 *     <th>Descripción</th>
 *   </tr>
 *   <tr>
 *     <td>Construcción</td>
 *     <td>Se instancian los componentes de la interfaz y se definen las propiedades básicas.</td>
 *   </tr>
 *   <tr>
 *     <td>Inicialización</td>
 *     <td>Se configuran los listeners y la lógica de los botones o menús.</td>
 *   </tr>
 *   <tr>
 *     <td>Ejecución</td>
 *     <td>La ventana se hace visible al usuario y queda en espera de eventos.</td>
 *   </tr>
 * </table>
 *
 * <h2>Notas de implementación</h2>
 * <ul>
 *   <li>Se recomienda mantener la lógica de negocio fuera de esta clase, limitándola a control visual.</li>
 *   <li>Usar el patrón MVC para desacoplar vista, modelo y controlador.</li>
 *   <li>Si se requiere cambiar el contenido dinámicamente, usar un {@link javax.swing.JPanel} central con {@link java.awt.CardLayout}.</li>
 * </ul>
 *
 * @author Pablo Gomez Perez
 * @version 1.0
 * @since 2025-10
 */
public class StartingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu jMenuArchivo = new JMenu("Archivo");
	private final JMenuItem jMenuItemAbrir = new JMenuItem("Cargar diseño");
	private final JMenuItem jMenuItemNuevo = new JMenuItem("Nuevo diseño");
	private final JMenuItem jMenuItemGuardar = new JMenuItem("Guardar");
	private final JMenu jMenuHerramientas = new JMenu("Herramientas");
	private final JMenuItem jMenuItemConvertir = new JMenuItem("Convertir");
	private final JMenuItem jMenuItemEnviar = new JMenuItem("Enviar");
	private final JSeparator separator = new JSeparator();
	private final JMenuItem jMenuItemLimpiar = new JMenuItem("Limpiar tabla");
	private final JPanel jPanelSuperiorBotones = new JPanel();
	private final JButton btnConvertir = new JButton("Convertir");
	private final JButton btnEnviar = new JButton("Enviar");
	private final JMenuItem jMenuItemEscanear = new JMenuItem("Escanear COM");
	private final JComboBox<String> cmbDispositivosCOMDisponibles = new JComboBox<String>();
	private final JButton btnEscanear = new JButton("Escanear dispositivos");
	private final JSplitPane splitPaneTablaContenedor = new JSplitPane();
	private final JPanel panelContenedorTablaBinaria = new JPanel();
	private final JPanel panelGridLayOutTablaBinaria = new JPanel();
	private boolean[][] binaryTable = new boolean[16][32];
	private CellPixelPanel[][] cellsPixelPanels = new CellPixelPanel[16][32];
	private String hexFrame;
	private List<String> aviablePortsName;
	private final JPanel panelResultConsole = new JPanel();
	private final JScrollPane scrollPaneConsoleContainer = new JScrollPane();
	private final JTextArea textAreaResultConsole = new JTextArea();

	/**
	 * Create the frame.
	 */
	public StartingWindow() {
		setTitle("P10 Link Software");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setTableDividerLocationEvent();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		{
			setJMenuBar(menuBar);
		}
		{
			menuBar.add(jMenuArchivo);
		}
		{
			jMenuArchivo.add(jMenuItemAbrir);
		}
		{
			jMenuArchivo.add(jMenuItemNuevo);
		}
		{
			jMenuArchivo.add(jMenuItemGuardar);
		}
		{
			menuBar.add(jMenuHerramientas);
		}
		{
			jMenuHerramientas.add(jMenuItemConvertir);
		}
		{
			jMenuHerramientas.add(jMenuItemEnviar);
		}
		{
			jMenuHerramientas.add(jMenuItemEscanear);
		}
		{
			jMenuHerramientas.add(separator);
		}
		{
			jMenuItemLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearAllPixels();
				}
			});
			jMenuHerramientas.add(jMenuItemLimpiar);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			FlowLayout flowLayout = (FlowLayout) jPanelSuperiorBotones.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);

			contentPane.add(jPanelSuperiorBotones, BorderLayout.NORTH);
		}
		{
			btnConvertir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					convertFrame();
				}
			});
			jPanelSuperiorBotones.add(btnConvertir);
		}
		{
			btnEnviar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sendData();
				}
			});
			jPanelSuperiorBotones.add(btnEnviar);
		}
		{
			cmbDispositivosCOMDisponibles.setPreferredSize(new Dimension(190, 22));
			jPanelSuperiorBotones.add(cmbDispositivosCOMDisponibles);
		}
		{
			btnEscanear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchForComDevices();
				}
			});
			jPanelSuperiorBotones.add(btnEscanear);
		}
		{
			splitPaneTablaContenedor.setOrientation(JSplitPane.VERTICAL_SPLIT);
			this.splitPaneTablaContenedor.setDividerLocation(410);
			contentPane.add(splitPaneTablaContenedor, BorderLayout.CENTER);
		}
		{
			splitPaneTablaContenedor.setLeftComponent(panelContenedorTablaBinaria);
		}
		panelContenedorTablaBinaria.setLayout(new BorderLayout(0, 0));
		{
			panelContenedorTablaBinaria.add(panelGridLayOutTablaBinaria, BorderLayout.CENTER);
		}
		panelGridLayOutTablaBinaria.setLayout(new GridLayout(16, 32, 0, 0));
		{
			splitPaneTablaContenedor.setRightComponent(panelResultConsole);
		}
		panelResultConsole.setLayout(new BorderLayout(0, 0));
		{
			panelResultConsole.add(scrollPaneConsoleContainer);
		}
		{
			textAreaResultConsole.setForeground(new Color(0, 255, 0));
			textAreaResultConsole.setBackground(new Color(0, 0, 0));
			scrollPaneConsoleContainer.setViewportView(textAreaResultConsole);
			this.setConsoleInitialText();
		}

		this.buildTable();
		this.searchForComDevices();

	}
	
	/**
     * Ajusta dinámicamente la posición del divisor del componente {@link JSplitPane}
     * que contiene la tabla principal dentro de la ventana.
     * <p>
     * Este método reposiciona el divisor del panel dividido
     * ({@code splitPaneTablaContenedor}) según la altura actual de la ventana,
     * garantizando que el área superior conserve una proporción fija y el área
     * inferior (por ejemplo, una tabla o panel de detalle) mantenga
     * aproximadamente 310 píxeles de altura.
     * </p>
     *
     * <h2>Propósito</h2>
     * <ul>
     *   <li>Evitar que el divisor del {@code JSplitPane} quede desalineado cuando la ventana cambia de tamaño.</li>
     *   <li>Mantener una distribución visual coherente entre los paneles superior e inferior.</li>
     *   <li>Adaptar la interfaz a diferentes resoluciones o tamaños de pantalla.</li>
     * </ul>
     *
     * <h2>Funcionamiento</h2>
     * <p>
     * La posición del divisor se calcula restando {@code 310} píxeles a la altura total
     * de la ventana, de forma que la tabla inferior mantenga ese espacio reservado.
     * </p>
     *
     * <h2>Notas</h2>
     * <ul>
     *   <li>Debe ser llamado después de que los componentes visuales han sido inicializados.</li>
     *   <li>En entornos de redimensionamiento dinámico, se recomienda invocarlo dentro de un listener de eventos {@code componentResized}.</li>
     * </ul>
     *
     * @see javax.swing.JSplitPane#setDividerLocation(int)
     * @since 1.0
     */
	private void setTableDividerLocationEvent() {
		this.splitPaneTablaContenedor.setDividerLocation(this.getHeight() - 310);
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
			if (this.panelGridLayOutTablaBinaria == null)
				throw new Exception("El componente es null");

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
					this.panelGridLayOutTablaBinaria.add(cellPanel);
					
					
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
     * Devuelve la representación lógica completa de la tabla binaria mostrada
     * en la interfaz gráfica del editor de píxeles.
     * <p>
     * La matriz resultante es de tipo {@code boolean[16][32]} y refleja el
     * estado actual (encendido/apagado) de cada celda {@link CellPixelPanel}
     * dentro de la cuadrícula gestionada por {@code panelGridLayOutTablaBinaria}.
     * Cada posición de la matriz corresponde a la combinación de fila y columna
     * de una celda en la vista.
     * </p>
     *
     * <h2>Estructura del retorno</h2>
     * <table border="1" cellpadding="4" cellspacing="0">
     *   <tr>
     *     <th>Índice</th>
     *     <th>Tipo</th>
     *     <th>Descripción</th>
     *   </tr>
     *   <tr>
     *     <td>{@code [i][j]}</td>
     *     <td>{@code boolean}</td>
     *     <td>
     *       Estado lógico del píxel en la fila <code>i</code> y columna <code>j</code>.<br>
     *       <ul>
     *         <li>{@code true} → píxel encendido (celda verde)</li>
     *         <li>{@code false} → píxel apagado (celda amarilla)</li>
     *       </ul>
     *     </td>
     *   </tr>
     * </table>
     *
     * <h2>Uso típico</h2>
     * <pre>{@code
     * // Obtener el estado binario actual de la cuadrícula
     * boolean[][] estadoActual = getBinaryTable();
     *
     * // Acceder al estado de la celda en fila 5, columna 10
     * boolean pixelEncendido = estadoActual[5][10];
     * }</pre>
     *
     * <h2>Contexto de uso</h2>
     * Este método se utiliza comúnmente para:
     * <ul>
     *   <li>Exportar o serializar el patrón binario diseñado por el usuario.</li>
     *   <li>Transmitir el estado de la cuadrícula a un microcontrolador Tectuino.</li>
     *   <li>Actualizar vistas externas o buffers lógicos dentro del IDE.</li>
     * </ul>
     *
     * @return una matriz bidimensional de tipo {@code boolean[16][32]} que representa
     *         el estado lógico completo de la cuadrícula binaria.
     * @see CellPixelPanel
     * @see #buildTable()
     * @since 1.0
     */
	public boolean[][] getBinaryTable() {
		return this.binaryTable;
	}		

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
     * Convierte la matriz binaria actual representada por {@code binaryTable}
     * en su equivalente hexadecimal y la imprime en la consola del IDE.
     * <p>
     * Este método procesa el contenido lógico de la cuadrícula visual
     * compuesta por instancias de {@link CellPixelPanel}, generando una
     * representación hexadecimal estructurada para su posterior uso o
     * transmisión a un microcontrolador Tectuino.
     * </p>
     *
     * <h2>Flujo de ejecución</h2>
     * <ol>
     *   <li>Recorre e imprime la matriz binaria actual en formato textual
     *       (0 y 1) en la salida estándar.</li>
     *   <li>Registra en la consola del IDE el inicio del proceso de decodificación.</li>
     *   <li>Invoca {@link FrameConverter#convertBinaryMatrixToHex(boolean[][])}
     *       para generar una matriz equivalente en formato hexadecimal.</li>
     *   <li>Imprime la matriz hexadecimal resultante en la salida estándar.</li>
     *   <li>Convierte la matriz hexadecimal en una trama final utilizando
     *       {@link FrameConverter#getRamArrayFrame(String[][])}.</li>
     *   <li>Guarda el resultado en {@code hexFrame} y lo muestra en la consola del IDE.</li>
     * </ol>
     *
     * <h2>Estructuras procesadas</h2>
     * <table border="1" cellpadding="4" cellspacing="0">
     *   <tr><th>Variable</th><th>Tipo</th><th>Descripción</th></tr>
     *   <tr>
     *     <td>{@code binaryTable}</td>
     *     <td>{@code boolean[16][32]}</td>
     *     <td>Matriz que representa el estado lógico (1/0) de cada píxel.</td>
     *   </tr>
     *   <tr>
     *     <td>{@code hexMatrix}</td>
     *     <td>{@code String[filas][columnas]}</td>
     *     <td>Matriz hexadecimal generada a partir de la conversión binaria.</td>
     *   </tr>
     *   <tr>
     *     <td>{@code hexFrame}</td>
     *     <td>{@code String}</td>
     *     <td>Cadena resultante con la trama final en formato hexadecimal.</td>
     *   </tr>
     * </table>
     *
     * <h2>Gestión de errores</h2>
     * <ul>
     *   <li>Si ocurre una excepción durante el proceso de conversión o decodificación,
     *       se captura y se imprime en la consola de error estándar.</li>
     *   <li>Además, se registra un mensaje de error descriptivo en la consola del IDE
     *       mediante {@code writteResultInConsole()}.</li>
     * </ul>
     *
     * <h2>Ejemplo de uso</h2>
     * <pre>{@code
     * // Generar la trama hexadecimal actual de la cuadrícula binaria
     * convertFrame();
     * 
     * // Resultado visible en la consola:
     * // - Representación binaria 16x32
     * // - Matriz hexadecimal
     * // - Trama completa codificada
     * }</pre>
     *
     * <h2>Dependencias</h2>
     * <ul>
     *   <li>{@link FrameConverter} - para la conversión binario→hexadecimal y la generación de tramas.</li>
     *   <li>{@link #writteResultInConsole(String)} - para mostrar resultados en la consola del IDE.</li>
     *   <li>{@link #binaryTable} - fuente principal de datos binarios.</li>
     * </ul>
     *
     * @throws RuntimeException si ocurre un error durante el proceso de conversión.
     * @see FrameConverter#convertBinaryMatrixToHex(boolean[][])
     * @see FrameConverter#getRamArrayFrame(String[][])
     * @see #buildTable()
     * @since 1.0
     */
	private void convertFrame() {
		
		for(int i = 0; i < this.binaryTable.length; i++) {
			for(int j = 0; j < this.binaryTable[i].length ; j++) {
				System.out.print(this.binaryTable[i][j]? 1 + " " : 0 + " ");
			}
			System.out.println();
		}				
		
		try {
			
			this.writteResultInConsole("Iniciando decodificación...");
			//this.hexFrame = FrameConverter.convertToHexString(this.binaryTable);
			
			String[][] hexMatrix = FrameConverter.convertBinaryMatrixToHex(binaryTable);
			for(int i = 0; i< hexMatrix.length; i++) {
				for(int j = 0; j < hexMatrix[i].length; j++) {
					System.out.print(" " + hexMatrix[i][j]);
				}
				System.out.println();
			}
			
			this.hexFrame = FrameConverter.getRamArrayFrame(hexMatrix);
			
			this.writteResultInConsole("Trama obtenida");
			this.writteResultInConsole(hexFrame);
			
		}catch (Exception e) {
			
			this.writteResultInConsole("Ha ocurrido un error: " + e.getMessage());
			e.printStackTrace(System.err);
			
		}
		
	}
	
	private void clearAllPixels() {
		for(int i = 0; i < cellsPixelPanels.length; i++) {
			for(int j = 0; j < cellsPixelPanels[i].length; j++) {
				this.cellsPixelPanels[i][j].setOn(false);
				this.binaryTable[i][j] = false;
			}
		}
	}
	
	private void setConsoleInitialText() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("==========================================================\n");
		sb.append("                 Result Console Terminal                 \n");
		sb.append("==========================================================\n");
		
		this.textAreaResultConsole.setText(sb.toString());
		
	}
	
	private void writteResultInConsole(String msg) {
		this.textAreaResultConsole.append("\n>>");
		this.textAreaResultConsole.append(msg);
	}
	
	private void searchForComDevices() {
		this.cmbDispositivosCOMDisponibles.removeAllItems();
		this.aviablePortsName = SerialTransmitter.listAviablePorts();
		
		if(aviablePortsName.size() <= 0) {
			this.writteResultInConsole("No se detectaron dispositivos conectados...");
			return;
		}
		
		for(String s : aviablePortsName) {
			this.cmbDispositivosCOMDisponibles.addItem(s);
		}				
	}
	
	private void sendData() {
		
		if(this.aviablePortsName == null || this.aviablePortsName.size() < 0) {
			this.writteResultInConsole("No se detectaron dispositivos conectados");
			return;
		}
		
		if(this.hexFrame == null || this.hexFrame.length() < 128) {
			this.writteResultInConsole("Los datos son Null o incompletos");
			return;
		}
		
		SerialTransmitter tx = new SerialTransmitter();
		if(tx.openPort(this.cmbDispositivosCOMDisponibles.getSelectedItem().toString())) {
			this.writteResultInConsole("Puerto abierto: " + this.cmbDispositivosCOMDisponibles.getSelectedItem());
			boolean success = tx.sendHexFrame(this.hexFrame);
			this.writteResultInConsole("Envio: " + (success ? "OK" : "Fallido"));
			tx.closePort();
		}
		
	}

}
