package com.aluracursos.ecomart.controller;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;
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
      this.chatClient = chatClientBuilder
              .defaultOptions(ChatOptions
                     .builder()
                      .model("gpt-4o-mini")
                      .build()
              ).build();
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
         var tokens = contadorDeTokens(system, producto);
         System.out.println("Cantidad de tokens "+tokens);
         // implementa la logica para la seleccion del modelo
      return this.chatClient.prompt()
            .system(system)
            .user(producto)
            .options(ChatOptions.builder()
                  .model("gpt-4o-mini")  //usa primero este modelo
                  .temperature(0.82)  // 0.1 y 2.0
                  .build())
            .call()
            .content();
   }

   // este metodo tratar de colocarlo en una clase aparte, y se inyecta en los controllers necesarios
   private int contadorDeTokens(String system, String user){
      var registry = Encodings.newDefaultEncodingRegistry();  // type EncodingRegistry
      var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);  // tyoe Encoding
      return enc.countTokens(system+user);
   }
}
