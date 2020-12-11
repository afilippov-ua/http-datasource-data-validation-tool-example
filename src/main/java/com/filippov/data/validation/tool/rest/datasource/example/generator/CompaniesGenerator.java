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
import com.filippov.data.validation.tool.rest.datasource.example.utils.MemoryUtils;
import com.filippov.data.validation.tool.rest.datasource.example.utils.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class CompaniesGenerator extends AbstractGenerator<Company> {
    private final Integer numberOfCompanies;
    private final Integer percentOfDiscrepancies;

    public CompaniesGenerator(ApplicationProperties applicationProperties) {
        this.numberOfCompanies = applicationProperties.getNumberOfCompanies();
        this.percentOfDiscrepancies = applicationProperties.getPercentOfDiscrepancies();
    }

    @Override
    public List<Company> generate() {
        final Timer timer = Timer.start();
        log.debug("Companies data set generation has started");

        final List<Company> result = IntStream.range(1, numberOfCompanies + 1)
                .mapToObj(id -> needDiscrepancies(id) ? generateCompanyWithDiscrepancy(id) : generateNormalCompany(id))
                .collect(toList());

        if (percentOfDiscrepancies != 0) {
            log.debug("Generation additional 100 companies");
            // generate additional elements
            IntStream.range(numberOfCompanies + 1, numberOfCompanies + 101)
                    .mapToObj(this::generateNormalCompany)
                    .forEach(result::add);
        }

        log.debug("Companies data set generation has finished. Generated: {} companies. Execution time: {} ms.", result.size(), timer.stop());
        MemoryUtils.logMemoryUsage();
        return result;
    }

    private boolean needDiscrepancies(int id) {
        if (percentOfDiscrepancies != 0) {
            return id % (100 * percentOfDiscrepancies) == 0;
        }
        return false;
    }

    private Company generateNormalCompany(int id) {
        return Company.builder()
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
                .build();
    }

    private Company generateCompanyWithDiscrepancy(int id) {
        return Company.builder()
                .intId(id)
                .longId((long) Integer.MAX_VALUE + id + 1)
                .active(id % 2 == 1)
                .companyName("changed-company-" + id)
                .lastRevenue((id % 2 == 0) ? id + 0.555 : id + 0.07)
                .country((id % 10 == 0) ? null : getCountry(id))
                .dateOfCreation(getLocalDateById(id).atStartOfDay().toInstant(ZoneOffset.UTC))
                .foundersFirstNames((id % 2 == 1) ? "Gavin,Richard,Big" : "Bill")
                .foundersLastNames((id % 2 == 0) ? "Belson,Hendricks,Head" : "Gates")
                .categories((id % 2 == 0) ? asList(10, 20, 150, 5, 1) : singletonList(15))
                .competitors((id % 2 == 1) ? asList("Google", "Amazon", "Apple", "Netflix") : singletonList("Facebook"))
                .build();
    }


}
