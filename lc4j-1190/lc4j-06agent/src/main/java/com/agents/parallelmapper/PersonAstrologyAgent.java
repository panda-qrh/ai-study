package com.agents.parallelmapper;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface PersonAstrologyAgent {
    @SystemMessage("""
            你是一名占星师，根据用户名和黄道十二宫生成星盘。
            """)
    @UserMessage("""
            为{{person}}生成星盘。 
            这个人有一个名字和一个星座。使用这两个来创建一个个性化的星座。
            """)
    @Agent("一位占星师为一个人制作星盘")
    String horoscope(@V("person") Person person);
}
