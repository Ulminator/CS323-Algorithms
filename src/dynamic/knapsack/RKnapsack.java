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
package dynamic.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RKnapsack extends AbstractKnapsack
{
    @Override
    public List<KnapsackItem> solve(KnapsackItem[] items, int maxWeight)
    {
        Arrays.sort(items);
        return solve(items, maxWeight, items.length-1);
    }
    
    /** @param items sorted by their weights in ascending order. */
    private List<KnapsackItem> solve(KnapsackItem[] items, int maxWeight, int index)
    {
        if (index < 0 || maxWeight == 0) return new ArrayList<>();
        KnapsackItem item = items[index];
        
        if (item.getWeight() > maxWeight)
            return solve(items, maxWeight, index-1);
        else
        {
            List<KnapsackItem> with    = solve(items, maxWeight - item.getWeight(), index-1);
            List<KnapsackItem> without = solve(items, maxWeight, index-1);
            with.add(item);
            return (getTotalValue(with) > getTotalValue(without)) ? with : without;
        }
    }
}
