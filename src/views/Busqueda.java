package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ReservaController;
import model.Huespedes;
import model.Reserva;
import controller.HuespedesController;


import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.text.DateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private ReservaController reservaController;
	private HuespedesController huespedesController;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservasView reservasView;
	String reserva;
	String huespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservasView = new ReservasView();
		this.reservaController = new ReservaController();
		this.huespedesController = new HuespedesController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tbReservas);
		
		
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblTitulo = new JLabel("SISTEMA DE BÚSQUEDA");
		lblTitulo.setBounds(331, 62, 280, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		contentPane.add(lblTitulo);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(20, 169, 865, 328);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(panel);
		
		

		tbHuespedes = new JTable();
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes, null);
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Numero de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Numero de Reserva");
		mostrarTablaHuespedes();

		
		
		
		tbReservas = new JTable();
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas, null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarTablaReservas();
		
		JLabel logo = new JLabel("");
		logo.setBounds(56, 51, 104, 107);
		logo.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		contentPane.add(logo);
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(539, 159, 193, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {					
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				} else {
					mostrarTablaReservasId();		
					mostrarTablaHuespedesId();
			}
				}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0) {
					ActualizarReservas();
					limpiarTabla();
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				}
				else if (filaHuespedes >= 0) {
					ActualizarHuespedes();
					limpiarTabla();
					mostrarTablaHuespedes();
					mostrarTablaReservas();
					
				}else {
					JOptionPane.showMessageDialog(null, "Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para editar");
				}
			}
		});
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0) {

					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?"); 

					if(confirmar == JOptionPane.YES_OPTION){

						String valor = tbReservas.getValueAt(filaReservas, 0).toString();			
						reservaController.eliminarReservas(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						mostrarTablaReservas();
						mostrarTablaHuespedes();
					}
				}

				else if (filaHuespedes >= 0) {

					huespedes = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
					int confirmarh = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?"); 

					if(confirmarh == JOptionPane.YES_OPTION){

						String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();			
						huespedesController.eliminarH(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						mostrarTablaHuespedes();
						mostrarTablaReservas();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para eliminar");
				}							
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	private List<Reserva> MostrarReservas(){
		return this.reservaController.mostrar();
	}
	
	private List<Reserva> buscarId() {
		return this.reservaController.buscar(txtBuscar.getText());
	}
	//
	private List<Huespedes> MostrarHuespedes(){
		return this.huespedesController.mostrar();
	}
	
	private List<Huespedes> buscarHuespedesId() {
		return this.huespedesController.buscar(txtBuscar.getText());
	}
	

	
	private void mostrarTablaReservas(){
		List<Reserva> reserva =  MostrarReservas();
		modelo.setRowCount(0);
		try {
			reserva.forEach(reservas -> modelo.addRow(
					new Object[] {
							
							reservas.getId(),
							reservas.getFechaE(),
							reservas.getFechaS(),
							reservas.getValor(),
							reservas.getFormaPago()
							
					}));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void mostrarTablaReservasId(){
		List<Reserva> reserva =  buscarId();
		modelo.setRowCount(0);
		try {
			reserva.forEach(reservas -> modelo.addRow(
					new Object[] {
							
							reservas.getId(),
							reservas.getFechaE(),
							reservas.getFechaS(),
							reservas.getValor(),
							reservas.getFormaPago()
							
					}));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void ActualizarReservas() {		
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
        .ifPresentOrElse(fila -> {
        	
        	LocalDate dataE;
        	LocalDate dataS;
        	Object valorOriginal = modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn());
        	
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	
        	String fechaEn = modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString();
            String fechaSa = modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString();
            String regex = "^(?:19|20)[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
                
            // Verificar si las fechas tienen el formato correcto
            if (!fechaEn.matches(regex) || !fechaSa.matches(regex)) {
                       
                       JOptionPane.showMessageDialog(this, String.format("El formato de fecha no es válido (debe ser yyyy-MM-dd)."));
                       modelo.setValueAt(valorOriginal,tbReservas.getSelectedRow(), tbReservas.getSelectedColumn());
                       return;
            }
            dataE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(), dateFormat);
            dataS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(), dateFormat);

        	String valor = calcularValorReserva(dataE, dataS);        	
        	if(valor!=null) {
        		
    			String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
    			Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
    			if(tbReservas.getSelectedColumn()==0) {
            		JOptionPane.showMessageDialog(this, String.format("No se pueden modificar los ID"));
            	}else {
            		this.reservaController.actualizarReservas(dataE, dataS, valor, formaPago, id);
            		JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
            	}
        	}
        	
		}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
		
	}

	private String calcularValorReserva(LocalDate dateE, LocalDate dateS) {
	    if (dateE != null && dateS != null) {
	        if (dateE.isAfter(dateS)) {
	            // La fecha de entrada es posterior a la fecha de salida
	        	JOptionPane.showMessageDialog(null,"la fecha de entrada no puede ser posterior  a la fecha de salida", 
						"Error en fechas", JOptionPane.ERROR_MESSAGE);
	        	
				
	        	return null;
	        } else {
	            int dias = (int) ChronoUnit.DAYS.between(dateE, dateS);
	            int noche = 50;
	            int valor = dias * noche;
	            return "$" + valor;
	        }
	    } else {
	        return "";
	    }
	}

	
	
	
	private void mostrarTablaHuespedes(){
		List<Huespedes> huesped =  MostrarHuespedes();
		modeloHuesped.setRowCount(0);
		try {
			huesped.forEach(huespedes -> modeloHuesped.addRow(
					new Object[] {
							
							huespedes.getId(),
							huespedes.getNombre(),
							huespedes.getApellido(),
							huespedes.getFechaNacimiento(),
							huespedes.getNacionalidad(),
							huespedes.getTelefono(),
							huespedes.getIdReserva()
							
						
							
					}));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void mostrarTablaHuespedesId(){
		List<Huespedes> huesped =  buscarHuespedesId();
		modeloHuesped.setRowCount(0);
		try {
			huesped.forEach(huespedes -> modeloHuesped.addRow(
					new Object[] {
							
							huespedes.getId(),
							huespedes.getNombre(),
							huespedes.getApellido(),
							huespedes.getFechaNacimiento(),
							huespedes.getNacionalidad(),
							huespedes.getTelefono(),
							huespedes.getIdReserva()
							
					}));
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	private void ActualizarHuespedes() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
        .ifPresentOrElse(fila -> {
        	
        	LocalDate fechaNacimiento;
        	Object valorOriginal = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn());
        	
        	Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
        	String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
        	String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
        	
        	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        		
        	String fechaN = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString();
        	String regex = "^(?:19|20)[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
            // Verificar si las fechas tienen el formato correcto
            if (!fechaN.matches(regex)) {
                	JOptionPane.showMessageDialog(this, String.format("El formato de fecha no es válido (debe ser yyyy-MM-dd)."));
                    modeloHuesped.setValueAt(valorOriginal,tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn());
                    return;
             }
              
      		fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString(), dateFormat);
        	String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
        	String telefono= (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
        	Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
			
        	
			
        	if(tbHuespedes.getSelectedColumn()==0) {
        		JOptionPane.showMessageDialog(this, String.format("No se pueden modificar los ID"));
        	}else {
        		this.huespedesController.actualizarH(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
        		JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
        	}
        	
        	
		}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
		
	}



	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}


	

	

	
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
