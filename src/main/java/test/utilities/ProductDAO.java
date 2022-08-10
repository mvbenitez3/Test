package test.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import test.model.Product;

/**
 * Patron de diseño DAO, que permite obtener 
 * y almacenar datos
 * @author Vanessa Benítez
 *
 */
public class ProductDAO {
Persistence db_connect = new Persistence();
	
	
	/**
	 * Metodo que consiste en guardar el objeto producto en la base de datos
	 * 
	 * @param products, lista que se envia desde la logica para guardar 
	 * los datos de los productos obtenidos durante el scraping 
	 */
	public void save(List<Product> products) {
		Connection connection = db_connect.getConnection();
		PreparedStatement ps = null;
		
		try {
			String query = " insert into product (CODE, URL, NAME, PRICE) values (?, ?, ?, ?)";
			
			for (Product product : products) {
				print(product);
				
				ps = connection.prepareStatement(query);
				
				ps.setString(1, product.getCode());
				ps.setString(2, product.getUrl());
				ps.setString(3, product.getName());
				ps.setString(4, product.getPrice());
				
				ps.execute();
				ps.close();
			}
			
		} catch (Exception e) {
			System.out.println("Error"+ e);
		}
	}
	
	/**
	 * Metodo que consite en imprimir la informacion de los productos en consola
	 */
	public void print(Product product) {
		String msj = "---------------\n"
				+ product.getUrl() + "\n"
				+ product.getName() + "\n"
				+ product.getPrice() + "\n"
				+ " Code " + product.getCode();
		System.out.println(msj);
	}
}
