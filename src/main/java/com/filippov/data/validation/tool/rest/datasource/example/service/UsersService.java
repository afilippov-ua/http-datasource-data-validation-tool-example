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

package com.filippov.data.validation.tool.rest.datasource.example.service;

import com.filippov.data.validation.tool.rest.datasource.example.generator.UsersGenerator;
import com.filippov.data.validation.tool.rest.datasource.example.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {
    private final List<User> data;

    public UsersService(UsersGenerator usersGenerator) {
        this.data = usersGenerator.generate();
    }

    public List<User> getUsers(Integer page, Integer pageSize) {
        return data.stream()
                .skip((page == 0) ? 0 : (long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public int getSize() {
        return data.size();
    }
}