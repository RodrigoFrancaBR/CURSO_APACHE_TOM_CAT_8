package br.com.franca.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.franca.modelo.Status;
import br.com.franca.modelo.Unidade;

/**
 * Servlet implementation class HelloWorld
 */
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloWorld() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Unidade unidade = null;
		List<Unidade> listaDeUnidades = null;
		DataSource ds = null;

		Context initCtx;
		try {

			initCtx = new InitialContext();

			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			unidade = (Unidade) envCtx.lookup("bean/UnidadeFactory");

			System.out.println(unidade.toString());

			ds = (DataSource) envCtx.lookup("jdbc/cursoDb");

			Connection conn = ds.getConnection();

			Statement createStatement = conn.createStatement();

			PreparedStatement stm = conn.prepareStatement("SELECT * FROM TB_UNIDADE");

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {

				listaDeUnidades = new ArrayList<>();

				unidade = new Unidade();

				unidade.setId(rs.getLong("id"));
				unidade.setNome(rs.getString("nome"));
				unidade.setEndereco(rs.getString("endereco"));
				String status = rs.getString("status");
				unidade.setStatus(Status.getStatus(status));

				listaDeUnidades.add(unidade);
			}

			// usar a conexão
			conn.close();

		} catch (NamingException  
				|
				SQLException 
				e
				) {
			e.printStackTrace();
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath()).append(unidade.toString());
		
 		response.getWriter().append("Served at: ").append(request.getContextPath()).append(listaDeUnidades.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
