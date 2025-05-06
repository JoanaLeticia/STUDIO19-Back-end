package com.studio19.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.studio19.model.Projeto;
import com.studio19.repository.ProjetoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.studio19.validation.ValidationException;

@ApplicationScoped
public class ProjetoFileService implements FileService {
    private final String PATH_USER = "src/main/resources/imagens/projetos/";

    @Inject
    ProjetoRepository projetoRepository;

    @Override
    @Transactional
    public void salvar(Long id, String nomeImagem, byte[] imagem) {
        Projeto projeto = projetoRepository.findById(id);

        try {
            String nomeArquivo = salvarImagem(nomeImagem, imagem);

            // Se lista de imagens estiver vazia, inicializa
            List<String> imagens = projeto.getImagens();
            if (imagens == null) {
                imagens = new ArrayList<>();
            }

            imagens.add(nomeArquivo);
            projeto.setImagens(imagens);
        } catch (IOException e) {
            throw new ValidationException("imagem", e.getMessage());
        }
    }

    private String salvarImagem(String nomeImagem, byte[] imagem) throws IOException {
        // Verificar o tipo da imagem
        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/gif", "image/png", "image/jpeg");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportado.");
        }

        // Verificar o tamanho do arquivo - não permitir maior que 10mb
        if (imagem.length > 1024 * 1024 * 10) {
            throw new IOException("Arquivo muito grande, tamanho máximo 10mb.");
        }

        // Criar pasta quando não existir
        File diretorio = new File(PATH_USER);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Gerar nome do arquivo
        String nomeArquivo = UUID.randomUUID() 
                                + "." 
                                + mimeType.substring(mimeType.lastIndexOf("/") + 1);
        String path = PATH_USER + nomeArquivo;

        // Salvar o arquivo
        File file = new File(path);
        if (file.exists()) {
            throw new IOException("Este arquivo já existe. Programador, você deve melhorar esse código");
        }

        // Criar o arquivo no SO
        file.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imagem);
            fos.flush();
        }

        return nomeArquivo;
    }

    @Override
    public File download(String nomeImagem) {
        return new File(PATH_USER + nomeImagem);
    }
}
