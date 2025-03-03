package objetos;

import java.io.IOException;

import javax.imageio.ImageIO;

import principal.PanelDeJuego;

public class OBJ_Llave extends ObjetoBase {
	
	PanelDeJuego pdj;

	public OBJ_Llave(PanelDeJuego pdj) {
		
		this.pdj = pdj;

		nombre = "Llave";
		try {
			imagen = ImageIO.read(getClass().getResourceAsStream("/objetos/object_key.png"));
			uTool.escalarImagen(imagen, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
