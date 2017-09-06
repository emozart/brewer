package br.com.softbit.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.softbit.brewer.dto.FotoDTO;
import br.com.softbit.brewer.storage.FotoStorage;
import br.com.softbit.brewer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] arquivos){
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(arquivos, resultado, fotoStorage));
		thread.start();
		return resultado;
	}

	@GetMapping("/temp/{nomeFoto:.*}")
	public byte[] recuperaFotoTemporaria(@PathVariable String nomeFoto){
		return fotoStorage.recuperarFotoTemporaria(nomeFoto);
	}
	
	@GetMapping("/{nomeFoto:.*}")
	public byte[] recuperaFoto(@PathVariable String nomeFoto){
		return fotoStorage.recuperarFoto(nomeFoto);
	}
}
