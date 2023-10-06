package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Persona;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@ManagedBean
public class PersonaController implements Serializable {
    private List<Persona> personas;
    private Persona personaSeleccionada;

    @PostConstruct
    public void init() {
        listarPersonas();
        personaSeleccionada = new Persona();
    }
    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public void agregarPersona() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String url;
            HttpEntityEnclosingRequestBase httpRequest;

            if (personaSeleccionada.getId() == null) {
                // Agregar nueva persona
                url = "http://localhost:8081/api/v1/informations";
                httpRequest = new HttpPost(url);
            } else {
                // Editar persona existente
                url = "http://localhost:8081/api/v1/informations/" + personaSeleccionada.getId();
                httpRequest  = new HttpPut(url);
            }

            // Convierte la personaSeleccionada a formato JSON
            Gson gson = new Gson();

            String json = gson.toJson(personaSeleccionada);
            System.out.println(json);
            // Configura la solicitud HTTP con el JSON
            StringEntity entity = new StringEntity(json);
            httpRequest.setEntity(entity);
            httpRequest.setHeader("Content-Type", "application/json");

            // Ejecuta la solicitud HTTP
            HttpResponse response = httpClient.execute(httpRequest);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201 || statusCode == 200) {
                // Si la creación/edición fue exitosa, vuelve a cargar la lista de personas
                listarPersonas();
                personaSeleccionada = new Persona();
            } else {
                System.err.println("Error al agregar persona. Código de estado: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPersona(Persona persona) {
        // Asigna la persona seleccionada al campo personaSeleccionada
        this.personaSeleccionada = persona;
    }

    public void listarPersonas() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8081/api/v1/informations");
            HttpResponse response = httpClient.execute(httpGet);

            // Verifica el código de respuesta HTTP para asegurarte de que fue exitoso (por ejemplo, código 200)
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String json = EntityUtils.toString(response.getEntity());
                Gson gson = new Gson();
                personas = gson.fromJson(json, new TypeToken<List<Persona>>() {}.getType());

                // Imprime la respuesta JSON por pantalla
                System.out.println("Respuesta del servicio REST:");
                System.out.println(json);
            } else {
                System.err.println("Error en la solicitud HTTP. Código de estado: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarPersona(Persona persona) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String url = "http://localhost:8081/api/v1/informations/" + persona.getId();
            HttpDelete httpDelete = new HttpDelete(url);

            // Ejecuta la solicitud HTTP para eliminar la persona
            HttpResponse response = httpClient.execute(httpDelete);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                // Si la eliminación fue exitosa (código de estado 204), vuelve a cargar la lista de personas
                listarPersonas();
            } else {
                System.err.println("Error al eliminar persona. Código de estado: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}
