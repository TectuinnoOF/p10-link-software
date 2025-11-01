/**
 * Contiene todos los recursos gráficos e íconos utilizados por la aplicación
 * {@code P10-Link Software}, incluyendo imágenes para la interfaz de usuario,
 * logotipos de Tectuinno, íconos de botones y elementos visuales de estado.
 * <p>
 * Este paquete centraliza los activos gráficos del entorno visual de edición de
 * tramas binarias que se envían al chip {@code TCT-UARTP10}. Las imágenes aquí
 * contenidas se utilizan dentro de los formularios, cuadros de diálogo y menús
 * del entorno Swing, garantizando coherencia visual con la identidad del
 * proyecto Tectuinno.
 * </p>
 *
 * <h2>Propósito</h2>
 * <ul>
 *   <li>Facilitar la carga de íconos e imágenes mediante rutas relativas dentro del
 *       classpath (por ejemplo, {@code /org/tectuinno/P10Soft/app/images/logo.png}).</li>
 *   <li>Mantener una ubicación única y estructurada para los recursos gráficos.</li>
 *   <li>Permitir el empaquetado correcto de los recursos dentro del JAR final
 *       generado por Maven.</li>
 * </ul>
 *
 *
 * <h2>Buenas prácticas</h2>
 * <ul>
 *   <li>Usar nombres de archivo en minúsculas y con guiones bajos
 *       ({@code icon_connect.png}) para mantener consistencia.</li>
 *   <li>Evitar espacios, tildes o caracteres especiales en los nombres de los
 *       recursos.</li>
 *   <li>Guardar las imágenes en formato PNG con fondo transparente siempre que sea posible.</li>
 *   <li>Acceder a los recursos mediante {@link javax.swing.ImageIcon} o
 *       {@code getResourceAsStream()} para asegurar compatibilidad con empaquetado JAR.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * // Cargar un ícono desde este paquete y asignarlo a un botón
 * JButton btnSend = new JButton();
 * btnSend.setIcon(new ImageIcon(
 *     getClass().getResource("/org/tectuinno/P10Soft/app/images/icon_send.png")));
 * }</pre>
 *
 * <h2>Integración con el IDE</h2>
 * <ul>
 *   <li>Este paquete se incluye automáticamente en el classpath del proyecto Maven.</li>
 *   <li>Los recursos aquí contenidos se empaquetan dentro del archivo JAR final
 *       en {@code /org/tectuinno/P10Soft/app/images/}.</li>
 *   <li>No debe contener código fuente ni clases Java, solo recursos binarios.</li>
 * </ul>
 *
 * @author Tectuinno Development Team
 * @version 1.0
 * @since 1.0
 */
package org.tectuinno.P10Soft.app.images;