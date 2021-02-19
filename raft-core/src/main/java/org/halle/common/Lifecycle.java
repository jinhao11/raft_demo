package org.halle.common;

/**
 * 统一的生命周期定义
 */
public interface Lifecycle<T> {
    /**
     * 初始化服务
     * @param opts
     * @return true 初始化成功，false初始化失败
     */
    boolean init(T opts);
    void shutdown();
}
