/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.widgetset.client.grid.tutorials;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task {
    private int id;
    private String name;
    private String owner;
    private int priority;
    private int value;
    private double progress;
    private boolean approved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    private static String[] actions = { "Fix", "Implement", "Disable",
            "Activate", "Design", "Export", "Import", "Produce", "Invent",
            "Establish", "Feed", "Launch", "Deliver", "Polish" };

    private static String[] targets = { "Grid", "world peace", "teleportation",
            "Vaadin", "the dog", "the weather", "soup", "results" };
    private static String[] forenames = { "Teemu", "Johannes", "Patrik",
            "John", "Henrik", "Leif", "Artur", "Joonas" };
    private static String[] surnames = { "Suo-Anttila", "Dahlström",
            "Lindström", "Ahlroos", "Paul", "Åstrand", "Signell", "Lehtinen" };

    public static Task createRandomTask(Random random) {
        Task task = new Task();

        task.setName(pick(random, actions) + " " + pick(random, targets));
        task.setOwner(pick(random, forenames) + " " + pick(random, surnames));

        task.setPriority(random.nextInt(3) + 1);
        task.setProgress(random.nextDouble());
        task.setValue(random.nextInt(10) * 1000 + random.nextInt(10) * 100);

        task.setApproved(random.nextBoolean());

        return task;
    }

    private static String pick(Random random, String[] values) {
        return values[random.nextInt(values.length)];
    }

    public static List<Task> createRandomTasks(int count) {
        Random random = new Random();

        List<Task> tasks = new ArrayList<Task>(count);
        for (int i = 0; i < count; i++) {
            tasks.add(createRandomTask(random));
        }
        return tasks;
    }

}
