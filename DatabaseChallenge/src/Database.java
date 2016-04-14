/**
 * Created by Meghana on 3/3/2016.
 */

import java.util.*;

public class Database {

    String key;
    String curr_value, commit_value;

    Stack<String> rollBack;

    public Database(String s)
    {
        key = s;
        curr_value = "NULL";
        rollBack = new Stack<>();
        commit_value = "NULL";
    }
}
