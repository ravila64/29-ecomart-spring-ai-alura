package com.aluracursos.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generador")
public class GeneradorDeProductosController {

   private final ChatClient chatClient;

   public GeneradorDeProductosController(ChatClient.Builder chatClientBuilder) {
      this.chatClient = chatClientBuilder.build();
   }

   @GetMapping   // estaba ("/ai")
   public String generadorDeProductos() {
      // String userInput, quite se coloco pregunta, ademas esta
      // otros generadores(@RequestBody String pregunta)
      var pregunta = "genera 5 productos ecológicos";
      return this.chatClient.prompt()
            .user(pregunta)  // userInput
            .call() // llamada
            .content(); // contenido de la respuesta
   }
}
