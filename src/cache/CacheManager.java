package cache;

import beans.Member;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CacheManager {
    private static CacheManager cacheManager;

    public static CacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    private ArrayList<Member> memberList = new ArrayList<>();

    public void createMemberCache() {
        JsonObject jsonObject = readMembersFile();
        Type memberType = new TypeToken<ArrayList<Member>>() {}.getType();

        memberList= new Gson().fromJson(jsonObject.get("members"), memberType);
    }
    public JsonObject readMembersFile() {
        File file = new File("./resources/members.json");
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);

            return jsonObject;

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found" + e);
        }
        return new JsonObject();
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }
}
