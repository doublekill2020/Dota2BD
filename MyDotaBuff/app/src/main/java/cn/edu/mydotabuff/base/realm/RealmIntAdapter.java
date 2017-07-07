package cn.edu.mydotabuff.base.realm;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import io.realm.RealmList;

/**
 * Created by sadhu on 2017/7/7.
 * 描述
 */
public class RealmIntAdapter extends TypeAdapter<RealmList<RealmInt>> {
    @Override
    public void write(JsonWriter out, RealmList<RealmInt> value) throws IOException {

    }

    @Override
    public RealmList<RealmInt> read(JsonReader in) throws IOException {
        RealmList<RealmInt> list = new RealmList<>();
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        in.beginArray();
        while (in.hasNext()) {
            list.add(new RealmInt(in.nextInt()));
        }
        in.endArray();
        return list;
    }
}
