package ru.dorofeev.graphqlapplication.resolver.mutation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.graphqlapplication.model.entity.User;
import ru.dorofeev.graphqlapplication.model.enums.UserStatusType;
import ru.dorofeev.graphqlapplication.model.mutation.UserMutationRequest;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class UserMutationResolver implements GraphQLMutationResolver {

    public User createUser(UserMutationRequest request) {

        if (Objects.isNull(request)) {
            log.warn("Переданный объект для сохранения == null!");
            return null;
        }

        return User.builder()
                .id(UUID.randomUUID())
                .externalUserId(request.getUserId())
                .status(request.getStatus())
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }

    public User updateUser(UserMutationRequest request) {

        if (Objects.isNull(request)) {
            log.warn("Переданный объект для обновления == null!");
            return null;
        }

        return User.builder()
                .id(UUID.randomUUID())
                .externalUserId(request.getUserId())
                .status(request.getStatus())
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }

    public User deleteUser(UUID userId) {
        if (Objects.isNull(userId)) {
            log.warn("Переданный userId == null!");
            return null;
        }

        return User.builder()
                .id(UUID.randomUUID())
                .externalUserId(userId)
                .status(UserStatusType.DELETED)
                .createDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }

    public Integer deleteAllUsersByStatus(UserStatusType statusType, Integer limit) {
        return limit;
    }
}
