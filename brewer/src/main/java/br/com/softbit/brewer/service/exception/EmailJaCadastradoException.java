package br.com.softbit.brewer.service.exception;

public class EmailJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailJaCadastradoException(String message){
		super(message);
	}

}
