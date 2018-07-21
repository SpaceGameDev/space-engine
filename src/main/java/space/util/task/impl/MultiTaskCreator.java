package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.task.Task;
import space.util.task.TaskCreator;
import space.util.task.TaskExceptionHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MultiTaskCreator implements TaskCreator {
	
	public final Collection<? extends TaskCreator> taskCreator;
	
	public MultiTaskCreator(Collection<? extends TaskCreator> taskCreator) {
		this.taskCreator = taskCreator;
	}
	
	@Override
	public @NotNull Task create(@Nullable TaskExceptionHandler exceptionHandler) {
		return new MultiTask(taskCreator.stream().map(creator -> creator.create(exceptionHandler)).collect(Collectors.toCollection(ArrayList::new)));
	}
}
