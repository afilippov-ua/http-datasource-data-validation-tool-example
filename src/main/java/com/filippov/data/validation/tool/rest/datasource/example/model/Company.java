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

package com.filippov.data.validation.tool.rest.datasource.example.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Company {
    private final Integer intId;
    private final Long longId;
    private final Boolean active;
    private final String companyName;
    private final Double lastRevenue;
    private final String country;
    private final Instant dateOfCreation;
    private final String foundersFirstNames;
    private final String foundersLastNames;
    private final List<Integer> categories;
    private final List<String> competitors;
}
