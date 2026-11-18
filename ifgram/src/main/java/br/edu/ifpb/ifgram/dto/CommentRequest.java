package br.edu.ifpb.ifgram.dto;

import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        String texto,
        Long postId,
        Long autorId
) {
}