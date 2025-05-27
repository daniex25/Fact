package servlets;

import dto.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/cliente"})
public class ClienteServlet extends HttpServlet {

    @PersistenceUnit(unitName = "distribuidoPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("distribuidoPU");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            List<Cliente> clientes = em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //recuerda que el date es en año-mes-dia no dia-año-mes
            for (Cliente c : clientes) {
                JSONObject obj = new JSONObject();
                obj.put("codiClie", c.getCodiClie());
                obj.put("ndniClie", c.getNdniClie());
                obj.put("appaClie", c.getAppaClie());
                obj.put("apmaClie", c.getApmaClie());
                obj.put("nombClie", c.getNombClie());
                obj.put("fechNaciClie", dateFormat.format(c.getFechNaciClie()));
                obj.put("logiClie", c.getLogiClie());
                obj.put("passClie", c.getPassClie());
                jsonArray.put(obj);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonArray.toString());
            out.flush();
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            Cliente cliente = new Cliente();
            cliente.setCodiClie(jsonObject.getInt("codiClie"));
            cliente.setNdniClie(jsonObject.getString("ndniClie"));
            cliente.setAppaClie(jsonObject.getString("appaClie"));
            cliente.setApmaClie(jsonObject.getString("apmaClie"));
            cliente.setNombClie(jsonObject.getString("nombClie"));
            cliente.setFechNaciClie(dateFormat.parse(jsonObject.getString("fechNaciClie")));
            cliente.setLogiClie(jsonObject.getString("logiClie"));
            cliente.setPassClie(jsonObject.getString("passClie"));

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            int codiClie = jsonObject.getInt("codiClie");
            Cliente cliente = em.find(Cliente.class, codiClie);

            if (cliente != null) {
                cliente.setNdniClie(jsonObject.getString("ndniClie"));
                cliente.setAppaClie(jsonObject.getString("appaClie"));
                cliente.setApmaClie(jsonObject.getString("apmaClie"));
                cliente.setNombClie(jsonObject.getString("nombClie"));
                cliente.setFechNaciClie(dateFormat.parse(jsonObject.getString("fechNaciClie")));
                cliente.setLogiClie(jsonObject.getString("logiClie"));
                cliente.setPassClie(jsonObject.getString("passClie"));

                em.getTransaction().begin();
                em.merge(cliente);
                em.getTransaction().commit();
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            int codiClie = Integer.parseInt(request.getParameter("codiClie"));
            Cliente cliente = em.find(Cliente.class, codiClie);

            if (cliente != null) {
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}