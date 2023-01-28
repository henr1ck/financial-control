package br.edu.ifpi.financialcontrol.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.io.IOException;

@Configuration
public class JsonSerializerConfig {
    //@JsonComponent
    public static class PageSerializer extends JsonSerializer<Page<?>>{
        @Override
        public void serialize(Page<?> page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();

            jsonGenerator.writePOJOField("content" ,page.getContent());
            jsonGenerator.writeNumberField("page", page.getNumber());
            jsonGenerator.writeNumberField("numberOfElements", page.getNumberOfElements());
            jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
            jsonGenerator.writeNumberField("totalPages", page.getTotalPages());

            jsonGenerator.writeEndObject();
        }
    }
}
