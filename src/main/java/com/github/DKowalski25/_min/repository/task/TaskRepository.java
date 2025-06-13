package com.github.DKowalski25._min.repository.task;

import com.github.DKowalski25._min.models.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
