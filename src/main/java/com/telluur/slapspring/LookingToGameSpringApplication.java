package com.telluur.slapspring;

import com.telluur.slapspring.core.discord.BotProperties;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.LoginException;
import java.util.Collection;
import java.util.EnumSet;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class LookingToGameSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LookingToGameSpringApplication.class, args);
    }

    @Bean
    public JDA initJDA(@Autowired BotProperties botProperties) throws LoginException, InterruptedException {
        log.info("Bot config: {}", botProperties.toString());
        Collection<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES);
        return JDABuilder
                .create(botProperties.token(), intents)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build()
                .awaitReady();
    }


    @Bean
    public String baseUrl(@Autowired BotProperties properties) {
        return properties.web_base_url();
    }


}

