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

package com.filippov.data.validation.tool.rest.datasource.example.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;

@Slf4j
public class MemoryUtils {

    public static void logMemoryUsage() {
        final Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();

        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";

        log.debug("========================== Memory Info ==========================");
        log.debug("Free memory: " + format.format(freeMemory / mb) + mega);
        log.debug("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
        log.debug("Max memory: " + format.format(maxMemory / mb) + mega);
        log.debug("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
        log.debug("=================================================================");
    }
}
