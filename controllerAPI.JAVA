import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerAPI {
    ClientAPI api;
    
    @GetMapping("/cadeiras")
    public boolean[][] getCadeiras() {
        return room.display();
    }

    @PostMapping
    public String recebeCliente(@RequestParam String comando, @RequestParam int linha, @RequestParam int coluna) {
        try{    
            if (comando.equalsIgnoreCase("reserve")) {
			    api.reserve(linha, coluna);
			    return"Cadeira " + linha + "-" + coluna + " reservada";

		    } else if (comando.equalsIgnoreCase("free")) {
			    api.free(linha, coluna);
			    return "Cadeira " + linha + "-" + coluna + " liberada";

		    } else if (comando.equalsIgnoreCase("isAvailable")) {
			    boolean available = api.isAvailable(linha, coluna);
			    return available ? "A cadeira está disponível" : "A cadeira NÃO está disponível";

            else {return "Comando invalido"}
        }
    } catch (Exception e) {
			return"Exception: " + e.toString();
	}
	
}
