package pl.gda.pg.eti.kask.javaee;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;

/**
 *
 * @author psysiu
 */
public class WiezaResourceStub {

    private final Client client;

    private final WebTarget root;

    public WiezaResourceStub(String baseAddress, String login, String password, SslConfigurator sslConfig) {
        SSLContext sslContext = sslConfig.createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(login, password);
        client.register(feature);
        root = client.target(baseAddress + "/resources/wieze");
    }

    public Swiat findWieze() {
        return root.request(MediaType.APPLICATION_JSON_TYPE).get(Swiat.class);
    }
    
    public Wieza findWieza(int id) {
        return root.path("/wieza-{id}").resolveTemplate("id", id).request(MediaType.APPLICATION_JSON_TYPE).get(Wieza.class);
    }
    
    public int saveNewMag(int wiezaId, Mag mag) {
        return root.path("/wieza-{id}/nowy-mag").resolveTemplate("id", wiezaId).
          request().post(Entity.entity(mag, MediaType.APPLICATION_JSON_TYPE)).getStatus();
    }
    
}