package com.ibrahim.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import com.ibrahim.model.RequestData;
import com.ibrahim.model.ResponseISO;

public class Helper {
	
	private static ISOMsg isoMsg;
	
	public ResponseISO sendIso8583(RequestData requestData) throws ISOException {
		ResponseISO res = new ResponseISO();
		try {
			ISOChannel channel = new ASCIIChannel("localhost", 12345,  new GenericPackager("src/main/WEB-INF/iso8583.xml"));
			
			ISOMsg msg = new ISOMsg();
			msg.setMTI("0200");
			
			// Setting processing code
			msg.set(3, "020000");
			
			// Setting transaction amount
			msg.set(4, requestData.getNominal());
			
			// Setting transmission date and time
			msg.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
			
			// Setting system trace audit number
			msg.set(11, "123456");
			
//	        msg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
//	        msg.set(13, new SimpleDateFormat("MMdd").format(new Date()));
	        
			// Setting data element #48
	        msg.set(48, requestData.getMessage());
	        
	        
//	        msg.set(70, "001");
			
			Logger logger = new Logger();
			logger.addListener (new SimpleLogListener (System.out));
			((LogSource)channel).setLogger (logger, "sample"+"-channel");
			channel.connect();
			channel.send(msg);	
			isoMsg = channel.receive();
			channel.disconnect ();
			
			// pack the ISO 8583 Message
	        byte[] bIsoMsg = isoMsg.pack();
	 
	        // output ISO 8583 Message String
	        String isoMessage = "";
	        for (int i = 0; i < bIsoMsg.length; i++) {
	            isoMessage += (char) bIsoMsg[i];
	        }
	        System.out.println("Packed ISO8385 Message = '"+isoMessage+"'");
	        
	        // last, print the unpacked ISO8583
	        System.out.println("MTI='"+isoMsg.getMTI()+"'");
	        for(int i=1; i<=isoMsg.getMaxField(); i++){
	            if(isoMsg.hasField(i)) {
	            	if(i == 39)
	            		res.setStatus(isoMsg.getString(i));
	            	if(i == 63)
	            		res.setMessage(isoMsg.getString(i));
	            }
	                System.out.println(i+"='"+isoMsg.getString(i)+"'");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
