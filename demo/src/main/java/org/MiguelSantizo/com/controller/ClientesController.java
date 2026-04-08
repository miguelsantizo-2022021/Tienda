package org.MiguelSantizo.com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientesController {

     @GetMapping( "/Prueba")
     public String hola() { return "Hola Alumnos de 5to mi primer programa"; }
}
