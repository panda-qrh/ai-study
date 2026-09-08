package com;

import com.enums.Events;
import com.enums.States;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

@SpringBootTest
public class HelloTest {

    @Resource
    private StateMachine<States, Events> stateMachine;

    @Test
    public void hello() {
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
    }
}