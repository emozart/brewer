package br.com.softbit.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.softbit.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {
	
	private MultipartFile[] arquivos;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] arquivos, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
		this.arquivos = arquivos;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
	}
	
	@Override
	public void run() {
		
		System.out.println("Arquivo: \nQuandtidade: " + arquivos.length + "\nTamanho: " + arquivos[0].getSize());
		String nome = this.fotoStorage.salvarTemporariamente(arquivos);
		String contentType = arquivos[0].getContentType();
		resultado.setResult(new FotoDTO(nome, contentType));

	}

}
