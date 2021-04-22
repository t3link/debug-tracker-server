package link.tothetracker.tracker.debug;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author t3link
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class SimpleRecord {

    private static final String CLIENT_IDENTIFY_KEY = "client-version";

    private String identify;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public static SimpleRecord of(HttpServletRequest request) {
        // 转换单个参数
        var parameterMap = request.getParameterMap();
        var parameters = Maps.<String, String>newHashMapWithExpectedSize(parameterMap.size());
        parameterMap.forEach((k, v) -> parameters.put(k, v[0]));

        // 获取 header
        var headerNames = request.getHeaderNames();
        var headers = Maps.<String, String>newHashMapWithExpectedSize(8);
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                var header = headerNames.nextElement();
                var value = request.getHeader(header);
                headers.put(header, value);
            }
        }

        // 身份标识
        var identify = RandomStringUtils.randomAlphabetic(5);
        return new SimpleRecord(identify, parameters, headers);
    }


}
