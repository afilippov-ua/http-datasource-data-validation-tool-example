/*
 *   Copyright 2018-2020 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.filippov.data.validation.tool.rest.datasource.example.generator;

import com.filippov.data.validation.tool.rest.datasource.example.config.ApplicationProperties;
import com.filippov.data.validation.tool.rest.datasource.example.model.User;
import com.filippov.data.validation.tool.rest.datasource.example.utils.MemoryUtils;
import com.filippov.data.validation.tool.rest.datasource.example.utils.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class UsersGenerator extends AbstractGenerator<User> {

    private final Integer numberOfUsers;
    private final Integer percentOfDiscrepancies;

    public UsersGenerator(ApplicationProperties applicationProperties) {
        this.numberOfUsers = applicationProperties.getNumberOfUsers();
        this.percentOfDiscrepancies = applicationProperties.getPercentOfDiscrepancies();
    }

    @Override
    public List<User> generate() {
        final Timer timer = Timer.start();
        log.debug("Users data set generation has started");

        final List<User> result = IntStream.range(1, numberOfUsers + 1)
                .mapToObj(id -> needDiscrepancies(id) ? generateUserWithDiscrepancies(id) : generateNormalUser(id))
                .collect(toList());

        if (percentOfDiscrepancies != 0) {
            log.debug("Generation additional 100 users");
            IntStream.range(numberOfUsers + 1, numberOfUsers + 101)
                    .mapToObj(this::generateNormalUser)
                    .forEach(result::add);
        }
        log.debug("Users data set generation has finished. Generated: {} users. Execution time: {} ms.", result.size(), timer.stop());
        MemoryUtils.logMemoryUsage();
        return result;
    }

    private boolean needDiscrepancies(int id) {
        if (percentOfDiscrepancies != 0) {
            return id % (100 * percentOfDiscrepancies) == 0;
        }
        return false;
    }

    private User generateNormalUser(int id) {
        return User.builder()
                .intId(id)
                .longId((long) Integer.MAX_VALUE + id)
                .username("username-" + id)
                .password("password-" + id)
                .birthDate(getLocalDateById(id))
                .groupName("groupName-" + id)
                .build();
    }

    private User generateUserWithDiscrepancies(int id) {
        return User.builder()
                .intId(id)
                .longId((long) Integer.MAX_VALUE + id + 1)
                .username("changed-username-" + id)
                .password("changed-password-" + id)
                .birthDate(getLocalDateById(id + 1))
                .groupName("changed-groupName-" + id)
                .build();
    }
}
