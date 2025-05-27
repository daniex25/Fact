package servlets;

import dto.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    @PersistenceUnit(unitName = "distribuidoPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("distribuidoPU");
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        EntityManager em = getEntityManager();
        try {
            BufferedReader reader = request.getReader();
            JSONObject json = new JSONObject(reader.lines().collect(Collectors.joining()));
            
            String logiClie = json.getString("logiClie");
            String passClie = json.getString("passClie");
            //Contraseña sugerida para el primero: "PizzaConPiña"
            Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.logiClie = :logiClie AND c.passClie = :passClie");
            query.setParameter("logiClie", logiClie);
            query.setParameter("passClie", passClie);
            
            Cliente cliente;
            try {
                cliente = (Cliente) query.getSingleResult();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Usuario o contraseña incorrectos\"}");
                return;
            }

            // Crear respuesta JSON con los datos del cliente
            JSONObject responseJson = new JSONObject();
            responseJson.put("codiClie", cliente.getCodiClie());
            responseJson.put("logiClie", cliente.getLogiClie());
            responseJson.put("nombClie", cliente.getNombClie());
            responseJson.put("appaClie", cliente.getAppaClie());
            responseJson.put("apmaClie", cliente.getApmaClie());
            responseJson.put("role", "cliente");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(responseJson.toString());
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Error en la solicitud: " + e.getMessage() + "\"}");
        } finally {
            em.close();
        }
    }
}