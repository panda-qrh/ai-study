package com.ai02high;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.file.Paths;

@RestController
public class HighController {
    @Resource
    private Assistant2 assistant2;

    @GetMapping("/stream")
    public Flux<String> chat(String message) {
        return assistant2.chat(message);
    }

    @GetMapping("/desc")
    public Flux<String> descPic() {
        String filePath = "C:\\Users\\qrh19\\Pictures\\HErNmNdWkAAwVy-.jpg";
        ImageContent image = ImageContent.from(Paths.get(filePath), "image/jpeg");
        return assistant2.chat("描述这张图片，要求：细节处要详细描述", image);
    }

    @GetMapping("/cm")
    public Flux<String> chatMemory(String message) {
      return assistant2.chatMemory("user-123", message);
    }
}