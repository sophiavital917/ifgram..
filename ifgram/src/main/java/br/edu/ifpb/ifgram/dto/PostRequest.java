package br.edu.ifpb.ifgram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static br.edu.ifpb.ifgram.dto.CommentRequest.texto;

public record PostRequest() {
    @NotBlank(message = "A URL da imagem é obrigatória")
    public static String imagemUrl;

    @Size(max = 300, message = "A legenda deve ter no máximo 300 caracteres")
    public static String legenda;

    @NotNull(message = "O ID do autor é obrigatório")
    public static Long autorId;


    public Long autorId() {
        return Long.valueOf(texto);
    }

    public String imagemUrl() {
        return texto;
    }

    public Object legenda() {
        return texto;
    }
}

