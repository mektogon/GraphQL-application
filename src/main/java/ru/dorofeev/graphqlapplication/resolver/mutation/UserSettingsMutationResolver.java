package ru.dorofeev.graphqlapplication.resolver.mutation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.graphqlapplication.model.entity.User;
import ru.dorofeev.graphqlapplication.model.entity.UserSettings;
import ru.dorofeev.graphqlapplication.model.enums.UserStatusType;
import ru.dorofeev.graphqlapplication.model.mutation.UserSettingMutationRequest;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class UserSettingsMutationResolver implements GraphQLMutationResolver {

    public UserSettings createUserSettings(UserSettingMutationRequest request) {
        if (Objects.isNull(request)) {
            log.warn("Переданный объект для сохранения == null!");
            return null;
        }

        return UserSettings.builder()
                .id(UUID.randomUUID())
                .user(User.builder()
                        .id(UUID.randomUUID())
                        .externalUserId(request.getUserId())
                        .status(UserStatusType.ACTIVE)
                        .build()
                )
                .viewMode(request.getViewMode())
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }

    public UserSettings updateUserSettings(UserSettingMutationRequest request) {
        if (Objects.isNull(request)) {
            log.warn("Переданный объект для обновления == null!");
            return null;
        }

        return UserSettings.builder()
                .id(UUID.randomUUID())
                .user(User.builder()
                        .id(UUID.randomUUID())
                        .externalUserId(request.getUserId())
                        .status(UserStatusType.ACTIVE)
                        .build()
                )
                .viewMode(request.getViewMode())
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }
}