package com.studio19.dto;

import com.studio19.model.Admin;

public record AdminResponseDTO (
    Long id,
    String nome,
    String email
) {
   public static AdminResponseDTO valueOf(Admin admin) {
    return new AdminResponseDTO(
        admin.getId(),
        admin.getNome(),
        admin.getEmail());
   } 
}
