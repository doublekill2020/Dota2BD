package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.badr.infodota.api.dotabuff.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 26.12.2014
 * 17:43
 */
public class AccountDao extends GeneralDaoImpl<Unit> {
    public static final String TABLE_NAME = "accounts";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCAL_NAME = "local_name";
    public static final String COLUMN_GROUP = "group_id";
    public static final String COLUMN_SEARCHED = "searched";
    public static final String COLUMN_TIME_ADDED = "time_added";
    public static final String COLUMN_ICON = "icon";

    private static final String CREATE_TABLE_QUERY =
            " ( " +
                    COLUMN_ID + " integer not null, " +
                    COLUMN_NAME + " text default null, " +
                    COLUMN_LOCAL_NAME + " text default null, " +
                    COLUMN_GROUP + " integer default 0, " +
                    COLUMN_SEARCHED + " integer default 0, " +
                    COLUMN_ICON + " text default null, " +
                    COLUMN_TIME_ADDED + " integer default null);";
    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_LOCAL_NAME,
            COLUMN_GROUP,
            COLUMN_SEARCHED,
            COLUMN_ICON,
            COLUMN_TIME_ADDED
    };

    @Override
    protected String getNoTableNameDataBaseCreateQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public Unit cursorToEntity(Cursor cursor, int index) {
        Unit entity = new Unit();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setName(cursor.getString(i));
        i++;
        entity.setLocalName(cursor.getString(i));
        i++;
        entity.setGroup(Unit.Groups.values()[cursor.getInt(i)]);
        i++;
        entity.setSearched(cursor.getInt(i) == 1);
        i++;
        entity.setIcon(cursor.getString(i));
        //ignore time added
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Unit entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (TextUtils.isEmpty(entity.getName())) {
            values.putNull(COLUMN_NAME);
        } else {
            values.put(COLUMN_NAME, entity.getName());
        }
        if (TextUtils.isEmpty(entity.getLocalName())) {
            values.putNull(COLUMN_LOCAL_NAME);
        } else {
            values.put(COLUMN_LOCAL_NAME, entity.getLocalName());
        }
        if (TextUtils.isEmpty(entity.getIcon())) {
            values.putNull(COLUMN_ICON);
        } else {
            values.put(COLUMN_ICON, entity.getIcon());
        }
        if (entity.getGroup() != Unit.Groups.NONE) {
            values.put(COLUMN_GROUP, entity.getGroup().ordinal());
        }
        values.put(COLUMN_TIME_ADDED, System.currentTimeMillis());
        if (entity.isSearched()) {
            values.put(COLUMN_SEARCHED, 1);
        }

        return values;
    }

    @Override
    public String getDefaultOrderColumns() {
        return COLUMN_TIME_ADDED + " desc";
    }

    public List<Unit> getSearchedEntities(SQLiteDatabase database) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_SEARCHED + "=1", null, null, null, getDefaultOrderColumns());
        try {
            List<Unit> entities = new ArrayList<Unit>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Unit entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    public List<Unit> getEntitiesByGroup(SQLiteDatabase database, Unit.Groups group) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_GROUP + "=?", new String[]{String.valueOf(group.ordinal())}, null, null, getDefaultOrderColumns() + ", " + COLUMN_NAME + " COLLATE NOCASE");
        try {
            List<Unit> entities = new ArrayList<Unit>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Unit entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        super.onCreate(database);
        addPros(database);
    }

    private void addPros(SQLiteDatabase db) {
        int group = Unit.Groups.PRO.ordinal();
        String insertPro = "insert into " + getTableName() + " (" +
                COLUMN_ID + "," +
                COLUMN_GROUP + "," +
                COLUMN_ICON + "," +
                COLUMN_NAME + ")" +
                " values(" +
                "?," +
                +group + "," +
                "?," +
                "?);";
        //db.execSQL(insertPro,new Integer[]{"id","icon","name"});
        db.execSQL(insertPro, new String[]{"123787524", "http://media.steampowered.com/steamcommunity/public/images/avatars/bf/bf6871e8ea21b3e661ee5aaaf66b6f734bbee604_full.jpg", "Dicktator"});
        db.execSQL(insertPro, new String[]{"77415789", "http://media.steampowered.com/steamcommunity/public/images/avatars/84/84cf7a601677122b80e4552645bfb063b15ac20a_full.jpg", "Inmate"});
        db.execSQL(insertPro, new String[]{"114118637", "http://media.steampowered.com/steamcommunity/public/images/avatars/2f/2f9ce4dff5060613f18e8a86ccdbbfab29d6a107_full.jpg", "Nexus"});
        db.execSQL(insertPro, new String[]{"134556694", "http://media.steampowered.com/steamcommunity/public/images/avatars/0d/0d5776eb46f0b64f486439557021c0fc8ad685ce_full.jpg", "`Solo"});
        db.execSQL(insertPro, new String[]{"89782335", "http://media.steampowered.com/steamcommunity/public/images/avatars/e5/e563a766c9f796238a8361eb149d422bbb3e93ef_full.jpg", "RX Dread"});
        db.execSQL(insertPro, new String[]{"85417034", "http://media.steampowered.com/steamcommunity/public/images/avatars/d9/d9c0720d24a197201bf3facd8cb3e08a83f97dd0_full.jpg", "xy-"});
        db.execSQL(insertPro, new String[]{"32995405", "http://media.steampowered.com/steamcommunity/public/images/avatars/a6/a66313f0bdd02b4333c48df95e1d4187e3f2ad01_full.jpg", "@WagaGaming"});
        db.execSQL(insertPro, new String[]{"93712171", "http://media.steampowered.com/steamcommunity/public/images/avatars/a4/a46ced300d634c6913b314241f8a1a55cf5bd22e_full.jpg", "Virtus.pro > NS"});
        db.execSQL(insertPro, new String[]{"89326318", "http://media.steampowered.com/steamcommunity/public/images/avatars/6f/6f4c5c6d4ea047895abc67a063df03b6ebc8b5fa_full.jpg", "Virtus.Pro > LighTofHeaveN"});
        db.execSQL(insertPro, new String[]{"91931211", "http://media.steampowered.com/steamcommunity/public/images/avatars/2e/2e729eca7d22fee09bd55cfc665df20a3f36d46e_full.jpg", "Virtus.pro > KSi"});
        db.execSQL(insertPro, new String[]{"87586992", "http://media.steampowered.com/steamcommunity/public/images/avatars/68/6897a8d0ef5bd64e989e69b59aeba7d101cbbfea_full.jpg", "Virtus Pro > God"});
        db.execSQL(insertPro, new String[]{"107644273", "http://media.steampowered.com/steamcommunity/public/images/avatars/e1/e1d8ecccf007e989f7db1579aaa552aa1b243413_full.jpg", "Virtus.Pro > ARS-ART"});
        db.execSQL(insertPro, new String[]{"86774335", "http://media.steampowered.com/steamcommunity/public/images/avatars/93/9349d18a24cddcb143a3169a61cf913c04ac8e78_full.jpg", "VG.\u5929\u5594.TuTu"});
        db.execSQL(insertPro, new String[]{"90882159", "http://media.steampowered.com/steamcommunity/public/images/avatars/9c/9caf90d2bce820471cfa3a23ca417eb0e32fc4f3_full.jpg", "VG.\u5929\u5594.Super"});
        db.execSQL(insertPro, new String[]{"91698091", "http://media.steampowered.com/steamcommunity/public/images/avatars/4c/4c76064be169987500e4c7e228aabe3e33e1df47_full.jpg", "VG.\u5929\u5594.rOtK"});
        db.execSQL(insertPro, new String[]{"101695162", "http://media.steampowered.com/steamcommunity/public/images/avatars/b7/b70c31875a053fc4905c69309a3103d2249b14db_full.jpg", "VG.\u5929\u5594.Fy"});
        db.execSQL(insertPro, new String[]{"113800818", "http://media.steampowered.com/steamcommunity/public/images/avatars/93/93b1c7f248d016ac63c366097ba07ecba50ccede_full.jpg", "VG.\u5929\u5594.Fenrir"});
        db.execSQL(insertPro, new String[]{"113705693", "http://media.steampowered.com/steamcommunity/public/images/avatars/d1/d1314c5317158b418c3e44d9d39648bb89949f48_full.jpg", "TongFu.Zsmj"});
        db.execSQL(insertPro, new String[]{"90137663", "http://media.steampowered.com/steamcommunity/public/images/avatars/4f/4f5436a80f072cdd7d0cd00bd25ef057b2ef2915_full.jpg", "TongFu.Zhou"});
        db.execSQL(insertPro, new String[]{"111533846", "http://media.steampowered.com/steamcommunity/public/images/avatars/a7/a705f2527edd5d806e7c07a0b9fb8398dce1e5f5_full.jpg", "TongFu.Xtt"});
        db.execSQL(insertPro, new String[]{"100883708", "http://media.steampowered.com/steamcommunity/public/images/avatars/7c/7c299f6db1aa7ac3639a7dfb7c2e78ed30519f64_full.jpg", "TongFu.SanSheng"});
        db.execSQL(insertPro, new String[]{"89157606", "http://media.steampowered.com/steamcommunity/public/images/avatars/e5/e51e280af41c0dbc5d95361a6b6710f4b35c14f8_full.jpg", "TongFu.Mu"});
        db.execSQL(insertPro, new String[]{"89330493", "http://media.steampowered.com/steamcommunity/public/images/avatars/7b/7bd24f6b89334acf44bcba194630c1f5d681bd9c_full.jpg", "Titan.XtiNcT"});
        db.execSQL(insertPro, new String[]{"93616251", "http://media.steampowered.com/steamcommunity/public/images/avatars/c1/c1c1c43c6080b760143ed1c94b2497fcf62f201a_full.jpg", "Titan.Ohaiyo"});
        db.execSQL(insertPro, new String[]{"89249333", "http://media.steampowered.com/steamcommunity/public/images/avatars/c1/c1c1c43c6080b760143ed1c94b2497fcf62f201a_full.jpg", "Titan.Net"});
        db.execSQL(insertPro, new String[]{"91644707", "http://media.steampowered.com/steamcommunity/public/images/avatars/3d/3d076709fb784572f001289e7c20870e2b3adfb4_full.jpg", "Titan.kYxY"});
        db.execSQL(insertPro, new String[]{"93670407", "http://media.steampowered.com/steamcommunity/public/images/avatars/77/771267828e93ec0c49460ec97564d024f0dbc9c8_full.jpg", "Titan.Ice"});
        db.execSQL(insertPro, new String[]{"690740", "http://media.steampowered.com/steamcommunity/public/images/avatars/f2/f21203cf4f904838318ebc6de77970006f33dff2_full.jpg", "SUNSfan"});
        db.execSQL(insertPro, new String[]{"10366616", "http://media.steampowered.com/steamcommunity/public/images/avatars/ee/ee42a838030c527ed0a80e278734c756ae71b8aa_full.jpg", "@SneykingDota2"});
        db.execSQL(insertPro, new String[]{"37147003", "http://media.steampowered.com/steamcommunity/public/images/avatars/ee/ee3997111dc77433d974826fc0480d4fb6669436_full.jpg", "Sheever"});
        db.execSQL(insertPro, new String[]{"19757254", "http://media.steampowered.com/steamcommunity/public/images/avatars/7f/7f33eb9940f32b22b62a736edee2ec907bcaaa37_full.jpg", "sG.SingSing.int"});
        db.execSQL(insertPro, new String[]{"6922000", "http://media.steampowered.com/steamcommunity/public/images/avatars/bc/bca1c239867401466647bd40f78541b356e0b610_full.jpg", "sG.pieliedie.int"});
        db.execSQL(insertPro, new String[]{"43276219", "http://media.steampowered.com/steamcommunity/public/images/avatars/86/86bc0ecfaad510d7dff08de78731c419be7a6575_full.jpg", "sG.EternaLEnVy.int"});
        db.execSQL(insertPro, new String[]{"88719902", "http://media.steampowered.com/steamcommunity/public/images/avatars/51/5153738652b853b17db4b2bc9949ba0dea43a81a_full.jpg", "sG.bOne7.int"});
        db.execSQL(insertPro, new String[]{"40547474", "http://media.steampowered.com/steamcommunity/public/images/avatars/88/882de59e69df6f53da75503e96caf4a7f04e5ac5_full.jpg", "sG.Aui_2000.int"});
        db.execSQL(insertPro, new String[]{"20321748", "http://media.steampowered.com/steamcommunity/public/images/avatars/8d/8d7334dd23ea386bca5a99e94ac14b4652b82e3f_full.jpg", "SexyBamboe"});
        db.execSQL(insertPro, new String[]{"113070925", "http://media.steampowered.com/steamcommunity/public/images/avatars/46/46f79231e1e7d88e1f073cf64d9162891c8e32ac_full.jpg", "Rstars.XDD"});
        db.execSQL(insertPro, new String[]{"108382060", "http://media.steampowered.com/steamcommunity/public/images/avatars/98/98737399177f5f04e41205ed38f192de45e62fa9_full.jpg", "Rstars.Sylar"});
        db.execSQL(insertPro, new String[]{"89399750", "http://media.steampowered.com/steamcommunity/public/images/avatars/8f/8f54a25eb2f1d0a74afbcf068f5364bedc5a9994_full.jpg", "Rstars.QQQ"});
        db.execSQL(insertPro, new String[]{"89230834", "http://media.steampowered.com/steamcommunity/public/images/avatars/21/21f1c804fc5070c175a8521d03550e9f261b64ab_full.jpg", "Rstars.Kingj"});
        db.execSQL(insertPro, new String[]{"108376607", "http://media.steampowered.com/steamcommunity/public/images/avatars/f5/f585cfc8eefe6ea1fda65201272fd6b232ee49fb_full.jpg", "Rstars.Cty"});
        db.execSQL(insertPro, new String[]{"89296893", "http://media.steampowered.com/steamcommunity/public/images/avatars/f8/f8e1454a4156b340d09feb24548d7acafdd6891c_full.jpg", "RSnake.Sag"});
        db.execSQL(insertPro, new String[]{"89246836", "http://media.steampowered.com/steamcommunity/public/images/avatars/9e/9ef36a8f10ba42fe4f0f1f253babc61ec48fd1ba_full.jpg", "Rsnake.Luo"});
        db.execSQL(insertPro, new String[]{"136477860", "http://media.steampowered.com/steamcommunity/public/images/avatars/24/24ce694814cf19efa0a04a0000a5b09b1e8ec8e1_full.jpg", "RSnake.Lin"});
        db.execSQL(insertPro, new String[]{"102020930", "http://media.steampowered.com/steamcommunity/public/images/avatars/48/488d42db7e94ec4d38425a2256bafeaf22a3a223_full.jpg", "RSnake.JoHnNy"});
        db.execSQL(insertPro, new String[]{"99460568", "http://media.steampowered.com/steamcommunity/public/images/avatars/95/952433e47b6fb1552ecc1c752e1063f3d7dbb349_full.jpg", "RSnake.Icy"});
        db.execSQL(insertPro, new String[]{"76600360", "http://media.steampowered.com/steamcommunity/public/images/avatars/79/79b3d78b82986bfb637b3ab811ae197df5dde10e_full.jpg", "RoX.KIS.yol"});
        db.execSQL(insertPro, new String[]{"53178236", "http://media.steampowered.com/steamcommunity/public/images/avatars/5c/5cc808d020d158612e3ab0e825936c3c598c3867_full.jpg", "RoX.KIS.Sedoy"});
        db.execSQL(insertPro, new String[]{"86750262", "http://media.steampowered.com/steamcommunity/public/images/avatars/6c/6cffe963ecaec9b105ef50a13bc30db509fa0913_full.jpg", "RoX.KIS.Scandal"});
        db.execSQL(insertPro, new String[]{"93614932", "http://media.steampowered.com/steamcommunity/public/images/avatars/22/225befde2629af83f9a77aef912ede642d691777_full.jpg", "RoX.KIS.Nexus"});
        db.execSQL(insertPro, new String[]{"96196828", "http://media.steampowered.com/steamcommunity/public/images/avatars/eb/eb89dcaad32cc26b3be6e63177faffc06c494f82_full.jpg", "RoX.KIS.BzzIsPerfect"});
        db.execSQL(insertPro, new String[]{"66296404", "http://media.steampowered.com/steamcommunity/public/images/avatars/b8/b882cb382f7f721843879ad7a7794b7dd2504650_full.jpg", "Purge"});
        db.execSQL(insertPro, new String[]{"31078647", "http://media.steampowered.com/steamcommunity/public/images/avatars/b7/b7bee394bfd3d4753d85ee808d2483c5f62e620a_full.jpg", "paS"});
        db.execSQL(insertPro, new String[]{"86762037", "http://media.steampowered.com/steamcommunity/public/images/avatars/1f/1ff2d55c8c5e2866845bb0ccec9313e865d0302e_full.jpg", "Orange.WinteR"});
        db.execSQL(insertPro, new String[]{"91143798", "http://media.steampowered.com/steamcommunity/public/images/avatars/76/76a60f1b96cde481d223b75ad21b6fce2f5f589f_full.jpg", "Orange.TFG"});
        db.execSQL(insertPro, new String[]{"121052479", "http://media.steampowered.com/steamcommunity/public/images/avatars/75/759fbd4995719026bd36e2286ffdcf76f075a002_full.jpg", "Orange.NWP"});
        db.execSQL(insertPro, new String[]{"89268488", "http://media.steampowered.com/steamcommunity/public/images/avatars/1b/1b5a4f54ff027d4ff75532ca235dc4aa3c5b991a_full.jpg", "Orange.Ling"});
        db.execSQL(insertPro, new String[]{"89625472", "http://media.steampowered.com/steamcommunity/public/images/avatars/ca/cac94b77c665ed593ffef6861d9684659a471994_full.jpg", "Na`Vi.XBOCT"});
        db.execSQL(insertPro, new String[]{"87278757", "http://media.steampowered.com/steamcommunity/public/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_full.jpg", "Na`Vi.Puppey"});
        db.execSQL(insertPro, new String[]{"82262664", "http://media.steampowered.com/steamcommunity/public/images/avatars/03/03b5a0d0244145b6d748f1b34c6d824151547207_full.jpg", "Na`Vi.KuroKy"});
        db.execSQL(insertPro, new String[]{"86723143", "http://media.steampowered.com/steamcommunity/public/images/avatars/93/930363412302d552cef976731b2cdaa10c6c78d9_full.jpg", "Na'Vi.Funn1k"});
        db.execSQL(insertPro, new String[]{"70388657", "http://media.steampowered.com/steamcommunity/public/images/avatars/1a/1a649b26114458a06711ff80592c6beaaf40f12b_full.jpg", "Na`Vi.Dendi"});
        db.execSQL(insertPro, new String[]{"96708830", "http://media.steampowered.com/steamcommunity/public/images/avatars/3e/3e19b5c3dbc088722e20530ecea778435de78499_full.jpg", "MUFC.FzFz"});
        db.execSQL(insertPro, new String[]{"80576254", "http://media.steampowered.com/steamcommunity/public/images/avatars/e4/e4db920d15114519ad9db2efa0fd341d6f36c746_full.jpg", "MUFC.dabeliuteef"});
        db.execSQL(insertPro, new String[]{"96309508", "http://media.steampowered.com/steamcommunity/public/images/avatars/e2/e2437a369c00d64ec31b28abce27580a10dd9cd6_full.jpg", "Mski.Julz"});
        db.execSQL(insertPro, new String[]{"86772934", "http://media.steampowered.com/steamcommunity/public/images/avatars/e2/e2437a369c00d64ec31b28abce27580a10dd9cd6_full.jpg", "Mski.JessieVash"});
        db.execSQL(insertPro, new String[]{"4281729", "http://media.steampowered.com/steamcommunity/public/images/avatars/90/90bae609afcffe66d66c68f43a6e9cf26a4f17c4_full.jpg", "mouz syndereN"});
        db.execSQL(insertPro, new String[]{"45614257", "http://media.steampowered.com/steamcommunity/public/images/avatars/da/da786dfdd1ce6eb659de671bebf8bdbd73c8e182_full.jpg", "MiTH.TnK"});
        db.execSQL(insertPro, new String[]{"96886397", "http://media.steampowered.com/steamcommunity/public/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_full.jpg", "MiTH.r5r5"});
        db.execSQL(insertPro, new String[]{"85485252", "http://media.steampowered.com/steamcommunity/public/images/avatars/f9/f96ebbc7c7ad7307ce6a67299b4036a34473ea90_full.jpg", "MiTH.MAWMAW"});
        db.execSQL(insertPro, new String[]{"91191397", "http://media.steampowered.com/steamcommunity/public/images/avatars/8f/8ff8c9a344ebe49878e812d5d44bcb69e326f4e0_full.jpg", "MiTH.LaKelz"});
        db.execSQL(insertPro, new String[]{"91191651", "http://media.steampowered.com/steamcommunity/public/images/avatars/f4/f41a8c3a4f801bd1ad9a1e353dd351a55ef95633_full.jpg", "MiTH.aabBAA"});
        db.execSQL(insertPro, new String[]{"34128052", "http://media.steampowered.com/steamcommunity/public/images/avatars/97/976a16e3541fcf12cec676b2b51cacab2ce95aa8_full.jpg", "Mini"});
        db.execSQL(insertPro, new String[]{"67760037", "http://media.steampowered.com/steamcommunity/public/images/avatars/74/74771c286200f7fadbd6c26c82b5ba9e43ff1dad_full.jpg", "Merlini"});
        db.execSQL(insertPro, new String[]{"59457413", "http://media.steampowered.com/steamcommunity/public/images/avatars/f2/f21c62c499a4f034cad1e1e1cf0e5d17eecfee52_full.jpg", "@Maelk"});
        db.execSQL(insertPro, new String[]{"82202389", "http://media.steampowered.com/steamcommunity/public/images/avatars/c2/c25481c503a79fbb0c052b98bd8a3cae4aff211d_full.jpg", "@LuminousInverse"});
        db.execSQL(insertPro, new String[]{"38515149", "http://media.steampowered.com/steamcommunity/public/images/avatars/71/7115afd8a242b68bc7ec81235f0a190969cc674e_full.jpg", "Liquid.Waytosexy"});
        db.execSQL(insertPro, new String[]{"67601693", "http://media.steampowered.com/steamcommunity/public/images/avatars/27/2728d75a08a4f2ee5a99c5314669e0c66f12bdc9_full.jpg", "Liquid.TC"});
        db.execSQL(insertPro, new String[]{"86738694", "http://media.steampowered.com/steamcommunity/public/images/avatars/fb/fb0c53212b43472bf3c70fa77f10d9ae2eb0ffcc_full.jpg", "Liquid.qojqva"});
        db.execSQL(insertPro, new String[]{"41470731", "http://media.steampowered.com/steamcommunity/public/images/avatars/27/2728d75a08a4f2ee5a99c5314669e0c66f12bdc9_full.jpg", "Liquid.FLUFFNSTUFF"});
        db.execSQL(insertPro, new String[]{"30237211", "http://media.steampowered.com/steamcommunity/public/images/avatars/fb/fb0c53212b43472bf3c70fa77f10d9ae2eb0ffcc_full.jpg", "Liquid.BuLba"});
        db.execSQL(insertPro, new String[]{"82630959", "http://media.steampowered.com/steamcommunity/public/images/avatars/40/40e02167bea74b96a10e59a41a2001a115942226_full.jpg", "LGD.xFreedom.Int"});
        db.execSQL(insertPro, new String[]{"123854991", "http://media.steampowered.com/steamcommunity/public/images/avatars/ae/ae4795d9c893cc69f08c2426dc8380e072466eec_full.jpg", "LGD.taobao.xiaotuji"});
        db.execSQL(insertPro, new String[]{"98887913", "http://media.steampowered.com/steamcommunity/public/images/avatars/87/876e1d00d06f12767ea6917df05dcb292ab75b83_full.jpg", "LGD.Taobao.xiao8"});
        db.execSQL(insertPro, new String[]{"114239371", "http://media.steampowered.com/steamcommunity/public/images/avatars/a1/a11a8382da993da738ad73efd01bc309bae68dd1_full.jpg", "LGD.taobao.ddc"});
        db.execSQL(insertPro, new String[]{"89371588", "http://media.steampowered.com/steamcommunity/public/images/avatars/f5/f583ba295ba8310cf46c95d6c701d5e55aeab451_full.jpg", "LGD.taobao.DD"});
        db.execSQL(insertPro, new String[]{"87285329", "http://media.steampowered.com/steamcommunity/public/images/avatars/bb/bbf11f4ab9e0b3887b7d4237d198a24cc565c6a6_full.jpg", "LGD.Pajkatt.Int"});
        db.execSQL(insertPro, new String[]{"87382579", "http://media.steampowered.com/steamcommunity/public/images/avatars/6d/6d034fead3c6d1e6c16cd8b49846c956cb06cb31_full.jpg", "LGD.MiSeRY.Int"});
        db.execSQL(insertPro, new String[]{"31818853", "http://media.steampowered.com/steamcommunity/public/images/avatars/cc/cc562418290579bbd34f497855cb08e501d6cfaa_full.jpg", "LGD.Brax.Int"});
        db.execSQL(insertPro, new String[]{"21289303", "http://media.steampowered.com/steamcommunity/public/images/avatars/70/70028b94e189632675936355e5ffbbf0372f6953_full.jpg", "LGD.Black^.Int"});
        db.execSQL(insertPro, new String[]{"145875", "http://media.steampowered.com/steamcommunity/public/images/avatars/c6/c6f334d797ae3021a10325630bd1b7e4546725b2_full.jpg", "LD"});
        db.execSQL(insertPro, new String[]{"33366656", "http://media.steampowered.com/steamcommunity/public/images/avatars/70/7077107bfd5039274795e25921a12aa34d46b560_full.jpg", "joinDOTA|Tob|Wan!"});
        db.execSQL(insertPro, new String[]{"26771994", "http://media.steampowered.com/steamcommunity/public/images/avatars/54/5498863c38bde184c59c36e551982623355e651c_full.jpg", "@jeraxai"});
        db.execSQL(insertPro, new String[]{"86715129", "http://media.steampowered.com/steamcommunity/public/images/avatars/27/2728d75a08a4f2ee5a99c5314669e0c66f12bdc9_full.jpg", "ixmike88"});
        db.execSQL(insertPro, new String[]{"3246092", "http://media.steampowered.com/steamcommunity/public/images/avatars/c2/c2853e1e1b885f7fdbb02c15da9dba605c2ca770_full.jpg", "@inphinity123"});
        db.execSQL(insertPro, new String[]{"90045009", "http://media.steampowered.com/steamcommunity/public/images/avatars/9c/9c6de366b5dc2fcd4c3dcc259d9ff57901805808_full.jpg", "iG.YYF"});
        db.execSQL(insertPro, new String[]{"88508515", "http://media.steampowered.com/steamcommunity/public/images/avatars/79/794a56537b8ba4298baa5d6d17941170482b6918_full.jpg", "iG.Hao"});
        db.execSQL(insertPro, new String[]{"88585077", "http://media.steampowered.com/steamcommunity/public/images/avatars/00/00e4e33a5579084cea98498b08bd169d75eb93e0_full.jpg", "iG.Ferrari_430"});
        db.execSQL(insertPro, new String[]{"82327674", "http://media.steampowered.com/steamcommunity/public/images/avatars/03/03c9150811545a7fa5ec067ec3980caa83b11134_full.jpg", "iG.Faith"});
        db.execSQL(insertPro, new String[]{"89217927", "http://media.steampowered.com/steamcommunity/public/images/avatars/05/0586284bfb5ff5372fdd80cf630cdd32c5bbe16f_full.jpg", "iG.Banana"});
        db.execSQL(insertPro, new String[]{"76995948", "http://media.steampowered.com/steamcommunity/public/images/avatars/fd/fd17c7c6b3721c020a6deda96ecd3b846b9c567e_full.jpg", "GoDZ"});
        db.execSQL(insertPro, new String[]{"89137399", "http://media.steampowered.com/steamcommunity/public/images/avatars/66/667680c15f14adce1a08a7847b6b7216e46abbf0_full.jpg", "@Goblak"});
        db.execSQL(insertPro, new String[]{"90892194", "http://media.steampowered.com/steamcommunity/public/images/avatars/97/977fb67a5864c053eb04bf3833352e76051535ca_full.jpg", "GC|LONGDD"});
        db.execSQL(insertPro, new String[]{"8712306", "http://media.steampowered.com/steamcommunity/public/images/avatars/a0/a05950846b505eda2dda2afdbad4ef18581ee4f5_full.jpg", "Fnatic.Trixi"});
        db.execSQL(insertPro, new String[]{"19672354", "http://media.steampowered.com/steamcommunity/public/images/avatars/63/632036cbd3c0d755f65c07ef25f20f27ee56265c_full.jpg", "Fnatic.N0tail"});
        db.execSQL(insertPro, new String[]{"21265941", "http://media.steampowered.com/steamcommunity/public/images/avatars/f1/f11eae06ba16370c9545661ab5d79757bc3d7361_full.jpg", "Fnatic.H4nn1"});
        db.execSQL(insertPro, new String[]{"94155156", "http://media.steampowered.com/steamcommunity/public/images/avatars/d8/d84b44e323df52a39d066b5438367e9507a4791d_full.jpg", "Fnatic.Fly"});
        db.execSQL(insertPro, new String[]{"100317750", "http://media.steampowered.com/steamcommunity/public/images/avatars/84/84f5d08dac4cff9cb17e0634f8b4871d570146ec_full.jpg", "Fnatic.Era"});
        db.execSQL(insertPro, new String[]{"16769223", "http://media.steampowered.com/steamcommunity/public/images/avatars/c7/c724e9ce852ba9fae9cc5fbeb35cc84f05a5b14e_full.jpg", "FlipQ.Sockshka"});
        db.execSQL(insertPro, new String[]{"74432222", "http://media.steampowered.com/steamcommunity/public/images/avatars/9a/9a33f212c95e2636312a2d3c3670405904875730_full.jpg", "FlipQ.miGGel"});
        db.execSQL(insertPro, new String[]{"85783343", "http://media.steampowered.com/steamcommunity/public/images/avatars/2f/2f44386e2e6b49bf02e40b188e3055283f906bda_full.jpg", "FlipQ.MaNia"});
        db.execSQL(insertPro, new String[]{"88271237", "http://media.steampowered.com/steamcommunity/public/images/avatars/75/751161ed3e1eab5f9311ff92ba36c8b5dbc58218_full.jpg", "FlipQ.7ckngMad"});
        db.execSQL(insertPro, new String[]{"92164306", "http://media.steampowered.com/steamcommunity/public/images/avatars/d4/d4efd807625559813e7ada1f7e6ff88f2bd1993b_full.jpg", "FD.Scripted"});
        db.execSQL(insertPro, new String[]{"76904792", "http://media.steampowered.com/steamcommunity/public/images/avatars/72/72f78b4c8cc1f62323f8a33f6d53e27db57c2252_full.jpg", "FD.Poloson"});
        db.execSQL(insertPro, new String[]{"131543130", "http://media.steampowered.com/steamcommunity/public/images/avatars/a1/a12876a561c7100e06fba51e4bca4150c8cf52d7_full.jpg", "FD.Meracle"});
        db.execSQL(insertPro, new String[]{"86741690", "http://media.steampowered.com/steamcommunity/public/images/avatars/0e/0e5b5ccecb4bc1be40536b5eb6fb79430d210e0c_full.jpg", "FD.Lubby"});
        db.execSQL(insertPro, new String[]{"75390186", "http://media.steampowered.com/steamcommunity/public/images/avatars/8f/8f5b1b4c88a57d3ea510a3e60cb810928bd540ec_full.jpg", "FD.Hana"});
        db.execSQL(insertPro, new String[]{"86799300", "http://media.steampowered.com/steamcommunity/public/images/avatars/e5/e5eaf92367dfc811684abf9f6978a4e8583e1e51_full.jpg", "FATA-"});
        db.execSQL(insertPro, new String[]{"11550182", "http://media.steampowered.com/steamcommunity/public/images/avatars/e4/e4f5dd5d6c3cb0741e2c773c03f034e734be1772_full.jpg", "Empire.VANSKOR"});
        db.execSQL(insertPro, new String[]{"89269794", "http://media.steampowered.com/steamcommunity/public/images/avatars/e4/e4f5dd5d6c3cb0741e2c773c03f034e734be1772_full.jpg", "Empire.Silent"});
        db.execSQL(insertPro, new String[]{"86802844", "http://media.steampowered.com/steamcommunity/public/images/avatars/e4/e4f5dd5d6c3cb0741e2c773c03f034e734be1772_full.jpg", "Empire.Mag"});
        db.execSQL(insertPro, new String[]{"91064780", "http://media.steampowered.com/steamcommunity/public/images/avatars/e4/e4f5dd5d6c3cb0741e2c773c03f034e734be1772_full.jpg", "Empire.ALWAYSWANNAFLY"});
        db.execSQL(insertPro, new String[]{"87276347", "http://media.steampowered.com/steamcommunity/public/images/avatars/f8/f8de58eb18a0cad87270ef1d1250c574498577fc_full.jpg", "EG.Universe"});
        db.execSQL(insertPro, new String[]{"86726887", "http://media.steampowered.com/steamcommunity/public/images/avatars/c7/c758baaa4100b7e5f585b3159b2f36ecc773f1a9_full.jpg", "EG.MSS"});
        db.execSQL(insertPro, new String[]{"87360406", "http://media.steampowered.com/steamcommunity/public/images/avatars/8b/8be566906f58d4c3c9e443a6cfef6fa1c42121eb_full.jpg", "EG.Jeyo"});
        db.execSQL(insertPro, new String[]{"5448108", "http://media.steampowered.com/steamcommunity/public/images/avatars/71/7168b32d553334e7b6dee94f76bd350080d7f201_full.jpg", "EG.Fogged"});
        db.execSQL(insertPro, new String[]{"87177591", "http://media.steampowered.com/steamcommunity/public/images/avatars/82/823b045a5da377e827fc52ce5350942b78476d58_full.jpg", "EG.Fear"});
        db.execSQL(insertPro, new String[]{"70478881", "http://media.steampowered.com/steamcommunity/public/images/avatars/24/24233c3af44bdb83ed0df3c8505523fae04301fd_full.jpg", "Draskyl"});
        db.execSQL(insertPro, new String[]{"89871557", "http://media.steampowered.com/steamcommunity/public/images/avatars/d6/d6214ff6edefd6a5b1202eb3817a1c5601a33152_full.jpg", "DK.Mushi"});
        db.execSQL(insertPro, new String[]{"89407113", "http://media.steampowered.com/steamcommunity/public/images/avatars/f6/f622c5095e0a43e9480b6840897832183195bd2b_full.jpg", "DK.MMY"});
        db.execSQL(insertPro, new String[]{"89423756", "http://media.steampowered.com/steamcommunity/public/images/avatars/71/7139f2c35be77813c7acaf31396241939e475f13_full.jpg", "DK.LaNm"});
        db.execSQL(insertPro, new String[]{"84772440", "http://media.steampowered.com/steamcommunity/public/images/avatars/f5/f5089366eaf6d9bf64307595e19da654dc679030_full.jpg", "DK.iceiceice"});
        db.execSQL(insertPro, new String[]{"90892734", "http://media.steampowered.com/steamcommunity/public/images/avatars/8b/8b630afad30fead6c5f2b021cacca941ed06615c_full.jpg", "DK.BurNIng"});
        db.execSQL(insertPro, new String[]{"1185644", "http://media.steampowered.com/steamcommunity/public/images/avatars/44/440a284cb16383e3525ed3c8689c3f9fdf73ed28_full.jpg", "Dignitas.Korok"});
        db.execSQL(insertPro, new String[]{"29904110", "http://media.steampowered.com/steamcommunity/public/images/avatars/77/77815904fc01d5a5106ac6e15dbee55b03ff8f03_full.jpg", "Dignitas.bLeek"});
        db.execSQL(insertPro, new String[]{"44463405", "http://media.steampowered.com/steamcommunity/public/images/avatars/26/26c52b8af4195235c8b791097fb34e64c8b631bb_full.jpg", "Dignitas.Bdiz"});
        db.execSQL(insertPro, new String[]{"85805514", "http://media.steampowered.com/steamcommunity/public/images/avatars/88/882c4ce8101ad0b7af91e0c6805fd061215927d1_full.jpg", "@DeMoN"});
        db.execSQL(insertPro, new String[]{"5390881", "http://media.steampowered.com/steamcommunity/public/images/avatars/4d/4dae3e0726de7edd5ac675a36bf54ef45590327c_full.jpg", "Cyborgmatt"});
        db.execSQL(insertPro, new String[]{"85375207", "http://media.steampowered.com/steamcommunity/public/images/avatars/2a/2a0b517d1b0154dfd8fc797e876297161f20e52e_full.jpg", "ComeWithMe"});
        db.execSQL(insertPro, new String[]{"88553213", "http://media.steampowered.com/steamcommunity/public/images/avatars/18/18d3cb1610ccab455d09a059098bc7e6e781ec6e_full.jpg", "ChuaN"});
        db.execSQL(insertPro, new String[]{"78757666", "http://media.steampowered.com/steamcommunity/public/images/avatars/a1/a19237972316acdfbef0bfc1b80109d0c294c30c_full.jpg", "Bruno"});
        db.execSQL(insertPro, new String[]{"39853004", "http://media.steampowered.com/steamcommunity/public/images/avatars/6f/6f4831f81e44ca1d600e6848004c38ab3ea03e09_full.jpg", "Ayesee"});
        db.execSQL(insertPro, new String[]{"86745912", "http://media.steampowered.com/steamcommunity/public/images/avatars/0a/0a81b9696d8a4fcf701bd2bd7d767fede9538418_full.jpg", "Arteezy"});
        db.execSQL(insertPro, new String[]{"41231571", "http://media.steampowered.com/steamcommunity/public/images/avatars/66/66f02857a4ee94ab62fde38c99faa74dbc8267b3_full.jpg", "[A] s4"});
        db.execSQL(insertPro, new String[]{"101495620", "http://media.steampowered.com/steamcommunity/public/images/avatars/66/66f02857a4ee94ab62fde38c99faa74dbc8267b3_full.jpg", "[A]Loda"});
        db.execSQL(insertPro, new String[]{"41288955", "http://media.steampowered.com/steamcommunity/public/images/avatars/66/66f02857a4ee94ab62fde38c99faa74dbc8267b3_full.jpg", "[A]kke"});
        db.execSQL(insertPro, new String[]{"3916428", "http://media.steampowered.com/steamcommunity/public/images/avatars/10/10ecf2e0c8bf3a0fc0618b6c8c6143bfdb3b2969_full.jpg", "[A]EGM"});
        db.execSQL(insertPro, new String[]{"76482434", "http://media.steampowered.com/steamcommunity/public/images/avatars/66/66f02857a4ee94ab62fde38c99faa74dbc8267b3_full.jpg", "[A]dmiralBulldog"});
        db.execSQL(insertPro, new String[]{"35960687", "http://media.steampowered.com/steamcommunity/public/images/avatars/d5/d5289157855c51e2d3f9507291a2faa815aeeda1_full.jpg", "2GD"});
        db.execSQL(insertPro, new String[]{"87196890", "http://media.steampowered.com/steamcommunity/public/images/avatars/f8/f8de58eb18a0cad87270ef1d1250c574498577fc_full.jpg", "1437"});
    }
}
