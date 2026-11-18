package br.edu.ifpb.ifgram.Service;

import br.edu.ifpb.ifgram.Repository.PostRepository;
import br.edu.ifpb.ifgram.Repository.UserRepository;
import br.edu.ifpb.ifgram.dto.PostRequest;
import br.edu.ifpb.ifgram.dto.PostResponse;
import br.edu.ifpb.ifgram.model.Post;
import br.edu.ifpb.ifgram.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse criar(@Valid PostRequest request) {

        User autor = userRepository.findById(request.autorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Autor não encontrado"
                ));

        Post post = new Post(
                request.imagemUrl(),
                request.legenda(),
                autor
        );

        return PostResponse.from(postRepository.save(post));
    }

    public List<PostResponse> listarFeed() {
        return postRepository.listarFeed()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public List<PostResponse> listarPorAutor(Long autorId) {

        if (!userRepository.existsById(autorId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        return postRepository.findByAutorId(autorId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse buscarPorId(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Publicação não encontrada"
                ));

        return PostResponse.from(post);
    }

    @Transactional
    public void deletar(Long id) {

        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Publicação não encontrada"
            );
        }

        postRepository.deleteById(id);
    }
}