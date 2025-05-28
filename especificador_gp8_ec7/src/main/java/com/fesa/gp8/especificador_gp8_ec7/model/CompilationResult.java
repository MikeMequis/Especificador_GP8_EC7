package com.fesa.gp8.especificador_gp8_ec7.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CompilationResult {
    private boolean success;
    private String output;
    private String error;
    private String parseTree;
} 