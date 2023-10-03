package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import factory.ConnectionFactory;
import model.Reserva;

public class ReservaDAO {
	
	private Connection con;
	
	public ReservaDAO(Connection con) {
			super();
			this.con = con;
	}
	
	public void guardar(Reserva reserva) {
		try {
			//guardamos la sentencia de registro en la variable sql
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_de_pago) "
					+ "VALUES (?,?,?,?)";
			//java.sql.Statement.RETURN_GENERATED_KEYS me retorna la clave generada por la base de datos de los atributos autoincrementales
			try(PreparedStatement state = con.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS)){
				//aqui se llenan los valores de posicion ?,?,?,? 
				state.setObject(1, reserva.getFechaE());
				state.setObject(2, reserva.getFechaS());
				state.setString(3,reserva.getValor());
				state.setString(4, reserva.getFormaPago());
				
				state.executeUpdate();
				
				//aqui obtiene el valor de las claves generadas " id "
				try(ResultSet result = state.getGeneratedKeys()){
					//mientras hallan registros
					while(result.next()) {
						/*La línea reserva.setId(result.getInt(1))
						toma el valor del primer campo generado, que generalmente corresponde
						al ID autoincremental de la reserva, y lo asigna como el ID de la reserva en el objeto reserva.*/
						reserva.setId(result.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	
	public List<Reserva> mostrar(){
		List<Reserva> resultado = new ArrayList<>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas");
			try(statement){ 
				
				statement.execute();
				
				final ResultSet result = statement.getResultSet();
				
				
				try(result){
					//mientras sigan habiendo registros, los va mostrando
					while(result.next()) {
						
						Reserva fila = new Reserva(result.getInt("id"),
								result.getObject("fecha_entrada", LocalDate.class),
								result.getObject("fecha_salida", LocalDate.class),
								result.getString("valor"),
								result.getString("forma_de_pago"));
						
						//para cada registro de result set estamos transfiriendo la informacion para un map y lo agregamos a unlistado llamado resultado
						resultado.add(fila);
				}
			}
		}
		return resultado;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		
	}

	public List<Reserva> buscarId(String id){
		List<Reserva> resultado = new ArrayList<>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id=?");
			try(statement){ 
				statement.setString(1, id);
				statement.execute();
				
				final ResultSet result = statement.getResultSet();
				
				
				try(result){
					//mientras sigan habiendo registros, los va mostrando
					while(result.next()) {
						
						Reserva fila = new Reserva(result.getInt("id"),
								result.getObject("fecha_entrada", LocalDate.class),
								result.getObject("fecha_salida", LocalDate.class),
								result.getString("valor"),
								result.getString("forma_de_pago"));
						
						//para cada registro de result set estamos transfiriendo la informacion para un map y lo agregamos a unlistado llamado resultado
						resultado.add(fila);
				}
			}
		}
		return resultado;
		} catch (SQLException e) {
			
			throw new RuntimeException();
		}
		
		
	}
	
	
	public int actualizarReserva(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago, Integer id) {
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
		       final PreparedStatement statement = con.prepareStatement("UPDATE reservas SET "
		                + "fecha_entrada = ?, "
		                + "fecha_salida = ?, "
		                + "valor = ?, "
		                + "forma_de_pago = ? "
		                + "WHERE id = ?");
			try(statement){
				
				statement.setObject(1, fechaE);
				statement.setObject(2, fechaS);
				statement.setString(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5,id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminarReserva(Integer id) {
		
		try {
			final Connection con = new ConnectionFactory().recuperaConexion();	
			
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS=0");
			final PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE id=?");
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




