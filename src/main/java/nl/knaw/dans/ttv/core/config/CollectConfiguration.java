/*
 * Copyright (C) 2021 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.ttv.core.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.knaw.dans.lib.util.ExecutorServiceFactory;
import nl.knaw.dans.ttv.core.config.validation.UniqueInboxEntryNames;
import nl.knaw.dans.ttv.core.config.validation.UniqueInboxEntryPaths;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.file.Path;
import java.util.List;

public class CollectConfiguration {
    @Valid
    @NotNull
    @JsonProperty("inboxes")
    @Size(min = 1)
    @UniqueInboxEntryNames(message = "multiple inboxes with the same name found")
    @UniqueInboxEntryPaths(message = "multiple inboxes are configured with the same path")
    private List<InboxEntry> inboxes;

    @Valid
    @NotNull
    @JsonProperty("taskQueue")
    private ExecutorServiceFactory taskQueue;

    @Valid
    @NotNull
    @Min(1)
    private long pollingInterval;

    private int canReadTimeout = 10;

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public List<InboxEntry> getInboxes() {
        return inboxes;
    }

    public void setInboxes(List<InboxEntry> inboxes) {
        this.inboxes = inboxes;
    }

    public ExecutorServiceFactory getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(ExecutorServiceFactory taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setCanReadTimeout(int canReadTimeout) {
        this.canReadTimeout = canReadTimeout;
    }

    public int getCanReadTimeout() {
        return canReadTimeout;
    }


    public static class InboxEntry {
        @NotEmpty
        private String name;
        @NotNull
        private Path path;

        public InboxEntry() {

        }

        public InboxEntry(String name, Path path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "InboxEntry{" + "name='" + name + '\'' + ", path='" + path + '\'' + '}';
        }
    }
}
