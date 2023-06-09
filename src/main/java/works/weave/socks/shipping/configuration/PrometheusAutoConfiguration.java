// GPT4:
// Currently, if an error occurs during the creation and registration of the SpringBootMetricsCollector or the ServletRegistrationBean for the MetricsServlet, it will likely cause the application startup to fail.

// To handle this, you could consider wrapping the creation and registration of these beans in try-catch blocks and handle the possible exceptions gracefully. This would ensure that even if the Prometheus metrics fail to be initialized, your application can still function (albeit without Prometheus metrics).

package works.weave.socks.shipping.configuration;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
@ConditionalOnClass(SpringBootMetricsCollector.class)
class PrometheusAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SpringBootMetricsCollector.class)
    SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
        SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector
                (publicMetrics);
        springBootMetricsCollector.register();
        return springBootMetricsCollector;
    }

    @Bean
    @ConditionalOnMissingBean(name = "prometheusMetricsServletRegistrationBean")
    ServletRegistrationBean prometheusMetricsServletRegistrationBean(@Value("${prometheus.metrics" +
            ".path:/metrics}") String metricsPath) {
        DefaultExports.initialize();
        return new ServletRegistrationBean(new MetricsServlet(), metricsPath);
    }
}
