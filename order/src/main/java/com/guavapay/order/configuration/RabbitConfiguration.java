package com.guavapay.order.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(rabbitHost);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue queueCreate() {
        return new Queue("order.create");
    }

    @Bean
    public Queue queueGetOrderById() {
        return new Queue("order.getOrderById");
    }

    @Bean
    public Queue queueGetUserOrders() {
        return new Queue("order.getUserOrders");
    }

    @Bean
    public Queue queueGetCourierOrders() {
        return new Queue("order.getCourierOrders");
    }

    @Bean
    public Queue queueChangeStatus() {
        return new Queue("order.changeStatus");
    }

    @Bean
    public Queue queueChangeDestination() {
        return new Queue("order.changeDestination");
    }

    @Bean
    public Queue queueCancel() {
        return new Queue("order.cancel");
    }

    @Bean
    public Queue queueAssignToCourier() {
        return new Queue("order.assignToCourier");
    }

    @Bean
    public Queue queueGetAll() {
        return new Queue("order.getAll");
    }

    @Bean
    public Queue queueSetCoordinates() {
        return new Queue("order.setCoordinates");
    }

    @Bean
    public Queue queueCreateCourier() {
        return new Queue("courier.create");
    }

    @Bean
    public Queue queueGetAllCourier() {
        return new Queue("courier.getAll");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public Binding bindingCreate() {
        return BindingBuilder.bind(queueCreate()).to(directExchange()).with("order.create");
    }

    @Bean
    public Binding bindingGetOrderById() {
        return BindingBuilder.bind(queueGetOrderById()).to(directExchange()).with("order.getOrderById");
    }

    @Bean
    public Binding bindingGetUserOrders() {
        return BindingBuilder.bind(queueGetUserOrders()).to(directExchange()).with("order.getUserOrders");
    }

    @Bean
    public Binding bindingGetCourierOrders() {
        return BindingBuilder.bind(queueGetCourierOrders()).to(directExchange()).with("order.getCourierOrders");
    }

    @Bean
    public Binding bindingChangeStatus() {
        return BindingBuilder.bind(queueChangeStatus()).to(directExchange()).with("order.changeStatus");
    }

    @Bean
    public Binding bindingChangeDestination() {
        return BindingBuilder.bind(queueChangeDestination()).to(directExchange()).with("order.changeDestination");
    }

    @Bean
    public Binding bindingCancel() {
        return BindingBuilder.bind(queueCancel()).to(directExchange()).with("order.cancel");
    }

    @Bean
    public Binding bindingAssignToCourier() {
        return BindingBuilder.bind(queueAssignToCourier()).to(directExchange()).with("order.assignToCourier");
    }

    @Bean
    public Binding bindingGetAll() {
        return BindingBuilder.bind(queueGetAll()).to(directExchange()).with("order.getAll");
    }

    @Bean
    public Binding bindingSetCoordinates() {
        return BindingBuilder.bind(queueSetCoordinates()).to(directExchange()).with("order.setCoordinates");
    }

    @Bean
    public Binding bindingGetAllCourier() {
        return BindingBuilder.bind(queueGetAllCourier()).to(directExchange()).with("courier.getAll");
    }

    @Bean
    public Binding bindingCreateCourier() {
        return BindingBuilder.bind(queueCreateCourier()).to(directExchange()).with("courier.create");
    }

}
