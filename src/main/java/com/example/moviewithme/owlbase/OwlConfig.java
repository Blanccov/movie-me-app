package com.example.moviewithme.owlbase;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class OwlConfig {

    @Bean
    public Set<OWLOntologyManager> ontologyManagers() {
        return new HashSet<>();
    }
}
