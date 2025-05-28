package com.fesa.gp8.especificador_gp8_ec7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fesa.gp8.especificador_gp8_ec7.model.CompilationResult;
import com.fesa.gp8.especificador_gp8_ec7.service.CompilerService;
import com.fesa.gp8.especificador_gp8_ec7.service.ExampleService;

@Controller
public class IDEController {
    
    @Autowired
    private CompilerService compilerService;
    
    @Autowired
    private ExampleService exampleService;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sourceCode", exampleService.getExample("Olá Mundo"));
        model.addAttribute("examples", exampleService.getExamples());
        return "ide";
    }
    
    @PostMapping(value = "/compile", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public CompilationResult compile(@RequestBody String sourceCode) {
        return compilerService.compile(sourceCode);
    }
    
    @PostMapping("/run")
    @ResponseBody
    public CompilationResult run(@RequestBody String sourceCode) {
        return compilerService.run(sourceCode);
    }
    
    @GetMapping("/example/{name}")
    @ResponseBody
    public String getExample(@PathVariable String name) {
        return exampleService.getExample(name);
    }
} 