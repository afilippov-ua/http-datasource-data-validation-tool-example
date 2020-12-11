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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class AbstractGenerator<T> implements DataGenerator {
    protected final List<LocalDate> dates = generateDates();
    protected final List<String> countries = asList("Ukraine", "USA", "Germany", "Switzerland", "Sweden", "Great Britain");

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

    protected LocalDate getLocalDateById(int id) {
        return (id < dates.size())
                ? dates.get(id)
                : dates.get(id % dates.size());
    }

    protected String getCountry(int id) {
        return (id < countries.size())
                ? countries.get(id)
                : countries.get(id % countries.size());
    }
}
