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
import com.filippov.data.validation.tool.rest.datasource.example.model.Company;
import com.filippov.data.validation.tool.rest.datasource.example.model.Department;
import com.filippov.data.validation.tool.rest.datasource.example.model.Employee;
import com.filippov.data.validation.tool.rest.datasource.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DataGenerator {
    private static final List<LocalDate> DATES = generateDates();
    private static final List<String> COUNTRIES = asList("Ukraine", "USA", "Germany", "Switzerland", "Sweden", "Great Britain");

    private final ApplicationProperties applicationProperties;

    private static List<LocalDate> generateDates() {
        final List<LocalDate> result = new ArrayList<>();
        for (int y = 2010; y < 2021; y++) {
            for (int m = 1; m < 13; m++) {
                for (int d = 0; d < 31; d++) {
                    try {
                        result.add(LocalDate.of(y, m, d));
                    } catch (DateTimeException ex) {
                        // out of range - skip
                    }
                }
            }
        }
        return result;
    }

    private LocalDate getLocalDateById(int id) {
        return (id < DATES.size())
                ? DATES.get(id)
                : DATES.get(id % DATES.size());
    }

    public List<User> generateUsers() {
        final Integer n = applicationProperties.getNumberOfUsers();
        final Instant now = Instant.now();

        long l = now.toEpochMilli() + (10 * 1000);
        return IntStream.range(1, n + 1)
                .mapToObj(id -> User.builder()
                        .intId(id)
                        .longId((long) Integer.MAX_VALUE + id)
                        .username("username-" + id)
                        .password("password-" + id)
                        .birthDate(getLocalDateById(id))
                        .groupName("groupName-" + id)
                        .build())
                .collect(toList());
    }

    public List<Department> generateDepartments() {
        final Integer n = applicationProperties.getNumberOfDepartments();
        final Integer nestedListSize = applicationProperties.getSizeOfNestedLists();

        return IntStream.range(1, n + 1)
                .mapToObj(id -> Department.builder()
                        .intId(id)
                        .longId((long) Integer.MAX_VALUE + id)
                        .name("department-" + id)
                        .numberOfEmployees(id * 10)
                        .employees(generateEmployees(id, nestedListSize))
                        .build())
                .collect(toList());
    }

    public List<Company> generateCompanies() {
        final Integer n = applicationProperties.getNumberOfCompanies();

        return IntStream.range(1, n + 1)
                .mapToObj(id -> Company.builder()
                        .intId(id)
                        .longId((long) Integer.MAX_VALUE + id)
                        .active(id % 2 == 0)
                        .companyName("company-" + id)
                        .lastRevenue((id % 2 == 0) ? id + 0.555 : id + 0.07)
                        .country((id % 10 == 0) ? null : getCountry(id))
                        .dateOfCreation(getLocalDateById(id).atStartOfDay().toInstant(ZoneOffset.UTC))
                        .foundersFirstNames((id % 2 == 0) ? "Gavin,Richard,Big" : "Bill")
                        .foundersLastNames((id % 2 == 0) ? "Belson,Hendricks,Head" : "Gates")
                        .categories((id % 2 == 0) ? asList(10, 20, 150, 5, 1) : singletonList(15))
                        .competitors((id % 2 == 0) ? asList("Google", "Amazon", "Apple", "Netflix") : singletonList("Facebook"))
                        .build())
                .collect(toList());
    }

    private List<Employee> generateEmployees(int id, int size) {
        if (id % 2 == 0) {
            return IntStream.range(1, size + 1)
                    .mapToObj(i -> Employee.builder()
                            .intId(i)
                            .longId((long) Integer.MAX_VALUE + i)
                            .firstName("employee-name-" + 1)
                            .lastName("employee-surname-" + 1)
                            .build())
                    .collect(toList());
        } else {
            return singletonList(
                    Employee.builder()
                            .intId(10)
                            .longId((long) Integer.MAX_VALUE + 10)
                            .firstName("John")
                            .lastName("Doe")
                            .build());
        }
    }

    private int nextInt(int cur, int max) {
        return (cur == max) ? 1 : cur + 1;
    }

    private String getCountry(int id) {
        return (id < COUNTRIES.size())
                ? COUNTRIES.get(id)
                : COUNTRIES.get(id % COUNTRIES.size());
    }
}
