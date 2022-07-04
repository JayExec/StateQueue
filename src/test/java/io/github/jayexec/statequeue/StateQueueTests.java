package io.github.jayexec.statequeue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;

class StateQueueTests
{
	@Test
	void testPushUndo()
	{
		StateQueue<Integer> stateQueue = new StateQueue<>(8, (Integer i) -> i, 0);
		for(int i = 1; i < 16; i++)
			stateQueue.push(i);
		
		for(int test = 14; test >= 8; test--)
			if (stateQueue.isUndo() == false || stateQueue.undo() != test)
				Assertions.fail();
	}
	
	@Test
	void testPushUndoRedo()
	{
		StateQueue<Integer> stateQueue = new StateQueue<>(8, (Integer i) -> i, 0);
		for(int i = 1; i < 16; i++)
			stateQueue.push(i);
		
		while (stateQueue.isUndo())
			stateQueue.undo();
		
		for(int test = 9; test < 16; test++)
			if ((stateQueue.isRedo() == false && test == 15) || stateQueue.redo() != test)
				Assertions.fail();
	}
	
	@Test
	void testRemoveRedoStatesOnPush()
	{
		StateQueue<Integer> stateQueue = new StateQueue<>(16, (Integer i) -> i, 0);
		for(int i = 1; i < 16; i++)
			stateQueue.push(i);
		
		for(int i = 0; i < 12; i++)
			stateQueue.undo();
		
		for(int i = 1; i <= 4; i++)
			stateQueue.push(i * 1000);
		
		while(stateQueue.isUndo())
			stateQueue.undo();
		
		int[] resultTab = new int[]{ 0, 1, 2, 3, 1000, 2000, 3000, 4000 };
		
		int currentState = stateQueue.current();
		
		for(int i = 0; i < 8; i++)
		{
			if (currentState != resultTab[i])
				Assertions.fail();
			
			if (i != 7)
			{
				if (stateQueue.isRedo() == false)
					Assertions.fail();
				
				currentState = stateQueue.redo();
			}
		}
		
		if (stateQueue.isRedo())
			Assertions.fail();
	}
}