package ru.dorofeev.graphqlapplication.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import ru.dorofeev.graphqlapplication.model.entity.User;
import ru.dorofeev.graphqlapplication.model.entity.UserSettings;
import ru.dorofeev.graphqlapplication.model.enums.ViewMode;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class UserSettingsResolver implements GraphQLResolver<UserSettings> {

    public UserSettings userSettings(User user) {
        return UserSettings.builder()
                .id(UUID.randomUUID())
                .user(user)
                .viewMode(ViewMode.TABLE)
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }
}
