package com.steven.AIAnswering.manager;

import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Cos Manager Test
 *
 */
@SpringBootTest
class CosManagerTest {

    @Resource
    private CosManager cosManager;

    @Test
    void putObject() {
        cosManager.putObject("test", "test.json");
    }
}