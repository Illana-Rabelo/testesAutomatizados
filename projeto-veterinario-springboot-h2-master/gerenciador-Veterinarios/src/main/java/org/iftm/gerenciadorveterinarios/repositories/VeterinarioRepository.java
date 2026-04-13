package org.iftm.gerenciadorveterinarios.repositories;

import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

   // Para o Teste 1 (Busca exata do banco)
   Optional<Veterinario> findByNome(String nome);

   Optional<Veterinario> findByNomeIgnoreCase(String nome);

   // Para o Teste 2 (Busca por parte do nome)
   List<Veterinario> findByNomeContainingIgnoreCase(String trecho);
}
