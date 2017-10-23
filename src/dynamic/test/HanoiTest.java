/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dynamic.test;


import dynamic.hanoi.AbstractHanoi;
import dynamic.hanoi.DHanoi;
import dynamic.hanoi.RHanoi;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HanoiTest
{
	@Test
	public void compare()
	{
		final char source       = 'S';
		final char intermediate = 'I';
		final char destination  = 'D';
		
		AbstractHanoi recursive = new RHanoi();
		AbstractHanoi dynamic   = new DHanoi();
		
		for (int k=1; k<20; k++)
			assertEquals(recursive.solve(k, source, intermediate, destination), dynamic.solve(k, source, intermediate, destination));
	}
	
//	@Test
	public void testSpeed()
	{
		final int ITERATIONS = 100;
		final int MAX_K      = 20;
		
		AbstractHanoi recursive = new RHanoi();
		AbstractHanoi dynamic   = new DHanoi();
		
		for (int k=2; k<MAX_K; k++)
			 System.out.println(testSpeed(ITERATIONS, k, dynamic, recursive));
	}
	
	String testSpeed(final int iterations, final int k, AbstractHanoi... solver)
	{
		long[] times = new long[solver.length];
		int i, j, len = solver.length;
		long st, et;
		
		for (i=0; i<len; i++)
		{
			st = System.currentTimeMillis();
			for (j=0; j<iterations; j++) solver[i].solve(k,'S','I','D');
			et = System.currentTimeMillis();
			times[i] = et - st;
		}
		
		return k+"\t"+ Arrays.stream(times).mapToObj(n -> Long.toString(n)).collect(Collectors.joining("\t"));
	}
}
