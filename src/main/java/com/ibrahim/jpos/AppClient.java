package com.ibrahim.jpos;

import java.io.IOException;
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

/**
 * Created By Ibrahim Manorek Project Jpos
 */
//http://iso8583tutorial.blogspot.com/2014/10/implementasi-menggunakan-jpos.html
public class AppClient {

	public static void main(String[] args) throws IOException, ISOException {
//		ISOChannel channel = new ASCIIChannel("192.168.19.68", 8583,  new GenericPackager("src/main/java/packager/fields.xml"));
		ISOChannel channel = new ASCIIChannel("localhost", 12345,  new GenericPackager("src/main/WEB-INF/iso8583.xml"));
		
		ISOMsg msg = new ISOMsg();
		msg.setMTI("0200");
		
		// Setting processing code
		msg.set(3, "020000");
		
		// Setting transaction amount
		msg.set(4, "5000");
		
		// Setting transmission date and time
		msg.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		// Setting system trace audit number
		msg.set(11, "123456");
		
//        msg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
//        msg.set(13, new SimpleDateFormat("MMdd").format(new Date()));
        
		// Setting data element #48
        msg.set(48, "Tutorial ISO 8583 Dengan Java");
        
        
//        msg.set(70, "001");
		
		Logger logger = new Logger();
		logger.addListener (new SimpleLogListener (System.out));
		((LogSource)channel).setLogger (logger, "sample"+"-channel");
		channel.connect();
		channel.send(msg);	
		ISOMsg isoMsg = channel.receive();
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
            if(isoMsg.hasField(i))
                System.out.println(i+"='"+isoMsg.getString(i)+"'");
        }
    }
	
}
