package tjd.mas.projectman.helpers;

import java.util.Map;

public class NextKey<T> {

    public Integer nextKey(Map<Integer, T> map){
        Integer nextKey = -1;
        for (Map.Entry<Integer, T> entry : map.entrySet())
        {
            if(entry.getKey()>nextKey)
                nextKey=entry.getKey();
        }
        return nextKey+1;
    }

}
