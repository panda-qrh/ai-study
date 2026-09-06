package com;

import com.tools.Calculator;
import dev.langchain4j.community.mcp.server.McpServer;
import dev.langchain4j.community.mcp.server.transport.StdioMcpServerTransport;
import dev.langchain4j.mcp.protocol.McpImplementation;
import org.springframework.boot.SpringApplication;

import java.util.List;

public class Lc4j08javamcpApplication {

    public static void main(String[] args) throws InterruptedException {
        McpImplementation serverInfo = new McpImplementation("my-first-mcp-server", "v1.0.0", "我的第一个MCP stdio服务");

        McpServer mcpServer = new McpServer(List.of(new Calculator()), serverInfo);

        new StdioMcpServerTransport(System.in,System.out,mcpServer);

        Thread.currentThread().join();

    }

}
