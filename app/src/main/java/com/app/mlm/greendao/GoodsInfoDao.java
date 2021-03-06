package com.app.mlm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.app.mlm.bean.GoodsInfo;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GOODS_INFO".
*/
public class GoodsInfoDao extends AbstractDao<GoodsInfo, Void> {

    public static final String TABLENAME = "GOODS_INFO";

    public GoodsInfoDao(DaoConfig config) {
        super(config);
    }


    public GoodsInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GOODS_INFO\" (" + //
                "\"ID\" INTEGER," + // 0: id
                "\"MDSE_ID\" INTEGER NOT NULL ," + // 1: mdseId
                "\"MDSE_PRICE\" REAL NOT NULL ," + // 2: mdsePrice
                "\"MDSE_BRAND\" TEXT," + // 3: mdseBrand
                "\"MDSE_PACK\" TEXT," + // 4: mdsePack
                "\"MERCHANT_TYPE\" TEXT," + // 5: merchantType
                "\"MDSE_NAME\" TEXT," + // 6: mdseName
                "\"MDSE_URL\" TEXT," + // 7: mdseUrl
                "\"CL_CODE\" TEXT," + // 8: clCode
                "\"SHOP_CAR_NUM\" INTEGER NOT NULL ," + // 9: shopCarNum
                "\"VM_CODE\" TEXT," + // 10: vmCode
                "\"VM_ID\" INTEGER," + // 11: vmId
                "\"CL_ID\" INTEGER," + // 12: clId
                "\"VM_CLAYERS\" INTEGER," + // 13: vmClayers
                "\"CLONG\" REAL NOT NULL ," + // 14: clong
                "\"CWIDTH\" REAL NOT NULL ," + // 15: cwidth
                "\"CHEIGHT\" REAL NOT NULL ," + // 16: cheight
                "\"REAL_PRICE\" REAL NOT NULL ," + // 17: realPrice
                "\"CL_CAPACITY\" INTEGER," + // 18: clCapacity
                "\"CLC_CAPACITY\" INTEGER," + // 19: clcCapacity
                "\"THRESHOLD\" TEXT," + // 20: threshold
                "\"REPLENISH\" INTEGER," + // 21: Replenish
                "\"CHANNEL_TYPE\" INTEGER," + // 22: channelType
                "\"PRIDUCT_BATCH\" TEXT," + // 23: priductBatch
                "\"MERCHANT_URL\" TEXT," + // 24: merchantUrl
                "\"ACTIVITY_PRICE\" REAL NOT NULL );"); // 25: activityPrice
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GOODS_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GoodsInfo entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMdseId());
        stmt.bindDouble(3, entity.getMdsePrice());

        String mdseBrand = entity.getMdseBrand();
        if (mdseBrand != null) {
            stmt.bindString(4, mdseBrand);
        }

        String mdsePack = entity.getMdsePack();
        if (mdsePack != null) {
            stmt.bindString(5, mdsePack);
        }

        String merchantType = entity.getMerchantType();
        if (merchantType != null) {
            stmt.bindString(6, merchantType);
        }

        String mdseName = entity.getMdseName();
        if (mdseName != null) {
            stmt.bindString(7, mdseName);
        }

        String mdseUrl = entity.getMdseUrl();
        if (mdseUrl != null) {
            stmt.bindString(8, mdseUrl);
        }

        String clCode = entity.getClCode();
        if (clCode != null) {
            stmt.bindString(9, clCode);
        }
        stmt.bindLong(10, entity.getShopCarNum());

        String vmCode = entity.getVmCode();
        if (vmCode != null) {
            stmt.bindString(11, vmCode);
        }

        Integer vmId = entity.getVmId();
        if (vmId != null) {
            stmt.bindLong(12, vmId);
        }

        Integer clId = entity.getClId();
        if (clId != null) {
            stmt.bindLong(13, clId);
        }

        Integer vmClayers = entity.getVmClayers();
        if (vmClayers != null) {
            stmt.bindLong(14, vmClayers);
        }
        stmt.bindDouble(15, entity.getClong());
        stmt.bindDouble(16, entity.getCwidth());
        stmt.bindDouble(17, entity.getCheight());
        stmt.bindDouble(18, entity.getRealPrice());

        Integer clCapacity = entity.getClCapacity();
        if (clCapacity != null) {
            stmt.bindLong(19, clCapacity);
        }

        Integer clcCapacity = entity.getClcCapacity();
        if (clcCapacity != null) {
            stmt.bindLong(20, clcCapacity);
        }

        String threshold = entity.getThreshold();
        if (threshold != null) {
            stmt.bindString(21, threshold);
        }

        Integer Replenish = entity.getReplenish();
        if (Replenish != null) {
            stmt.bindLong(22, Replenish);
        }

        Integer channelType = entity.getChannelType();
        if (channelType != null) {
            stmt.bindLong(23, channelType);
        }

        String priductBatch = entity.getPriductBatch();
        if (priductBatch != null) {
            stmt.bindString(24, priductBatch);
        }

        String merchantUrl = entity.getMerchantUrl();
        if (merchantUrl != null) {
            stmt.bindString(25, merchantUrl);
        }
        stmt.bindDouble(26, entity.getActivityPrice());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GoodsInfo entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMdseId());
        stmt.bindDouble(3, entity.getMdsePrice());

        String mdseBrand = entity.getMdseBrand();
        if (mdseBrand != null) {
            stmt.bindString(4, mdseBrand);
        }

        String mdsePack = entity.getMdsePack();
        if (mdsePack != null) {
            stmt.bindString(5, mdsePack);
        }

        String merchantType = entity.getMerchantType();
        if (merchantType != null) {
            stmt.bindString(6, merchantType);
        }

        String mdseName = entity.getMdseName();
        if (mdseName != null) {
            stmt.bindString(7, mdseName);
        }

        String mdseUrl = entity.getMdseUrl();
        if (mdseUrl != null) {
            stmt.bindString(8, mdseUrl);
        }

        String clCode = entity.getClCode();
        if (clCode != null) {
            stmt.bindString(9, clCode);
        }
        stmt.bindLong(10, entity.getShopCarNum());

        String vmCode = entity.getVmCode();
        if (vmCode != null) {
            stmt.bindString(11, vmCode);
        }

        Integer vmId = entity.getVmId();
        if (vmId != null) {
            stmt.bindLong(12, vmId);
        }

        Integer clId = entity.getClId();
        if (clId != null) {
            stmt.bindLong(13, clId);
        }

        Integer vmClayers = entity.getVmClayers();
        if (vmClayers != null) {
            stmt.bindLong(14, vmClayers);
        }
        stmt.bindDouble(15, entity.getClong());
        stmt.bindDouble(16, entity.getCwidth());
        stmt.bindDouble(17, entity.getCheight());
        stmt.bindDouble(18, entity.getRealPrice());

        Integer clCapacity = entity.getClCapacity();
        if (clCapacity != null) {
            stmt.bindLong(19, clCapacity);
        }

        Integer clcCapacity = entity.getClcCapacity();
        if (clcCapacity != null) {
            stmt.bindLong(20, clcCapacity);
        }

        String threshold = entity.getThreshold();
        if (threshold != null) {
            stmt.bindString(21, threshold);
        }

        Integer Replenish = entity.getReplenish();
        if (Replenish != null) {
            stmt.bindLong(22, Replenish);
        }

        Integer channelType = entity.getChannelType();
        if (channelType != null) {
            stmt.bindLong(23, channelType);
        }

        String priductBatch = entity.getPriductBatch();
        if (priductBatch != null) {
            stmt.bindString(24, priductBatch);
        }

        String merchantUrl = entity.getMerchantUrl();
        if (merchantUrl != null) {
            stmt.bindString(25, merchantUrl);
        }
        stmt.bindDouble(26, entity.getActivityPrice());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override
    public GoodsInfo readEntity(Cursor cursor, int offset) {
        GoodsInfo entity = new GoodsInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // mdseId
            cursor.getDouble(offset + 2), // mdsePrice
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mdseBrand
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mdsePack
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // merchantType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mdseName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // mdseUrl
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // clCode
            cursor.getInt(offset + 9), // shopCarNum
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // vmCode
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // vmId
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // clId
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // vmClayers
            cursor.getDouble(offset + 14), // clong
            cursor.getDouble(offset + 15), // cwidth
            cursor.getDouble(offset + 16), // cheight
            cursor.getDouble(offset + 17), // realPrice
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // clCapacity
            cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19), // clcCapacity
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // threshold
            cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21), // Replenish
            cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22), // channelType
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // priductBatch
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // merchantUrl
            cursor.getDouble(offset + 25) // activityPrice
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, GoodsInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMdseId(cursor.getInt(offset + 1));
        entity.setMdsePrice(cursor.getDouble(offset + 2));
        entity.setMdseBrand(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMdsePack(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMerchantType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMdseName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMdseUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setClCode(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setShopCarNum(cursor.getInt(offset + 9));
        entity.setVmCode(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setVmId(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setClId(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setVmClayers(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setClong(cursor.getDouble(offset + 14));
        entity.setCwidth(cursor.getDouble(offset + 15));
        entity.setCheight(cursor.getDouble(offset + 16));
        entity.setRealPrice(cursor.getDouble(offset + 17));
        entity.setClCapacity(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setClcCapacity(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
        entity.setThreshold(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setReplenish(cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21));
        entity.setChannelType(cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22));
        entity.setPriductBatch(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setMerchantUrl(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setActivityPrice(cursor.getDouble(offset + 25));
     }

    @Override
    protected final Void updateKeyAfterInsert(GoodsInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(GoodsInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(GoodsInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Properties of entity GoodsInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", false, "ID");
        public final static Property MdseId = new Property(1, int.class, "mdseId", false, "MDSE_ID");
        public final static Property MdsePrice = new Property(2, double.class, "mdsePrice", false, "MDSE_PRICE");
        public final static Property MdseBrand = new Property(3, String.class, "mdseBrand", false, "MDSE_BRAND");
        public final static Property MdsePack = new Property(4, String.class, "mdsePack", false, "MDSE_PACK");
        public final static Property MerchantType = new Property(5, String.class, "merchantType", false, "MERCHANT_TYPE");
        public final static Property MdseName = new Property(6, String.class, "mdseName", false, "MDSE_NAME");
        public final static Property MdseUrl = new Property(7, String.class, "mdseUrl", false, "MDSE_URL");
        public final static Property ClCode = new Property(8, String.class, "clCode", false, "CL_CODE");
        public final static Property ShopCarNum = new Property(9, int.class, "shopCarNum", false, "SHOP_CAR_NUM");
        public final static Property VmCode = new Property(10, String.class, "vmCode", false, "VM_CODE");
        public final static Property VmId = new Property(11, Integer.class, "vmId", false, "VM_ID");
        public final static Property ClId = new Property(12, Integer.class, "clId", false, "CL_ID");
        public final static Property VmClayers = new Property(13, Integer.class, "vmClayers", false, "VM_CLAYERS");
        public final static Property Clong = new Property(14, double.class, "clong", false, "CLONG");
        public final static Property Cwidth = new Property(15, double.class, "cwidth", false, "CWIDTH");
        public final static Property Cheight = new Property(16, double.class, "cheight", false, "CHEIGHT");
        public final static Property RealPrice = new Property(17, double.class, "realPrice", false, "REAL_PRICE");
        public final static Property ClCapacity = new Property(18, Integer.class, "clCapacity", false, "CL_CAPACITY");
        public final static Property ClcCapacity = new Property(19, Integer.class, "clcCapacity", false, "CLC_CAPACITY");
        public final static Property Threshold = new Property(20, String.class, "threshold", false, "THRESHOLD");
        public final static Property Replenish = new Property(21, Integer.class, "Replenish", false, "REPLENISH");
        public final static Property ChannelType = new Property(22, Integer.class, "channelType", false, "CHANNEL_TYPE");
        public final static Property PriductBatch = new Property(23, String.class, "priductBatch", false, "PRIDUCT_BATCH");
        public final static Property MerchantUrl = new Property(24, String.class, "merchantUrl", false, "MERCHANT_URL");
        public final static Property ActivityPrice = new Property(25, double.class, "activityPrice", false, "ACTIVITY_PRICE");
    }
    
}
