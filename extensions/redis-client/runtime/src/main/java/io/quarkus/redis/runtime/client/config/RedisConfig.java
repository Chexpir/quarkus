package io.quarkus.redis.runtime.client.config;

import java.util.Map;

import io.quarkus.runtime.annotations.ConfigDocMapKey;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = RedisConfig.REDIS_CONFIG_ROOT_NAME)
public class RedisConfig {
    public final static String REDIS_CONFIG_ROOT_NAME = "redis";
    public final static String HOSTS_CONFIG_NAME = "hosts";
    public static final String DEFAULT_CLIENT_NAME = "<default>";
    /**
     * The default redis client
     */
    @ConfigItem(name = ConfigItem.PARENT)
    public RedisClientConfig defaultRedisClient;

    /**
     * Configures additional (named) Redis clients.
     * <p>
     * Each client has a unique name which must be identified to select the right client.
     * For example:
     * <p>
     *
     * <pre>
     * quarkus.redis.client1.hosts = redis://localhost:6379
     * quarkus.redis.client2.hosts = redis://localhost:6380
     * </pre>
     * <p>
     * And then use the {@link io.quarkus.redis.client.RedisClientName} annotation to select the
     * {@link io.vertx.mutiny.redis.client.Redis},
     * {@link io.vertx.redis.client.Redis}, {@link io.vertx.mutiny.redis.client.RedisAPI} and
     * {@link io.vertx.redis.client.RedisAPI} beans.
     * <p>
     *
     * <pre>
     * {
     *     &#64;code
     *     &#64;RedisClientName("client1")
     *     &#64;Inject
     *     RedisAPI redis;
     * }
     * </pre>
     */
    @ConfigItem(name = ConfigItem.PARENT)
    @ConfigDocMapKey("redis-client-name")
    public Map<String, RedisClientConfig> namedRedisClients;

    public static boolean isDefaultClient(String name) {
        return DEFAULT_CLIENT_NAME.equalsIgnoreCase(name);
    }

}
