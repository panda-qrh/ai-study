# 操作说明

## 1、创建项目，不用web项目

## 2、创建tool

## 3、在主程序中创建mcp连接

## 4、将这个项目打包，并将打包好的jar放在任意目录

## 5、在calude code的桌面端配置mcp

在 `claude_desktop_config.json` 中添加一台服务器的条目：

```json
{
  "mcpServers": {
    "my-java-tool": {
      "command": "java",
      "args": [
        "-jar",
        "/absolute/path/to/my-java-mcp-server.jar"
      ]
    }
  }
}
```

## 6、将项目启动

## 7、重启claude code，就可以使用这个mcp了
