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

package com.filippov.data.validation.tool.rest.datasource.example.controller;

import com.filippov.data.validation.tool.rest.datasource.example.model.Company;
import com.filippov.data.validation.tool.rest.datasource.example.service.CompaniesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompaniesController {

    private final CompaniesService companiesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getCompanies(@RequestParam("page") Integer page,
                                      @RequestParam("pageSize") Integer pageSize) {
        log.debug("Companies data has been requested. Page: {}, pageSize: {}", page, pageSize);
        return companiesService.getCompanies(page, pageSize);
    }

    @GetMapping("/size")
    public Integer getSize() {
        return companiesService.getSize();
    }
}
