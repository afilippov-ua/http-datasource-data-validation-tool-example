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
import com.filippov.data.validation.tool.rest.datasource.example.model.Department;
import com.filippov.data.validation.tool.rest.datasource.example.model.Employee;
import com.filippov.data.validation.tool.rest.datasource.example.utils.MemoryUtils;
import com.filippov.data.validation.tool.rest.datasource.example.utils.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class DepartmentsGenerator extends AbstractGenerator<Department> {
    private final Integer numberOfDepartments;
    private final Integer percentOfDiscrepancies;
    private final Integer sizeOfNestedLists;

    public DepartmentsGenerator(ApplicationProperties applicationProperties) {
        this.numberOfDepartments = applicationProperties.getNumberOfDepartments();
        this.percentOfDiscrepancies = applicationProperties.getPercentOfDiscrepancies();
        this.sizeOfNestedLists = applicationProperties.getSizeOfNestedLists();
    }

    @Override
    public List<Department> generate() {
        final Timer timer = Timer.start();
        log.debug("Departments data set generation has started");

        final List<Department> result = IntStream.range(1, numberOfDepartments + 1)
                .mapToObj(id -> needDiscrepancies(id) ? generateDepartmentWithDiscrepancies(id) : generateNormalDepartment(id))
                .collect(toList());

        if (percentOfDiscrepancies != 0) {
            log.debug("Generation additional 100 departments");
            // generate additional elements
            IntStream.range(sizeOfNestedLists + 1, sizeOfNestedLists + 101)
                    .mapToObj(this::generateNormalDepartment)
                    .forEach(result::add);
        }

        log.debug("Departments data set generation has finished. Generated: {} departments. Execution time: {} ms.", result.size(), timer.stop());
        MemoryUtils.logMemoryUsage();
        return result;
    }

    private boolean needDiscrepancies(int id) {
        if (percentOfDiscrepancies != 0) {
            return id % (100 * percentOfDiscrepancies) == 0;
        }
        return false;
    }

    private Department generateNormalDepartment(int id) {
        return Department.builder()
                .intId(id)
                .longId((long) Integer.MAX_VALUE + id)
                .name("department-" + id)
                .numberOfEmployees(id * 10)
                .employees(generateEmployees(id))
                .build();
    }

    private Department generateDepartmentWithDiscrepancies(int id) {
        return Department.builder()
                .intId(id)
                .longId((long) Integer.MAX_VALUE + id + 2)
                .name("changed-department-" + id)
                .numberOfEmployees(id * 15)
                .employees((id % 2 == 0) ? generateEmployees(id) : generateEmployees(id + 1))
                .build();
    }

    private List<Employee> generateEmployees(int id) {
        if (id % 2 == 0) {
            return IntStream.range(1, sizeOfNestedLists + 1)
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
}
