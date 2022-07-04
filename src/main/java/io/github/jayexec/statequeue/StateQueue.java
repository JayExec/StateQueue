package io.github.jayexec.statequeue;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * Implementation of a state queue which provides {@code undo} and {@code redo}
 * operations. States are copied before pushing and after retrieving from queue.
 * @param   <T>   Type of elements stored in the state queue.
 * @author  JayExec
 * @author  jayexec.github.io
 * @version 1.0
 * @since   1.0
 */
public class StateQueue<T>
{
	private int maxLength;
	private int current;
	private final LinkedList<T> states;
	private final Function<T, T> copyFunction;
	
	/**
	 * Constructs a state queue for provided object.
	 * @param maxLength     Maximum number of states stored in the queue.
	 * @param copyFunction	Function used to copy a state.
	 * @param initialState  First state pushed to queue.
	 */
	public StateQueue(int maxLength, Function<T, T> copyFunction, T initialState)
	{
		this.maxLength = maxLength;
		this.copyFunction = copyFunction;
		current = 0;
		states = new LinkedList<>();
		states.add(copyFunction.apply(initialState));
	}
	
	/**
	 * Pushes updated state to the queue and updates current state mark. Removes
	 * succeeding and oldest preceding states, if necessary.
	 * @param state         New Object state.
	 */
	public void push(T state)
	{
		while (current < states.size() - 1)
			states.removeLast();
		
		if (states.size() == maxLength)
		{
			states.removeFirst();
			current--;
		}
		
		states.add(copyFunction.apply(state));
		current++;
	}
	
	/**
	 * Checks whether queue contains preceding states.
	 * @return {@code true} if queue contains preceding states, {@code false} otherwise.
	 */
	public boolean isUndo()
	{
		return current > 0;
	}
	
	/**
	 * Checks whether queue contains succeeding states.
	 * @return {@code true} if queue contains succeeding states, {@code false} otherwise.
	 */
	public boolean isRedo()
	{
		return current < states.size() - 1;
	}
	
	/**
	 * Decrements current state mark and returns preceding object state, if
	 * available. Otherwise, returns current state.
	 * @return Copy of the object state.
	 */
	public final T undo()
	{
		if (isUndo())
			current--;
		return copyFunction.apply(states.get(current));
	}
	
	/**
	 * Increments current state mark and returns succeeding object state, if
	 * available. Otherwise, returns current state.
	 * @return Copy of the object state.
	 */
	public final T redo()
	{
		if (isRedo())
			current++;
		return copyFunction.apply(states.get(current));
	}
	
	/**
	 * Returns current object state.
	 * @return Copy of the object state.
	 */
	public final T current()
	{
		return copyFunction.apply(states.get(current));
	}
}