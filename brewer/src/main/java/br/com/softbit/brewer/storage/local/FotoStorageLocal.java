package br.com.softbit.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.com.softbit.brewer.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage{
	
	private static final Logger LOG = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;

	public FotoStorageLocal(){
		this(FileSystems.getDefault().getPath(System.getenv("USERPROFILE"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path path){
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if(files != null && files.length > 0){
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando foto na pasta temporária.", e);
			}
		}
		return novoNome;
	}	

	@Override
	public byte[] recuperarFotoTemporaria(String nomeFoto) {
		
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro no carregamento da foto temporária.", e);
		}
	}
	
	@Override
	public byte[] recuperarFoto(String nomeFoto) {
		try {
			return Files.readAllBytes(this.local.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro no carregamento da foto.", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		
		try {
			Files.move(localTemporario.resolve(foto), local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto para o destino final.", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail.", e);
		}
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(localTemporario);
			
			if(LOG.isDebugEnabled()){
				LOG.debug("Pasta para salvar fotos temporárias criada com sucesso!");
				LOG.debug("Pasta default: " + this.local.toAbsolutePath());
				LOG.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pastas para salvamento das fotos.", e);
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal){
		
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("Nome original: %s\nNovo nome: %s", nomeOriginal, novoNome));
		}
		return novoNome;
	}


}
