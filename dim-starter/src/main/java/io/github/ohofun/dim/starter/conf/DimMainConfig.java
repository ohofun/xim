package io.github.ohofun.dim.starter.conf;

import io.github.ohofun.dim.starter.conf.property.DimConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/9 21:56
 */
@Configuration
@EnableConfigurationProperties(DimConfigProperties.class)
@ConditionalOnProperty(
        prefix = "dim", name = "open", havingValue = "true"
)
public class DimMainConfig {

}
