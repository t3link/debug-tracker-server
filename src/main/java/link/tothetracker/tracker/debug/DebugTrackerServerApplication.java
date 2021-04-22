package link.tothetracker.tracker.debug;

import com.google.common.collect.Maps;
import link.tothetracker.tracker.debug.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;

/**
 * @author t3link
 */
@SpringBootApplication
@RestController
@Slf4j
public class DebugTrackerServerApplication {

	private static final String PRE_MIN_RESPONSE =
			"d8:intervali30e12:min intervali10e10:tracker id%se";

	public static void main(String[] args) {
		SpringApplication.run(DebugTrackerServerApplication.class, args);
	}

	@GetMapping(path = "/announce")
	public String announce(HttpServletRequest request) {
		var record = SimpleRecord.of(request);
		log.info("[1] {}: ", JsonUtil.toJson(record));

		return success(record.getIdentify());
	}

	@GetMapping(path = "/scrape")
	public String scrape(HttpServletRequest request) {
		var record = SimpleRecord.of(request);
		log.info("[2] {}: ", JsonUtil.toJson(record));

		return "de";
	}

	private static String success(String identify) {
		var trackerId = identify + "_" + Instant.now().toEpochMilli();
		var encoded = trackerId.length() + ":" + trackerId;

		return String.format(PRE_MIN_RESPONSE, encoded);
	}

}
