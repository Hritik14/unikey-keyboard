package rkr.simplekeyboard.inputmethod.latin.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import rkr.simplekeyboard.inputmethod.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unikey {
    public static Map<Integer, List<Integer>> m;

    public static void init(Context context) {
        InputStream is = (InputStream) context.getResources().openRawResource(R.raw.unicode);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        m = new HashMap<Integer, List<Integer>>();
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (line.length() == 0)
                continue;
            if (line.charAt(0) == '#')
                continue;

            String[] tokens = line.split(";");
            if (tokens.length < 2)
                continue;

            String k[] = tokens[1].trim().split("\\s+");
            // TODO: Double char support possible here, maybe later
            if (k.length > 1)
                continue;
            Integer key = Integer.parseInt(k[0], 16);
            Integer val = Integer.parseInt(tokens[0].trim(), 16);

            if (m.containsKey(key))
                m.get(key).add(val);
            else {
                m.put(key, new ArrayList<Integer>());
                m.get(key).add(val);
            }
        }

        Log.d("unikey", "Map loaded, size: " + m.size());
    }
}
