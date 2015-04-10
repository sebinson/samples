package net.sebinson.common.configration.cache.zookeeper;

import java.util.ResourceBundle;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sebinson.common.configration.cache.type.ICacheType;
import net.sebinson.common.configration.cache.zookeeper.log.CacheLog;

import com.google.gson.Gson;

/**
 * 
 * @author C
 *
 */
public class CacheThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CacheThread.class);

    @Override
    public void run() {
        try {
            ZkClient zk = new ZkClient(CacheHelper.CACHE_ZK_SERVER_DEFAULT);
            zk.setZkSerializer(new CacheSerializer());
            ResourceBundle rb = ResourceBundle.getBundle(CacheHelper.CACHE_CONFIG_FILE_DEFAULT);
            CacheEnums[] c = CacheEnums.values();
            String key = null;
            String dataPath = null;
            for (CacheEnums cacheEnums : c) {
                key = cacheEnums.name();
                if (rb.containsKey(key)) {
                    CacheFactory.put(key, (ICacheType) Class.forName(rb.getString(key)).newInstance());
                    dataPath = this.getDataPathByKey(key);
                    this.reload(dataPath, zk.readData(dataPath, true));
                    zk.subscribeDataChanges(dataPath, new IZkDataListener() {
                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            return;
                        }

                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {
                            CacheThread.this.reload(dataPath, data);
                            System.out.println("本地数据已重新加载，数据:" + new Gson().toJson(data));
                        }
                    });
                }
            }
        } catch (Exception e) {
            logger.error("初始化本地缓存异常", e);
        }
    }

    private String getDataPathByKey(String key) {
        return CacheHelper.CACHE_ROOT_DEFAULT + "/" + key;
    }

    private String getKeyByDataPath(String dataPath) {
        return dataPath.substring(15);
    }

    /**
     * 重新加载本地缓存
     * 
     * @param dataPath
     * @param data
     */
    private void reload(String dataPath, Object data) {
        logger.info("开始加载本地缓存:" + dataPath);
        if (dataPath == null || dataPath.length() < 21) {
            logger.error("dataPath不合法:" + dataPath, new Exception());
            return;
        }
        if (data == null) {
            logger.error("data为空:" + dataPath, new Exception());
            return;
        }
        try {
            String key = this.getKeyByDataPath(dataPath);
            ICacheType cache = CacheFactory.get(key);
            cache.reload(data);
            logger.info("本地缓存加载完毕:" + dataPath);
        } catch (Exception e) {
            logger.error("加载本地缓存异常：" + dataPath, e);
        }
    }
}
