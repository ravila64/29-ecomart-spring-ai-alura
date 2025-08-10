package com.aluracursos.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorizador")
public class CategotizadorDeProductosController {

   private final ChatClient chatClient;
   public CategotizadorDeProductosController(ChatClient.Builder chatClientBuilder) {
      this.chatClient = chatClientBuilder.build();
   }

   @GetMapping
   public String categorizarProductos(String producto) {
      var system = """
            Tu eres un categorizador de productos y solo responder la categoría del producto informado,
            selecciona una categoría de la lista:
               1. Higiene personal
               2. Electrónicos
               3. Deportes
               4. Otros
               Ejemplo de uso:
               Producto: Pelota de futbol
               Respuesta: Deportes
            """;
      return this.chatClient.prompt()
            .system(system)
            .user(producto)
            .options(ChatOptions.builder()
                  .temperature(0.82)  // 0.1 y 2.0
                  .build())
            .call()
            .content();
   }
}
