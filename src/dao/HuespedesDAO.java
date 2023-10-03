package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Huespedes;
import model.Reserva;

public class HuespedesDAO {

	private Connection con;
	
	public HuespedesDAO(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Huespedes huespedes) {
		try {
			String sql = "INSERT INTO huespedes(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva)"
					+ "VALUES(?,?,?,?,?,?)";
			
			try(PreparedStatement state = con.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS)){
				state.setString(1, huespedes.getNombre());
				state.setString(2, huespedes.getApellido());
				state.setObject(3, huespedes.getFechaNacimiento());
				state.setString(4, huespedes.getNacionalidad());
				state.setString(5, huespedes.getTelefono());
				state.setInt(6, huespedes.getIdReserva());
				
				state.execute();
				
				try(ResultSet result = state.getGeneratedKeys()){
					while(result.next()) {
						
						huespedes.setId(result.getInt(1));
						
					}
					
				}
				
				
			} 
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	
	public List<Huespedes> mostrar(){
		List<Huespedes> resultado = new ArrayList<>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes");
			try(statement){ 
				
				statement.execute();
				
				final ResultSet result = statement.getResultSet();
				
				
				try(result){
					//mientras sigan habiendo registros, los va mostrando
					while(result.next()) {
						
						Huespedes fila = new Huespedes(
								result.getInt("id"),
								result.getString("nombre"),
								result.getString("apellido"),
								result.getObject("fecha_nacimiento", LocalDate.class),
								result.getString("nacionalidad"),
								result.getString("telefono"),
								result.getInt("id_reserva"));
						
						//para cada registro de result set estamos transfiriendo la informacion para un map y lo agregamos a unlistado llamado resultado
						resultado.add(fila);
						
						
				}
			}
		}
		return resultado;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}

	public List<Huespedes> buscarId(String id){
		List<Huespedes> resultado = new ArrayList<>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT id,nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?");
			try(statement){ 
				statement.setString(1, id);
				statement.execute();
				
				final ResultSet result = statement.getResultSet();
				
				
				try(result){
					//mientras sigan habiendo registros, los va mostrando
					while(result.next()) {
						
						Huespedes fila = new Huespedes(
								result.getInt("id"),
								result.getString("nombre"),
								result.getString("apellido"),
								result.getObject("fecha_nacimiento", LocalDate.class),
								result.getString("nacionalidad"),
								result.getString("telefono"),
								result.getInt("id_reserva"));
						
						//para cada registro de result set estamos transfiriendo la informacion para un map y lo agregamos a unlistado llamado resultado
						resultado.add(fila);
				}
			}
		}
		return resultado;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}	
	public int actualizarHuesped(Integer id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva) {
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
		       final PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET "
		                + "nombre = ?, "
		                + "apellido = ?, "
		                + "fecha_nacimiento = ?, "
		                + "nacionalidad = ?, "
		                + "telefono = ?, "
		                + "id_reserva = ? "
		                + "WHERE id = ?"); 
		       
		  
			try(statement){
			
				
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setObject(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6,idReserva);
				statement.setInt(7, id);
				
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public int eliminarHuesped(Integer id) {
		
		try {
			final Connection con = new ConnectionFactory().recuperaConexion();	
			
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS=0");
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id=?");
			try(statement){
				statement.setInt(1,id);
				statement.execute();
				state.execute("SET FOREIGN_KEY_CHECKS=1");
				//para saber si algo fue realmene eliminado se usa el siguiente metodo
				//nos devuelve cuantas filas fueron modificadas
				return statement.getUpdateCount();
			}
		
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	
	}
	
}
