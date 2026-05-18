package org.iftm.gerenciadorveterinarios.servicies; // Ajustado para bater com a pasta real

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Transactional
    public Animal cadastrar(Animal animal) {
        if (animal.getIdade() != null && animal.getIdade() < 0) {
            throw new IllegalArgumentException("A idade do animal não pode ser negativa.");
        }
        animal.setInternado(true);
        return repository.save(animal);
    }

    @Transactional
    public Animal darAlta(Long id) {
        Animal animal = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Animal com o ID fornecido não foi encontrado."));
        
        animal.setInternado(false);
        return repository.save(animal);
    }
}