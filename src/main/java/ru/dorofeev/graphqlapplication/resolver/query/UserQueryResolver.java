package ru.dorofeev.graphqlapplication.resolver.query;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.graphqlapplication.model.entity.User;
import ru.dorofeev.graphqlapplication.model.entity.UserSettings;
import ru.dorofeev.graphqlapplication.model.enums.UserStatusType;
import ru.dorofeev.graphqlapplication.model.enums.ViewMode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class UserQueryResolver implements GraphQLQueryResolver {
    public DataFetcherResult<User> getUser(UUID userId) {

        if (Objects.isNull(userId)) {
            log.warn("Переданный userId == null!");
            return DataFetcherResult.<User>newResult()
                    .error(new GenericGraphQLError("Переданный userId == null!"))
                    .build();
        }

        return DataFetcherResult.<User>newResult()
                .data(User.builder()
                        .id(UUID.randomUUID())
                        .externalUserId(userId)
                        .status(UserStatusType.CREATED)
                        .createDate(OffsetDateTime.now())
                        .updateDate(OffsetDateTime.now())
                        .settings(UserSettings.builder()
                                .id(UUID.randomUUID())
                                .viewMode(ViewMode.TABLE)
                                .createDate(OffsetDateTime.now())
                                .updateDate(OffsetDateTime.now())
                                .build()
                        ).build())
                .build();
    }

    public List<User> getUsersByStatus(UserStatusType status, int limit) {

        if (Objects.isNull(status)) {
            log.warn("Переданный userId == null!");
            return Collections.emptyList();
        }

        List<User> result = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            result.add(User.builder()
                    .id(UUID.randomUUID())
                    .externalUserId(UUID.randomUUID())
                    .status(status)
                    .createDate(OffsetDateTime.now())
                    .updateDate(OffsetDateTime.now())
                    .settings(UserSettings.builder()
                            .id(UUID.randomUUID())
                            .viewMode(ViewMode.TABLE)
                            .createDate(OffsetDateTime.now())
                            .updateDate(OffsetDateTime.now())
                            .build()
                    )
                    .build()
            );
        }

        return result;
    }
}
