package com.route;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * REST call to check whether route exists from origin to destination Case
 * Sensitive
 */
@RestController
public class CityRouteController {

	@RequestMapping("/")
	public String index() {
		return "Root folder accessed";
	}

	/**
	 * @param origin
	 *            origin city
	 * @param destination
	 *            destination city
	 * @return YES if exists; else NO
	 * @throws Exception
	 */
	@RequestMapping(value = "/connected", method = RequestMethod.GET)
	@ResponseBody
	public String checkPathExists(@RequestParam(value = "origin", required = true) String origin,
			@RequestParam(value = "destination", required = true) String destination)  throws Exception{

		StringBuilder checkRouteExists = new StringBuilder("");
		try {
			CityRoute cityRoute = new CityRoute();

			cityRoute.readData();
			checkRouteExists.append(cityRoute.checkRouteExists(origin, destination));

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return checkRouteExists.toString();

	}

	@ExceptionHandler(value = Exception.class)
	public @ResponseBody String handleException(Exception e) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Error occured:\n");
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	 
		return sb.toString();
	}

}
