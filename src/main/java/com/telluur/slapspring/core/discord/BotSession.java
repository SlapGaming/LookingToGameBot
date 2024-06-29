package com.telluur.slapspring.core.discord;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class BotSession {
    @Getter
    private final JDA jda;

    @Getter
    private final BotProperties botProperties;

    public BotSession(@Autowired BotProperties botProperties, @Autowired JDA jda) {
        this.botProperties = botProperties;
        this.jda = jda;
    }

    /**
     * For the Discord snowflake IDs in the bot properties, check if they return non-null instances of their objects.
     * These snowflakes need to be correct for the bot to function properly.
     * We exit if any of the IDs return null.
     */
    @PostConstruct
    private void checkConfiguredIDs() {
        try {
            /*
            Wrapper methods wrap the return types in Objects::requireNonNull to avoid null checks in implementing classes.
            Objects::requireNonNull will throw a NullPointerException when the argument is null.
             */
            getBoundGuild();
            getLTGTX();
        } catch (NullPointerException e) {
            //SpringApplication.exit(appContext, () -> 0);
        }
    }

    /*
    Convenience wrapper methods that returns the Guild/User/Role/Channel objects corresponding to the ID's
    specified in the bot properties. These are null checked here to remove null checks in all classes.
    Objects::requireNonNull should never fail, as the validity of the ID's are checked on startup above.
    If it does, it means that the corresponding Guild/User/Role/Channel was deleted, or the bot no longer has access.
     */
    public Guild getBoundGuild() {
        return Objects.requireNonNull(jda.getGuildById(botProperties.guild()));
    }


    public TextChannel getLTGTX() {
        return Objects.requireNonNull(jda.getTextChannelById(botProperties.tx_ltg()));
    }


}

