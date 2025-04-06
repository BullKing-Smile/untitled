package com.coocpu.canaldemo;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;

@SpringBootTest
class CanaldemoApplicationTests {

    @Test
    void contextLoads() {
//        CanalConnector example = CanalConnectors.newClusterConnector("127.0.0.1:11111", "example", "canal", "");
//
//        example.connect();
//
//        Message message = example.get(100);
//        System.out.println(message);
//        Assert.notNull(message, "success!!!!");
    }

    @Test
    void canalTest2() {
        CanalConnector example = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",11111), "example", "", "");

        while (true) {
            example.connect();
            example.subscribe("test.*");

            Message message = example.get(100);
            System.out.println(message);
            Assert.notNull(message, "success!!!!");
        }
    }
}
