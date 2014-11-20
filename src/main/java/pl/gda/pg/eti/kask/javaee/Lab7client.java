package pl.gda.pg.eti.kask.javaee;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.glassfish.jersey.SslConfigurator;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Zywiol;

/**
 *
 * @author maciek
 */
public class Lab7client {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
    SslConfigurator sslConfig = SslConfigurator.newInstance()
            .trustStoreFile("./cacerts.jks")
            .trustStorePassword("changeit")
            .keyStoreFile("./keystore.jks")
            .keyPassword("changeit");

    WiezaResourceStub stub = new WiezaResourceStub("https://localhost:8181/rest", "admin", "admin", sslConfig);

    Swiat swiat = stub.findWieze();
    List<Wieza> wieze = swiat.getWieza();
    for (Wieza wieza : wieze) {
      System.out.println(wieza);
    }
    
    System.out.println("");

    Wieza wieza = stub.findWieza(4);
    System.out.println(wieza);
    
    System.out.println("");
    
    Mag mag = new Mag();
    mag.setImie("Złowrogi");
    mag.setMana(100);
    mag.setZywiol(Zywiol.OGIEN);
    
    int status = stub.saveNewMag(wieza.getId(), mag);
    System.out.println("Dodanie maga, status: " + status);
    
  }
}
