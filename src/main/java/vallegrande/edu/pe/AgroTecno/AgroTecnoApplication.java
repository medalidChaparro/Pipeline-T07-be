package vallegrande.edu.pe.AgroTecno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AgroTecnoApplication {

    @PostConstruct
    public void init() {
        // Configurar zona horaria por defecto para toda la aplicación
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        System.out.println("Zona horaria configurada: " + TimeZone.getDefault().getID());
    }

    public static void main(String[] args) {
        SpringApplication.run(AgroTecnoApplication.class, args);
        System.out.println("Aplicación AgroTecno iniciada correctamente!");
    }
}