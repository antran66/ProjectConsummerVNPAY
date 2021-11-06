package com.vnpay.common;

import com.vnpay.model.Request;
import com.vnpay.model.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

	/**
	 * set value response to client
	 * @param message
	 * @return instance of response object
	 */
	public static Response setMessageResponse(String message){
		String code = MessageConfig.CODE_FAIL;
		if(message.equalsIgnoreCase(MessageConfig.SUCCESS) || message.equalsIgnoreCase(MessageConfig.CONTINUE_PROCESS)){
			code = MessageConfig.CODE_SUCCESS;
		}
		return Response.builder()
				.code(code)
				.message(message)
				.build();
	}

	/**
	 * check input data time is valid with formatter
	 * @param stringDate
	 * @return true if it matched
	 */
	public static boolean checkDateFormat(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			formatter.parse(stringDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * convert object to byte value
	 * @param request
	 * @return byte value
	 * @throws IOException
	 */
	public static byte[] getByteArray(Request request) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(request);
		return out.toByteArray();
	}
}
