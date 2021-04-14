package com.learning.springboot;

import com.learning.springboot.redis.Receiver;
import com.learning.springboot.restTemplateUsing.Page;
import com.learning.springboot.shedule.SheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

//@ComponentScan   //говорит Spring'у рекурсивно искать в пакете hello и его потомках классы, помеченные прямо или косвенно Spring аннотацией @Component.
//@EnableAutoConfiguration    //переключает на приемлемое по умолчанию поведение, основанное на содержании вашего classpath. Автоконфигурация
//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class SpringbootApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringbootApplication.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

    public static void main(String[] args) throws InterruptedException { //main() метод передает управление вспомогательному классу SpringApplication, где Application.class - аргумент его run() метода. Это сообщает Spring о чтении метаданных аннотации из Application и управлении ею как компонента в Spring application context.
        //SpringApplication.run(SheduledTasks.class, args);  /////for Scheduling
//		SpringApplication.run(SpringbootApplication.class, args);

//		RestTemplate restTemplate = new RestTemplate();   // осуществляет взаимодействие с большинством RESTful сервисами однострочным заклинанием
//		Page page=restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class);
//		System.out.println(page.getAbout());


        ApplicationContext context = SpringApplication.run(SpringbootApplication.class);

        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
        CountDownLatch latch = context.getBean(CountDownLatch.class);

        logger.info("sending message...");
        template.convertAndSend("chat", "your first using of redis!");

        latch.await();

        System.exit(0);
    }

}
